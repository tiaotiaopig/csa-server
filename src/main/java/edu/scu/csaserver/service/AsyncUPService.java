package edu.scu.csaserver.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class AsyncUPService {


    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Value("${self.unnamed-protocol.expire-time}")
    private Long expiredTime;
    @Value("${self.unnamed-protocol.redis-table-name}")
    private String redisTableName;
    @Value("${self.unnamed-protocol.redis-split-name}")
    private String redisSplitName;

    @Value("${self.unnamed-protocol.tshark.path}")
    private String tsharkPath;

    @Value("${self.unnamed-protocol.tshark-split-dir}")
    private String tsharkSplitPath;

    @Value("${self.unnamed-protocol.pcap-ana-script.path}")
    private String pcapAnaScriptPath;

    @Value("${self.unnamed-protocol.status.doing}")
    private String doing;
    @Value("${self.unnamed-protocol.status.empty}")
    private String empty;
    @Value("${self.unnamed-protocol.status.done}")
    private String done;
    @Value("${self.unnamed-protocol.status.error}")
    private String error;


    private static final String protein = "ACDEFGHIKLMNPQRSTVWY";



    @Async
    public void genData(File file,String proto,String [] fields){
        redisTemplate.opsForHash().put(redisTableName,genKey(file.getName(),proto),"doing");
        System.out.println("first gen data:" + file + " " + proto);
        try {
            String key = genFileName(file.getName(),proto);
            String status = splitStatus(file.getName(),proto);
            if (status==null||status.equals(doing)||status.equals(error)) {
                StringBuilder sb = new StringBuilder();
                sb.append(tsharkPath).append(" -r '").append(file.getAbsolutePath()).append("' -R \"").append(proto).append("\" -j \"").append(proto).append("\" -2 ");
                for (String t : fields)
                    sb.append(" -e \"").append(t).append("\"");
                sb.append(" -T fields").append(" -E separator=\"^\"");
                String[] cmd1 = new String[]{"sh", "-c", sb.toString()};
                System.out.println(Arrays.toString(cmd1));
                String []cmd2= new String[]{"sh", "-c", tsharkPath + " -r '" + file.getAbsolutePath() + "' -R \"" + proto + "\" -2 -Y \"frame\" -x"};
                System.out.println(Arrays.toString(cmd2));
                 doGenData(file.getName(),file.getAbsolutePath(),proto,fields,cmd1,cmd2);
            }else {
                String filePath = tsharkSplitPath+key;
                StringBuilder sb = new StringBuilder();
                sb.append(tsharkPath).append(" -r '").append(filePath).append("' -R \"").append(proto).append("\" -j \"").append(proto).append("\" -2 ");
                for (String t : fields)
                    sb.append(" -e \"").append(t).append("\"");
                sb.append(" -T fields").append(" -E separator=\"^\"");
                String[] cmd1 = new String[]{"sh", "-c", sb.toString()};
                System.out.println(Arrays.toString(cmd1));

                String []cmd2= new String[]{"sh", "-c", tsharkPath + " -r '" + filePath + "' -R \"" + proto + "\" -2 -Y \"frame\" -x"};
                System.out.println(Arrays.toString(cmd2));
                doGenData(file.getName(),key,proto,fields,cmd1,cmd2);
            }

        } catch (Exception e) {
            e.printStackTrace();
            redisTemplate.opsForHash().put(redisTableName,genKey(file.getName(),proto),error);
        }
    }

    @Async
    public void splitFile(File file,String []protocols){
        StringBuilder sb = new StringBuilder();
        sb.append(tsharkPath).append(" -r ").append(file.getAbsolutePath()).append(" -R \"");
        for (String proto:protocols){
            String []cmd = new String[]{"sh","-c", sb + proto + "\" -2 -w " + tsharkSplitPath + genFileName(file.getName(), proto)};
            System.out.println(Arrays.toString(cmd));
            boolean err = false;
            try {
                redisTemplate.opsForHash().put(redisSplitName,genFileName(file.getName(),proto),doing);
                Runtime.getRuntime().exec(cmd).waitFor();
            }catch (Exception e){
                e.printStackTrace();
                err = true;
                redisTemplate.opsForHash().put(redisSplitName,genFileName(file.getName(),proto),error);
            }
            if (!err)
                redisTemplate.opsForHash().put(redisSplitName,genFileName(file.getName(),proto),done);
        }
    }

    public String genKey(String file,String proto){
        return file +"_" +proto;
    }
    public boolean hasKey(String tableName,String key){
        return  redisTemplate.opsForHash().hasKey(tableName,key);
    }
    public String status(String file,String proto){
        if (hasKey(redisTableName,genKey(file,proto))){
            return ((String) redisTemplate.opsForHash().get(redisTableName, genKey(file, proto)));
        }
        return null;//没有进行过
    }
    public String genFileName(String file,String proto){
        return file.split("\\.")[0]+"_"+proto+".pcap";
    }
    private String splitStatus(String file,String proto){
        String key = genFileName(file,proto);
        boolean hasKey = redisTemplate.opsForHash().hasKey(redisSplitName,key);
        if (hasKey){
            return Objects.requireNonNull(redisTemplate.opsForHash().get(redisSplitName, key)).toString();
        }
        return null;
    }

    private int doGenFields(String [] fields,List<Map<String, Object>> tableData,String []cmd) throws IOException, InterruptedException {
        Process proc = Runtime.getRuntime().exec(cmd);
        InputStream stdin = proc.getInputStream();
        InputStreamReader isr = new InputStreamReader(stdin, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            String[] tmp = line.split("\\^");
            Map<String, Object> mp = new HashMap<>();
            for (int i = 0; i < fields.length; i++) {
                String val = null;
                if (i < tmp.length)
                    val = tmp[i];
                if (val == null || val.isBlank())
                    val = "null";
                mp.put(fields[i], val);
            }
            tableData.add(mp);
        }
        return proc.waitFor();
    }
    private int doGenFrame(List<Map<String, Object>> tableData,String [] cmd) throws IOException, InterruptedException {
        Process proc2 = Runtime.getRuntime().exec(cmd);
        InputStream stdin = proc2.getInputStream();
        InputStreamReader isr = new InputStreamReader(stdin, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder pre = new StringBuilder();
        int idx = 0;
        boolean use = true;
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isBlank()) {
                if (use) {
                    tableData.get(idx).put("frame", pre.toString());
                    ++idx;
                }
                use = true;
                pre = new StringBuilder();
                continue;
            } else if (line.startsWith("Reassembled") || line.startsWith("Decrypted") || line.startsWith("De-chunked") || line.startsWith("Uncompressed")) {
                use = false;
                continue;
            } else if (line.startsWith("Frame")) {
                use = true;
                continue;
            }
            pre.append(StringUtils.arrayToDelimitedString(line.substring(6, 53).split(" "), ""));
        }

        return proc2.waitFor();

    }
    @Async
    public void genUnknown(String filepath){
        String []cmd = new String[]{"sh","-c","python3 "+ pcapAnaScriptPath+" '"+filepath+"' all"};
//        String cmd = "python3 "+ pcapAnaScriptPath+" "+filepath +" all";
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            int code = process.waitFor();
            System.out.println("uk ret:"+code);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void doGenData(String originFile,String fileName,String proto,String [] fields,String []cmd1,String [] cmd2) throws IOException, InterruptedException {
        List<Map<String, Object>> tableData = new ArrayList<>();
        int p1Exval = doGenFields(fields,tableData,cmd1);
        int p2ExVal = doGenFrame(tableData,cmd2);


        if (p1Exval != 0 || p2ExVal != 0) {
            System.out.println("p1:" + p1Exval + " p2:" + p2ExVal);
            redisTemplate.opsForHash().put(redisTableName, genKey(fileName, proto), error);
            return;
        }


        Map[] objArr = new HashMap[tableData.size()];
        for (int i = 0; i < tableData.size(); i++) {
            objArr[i] = tableData.get(i);
        }
        if (objArr.length != 0) {
            redisTemplate.delete(genKey(originFile, proto));
            redisTemplate.opsForList().rightPushAll(genKey(originFile, proto), objArr);
            redisTemplate.opsForHash().put(redisTableName, genKey(originFile, proto), done);
//                redisTemplate.expire(genKey(file,proto), expiredTime, TimeUnit.SECONDS);//设置过期时间
        } else {//不存在
            redisTemplate.opsForHash().put(redisTableName, genKey(originFile, proto), empty);
        }
    }
}
