package edu.scu.csaserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.scu.csaserver.service.AsyncUPService;
import edu.scu.csaserver.vo.FileVo;
import edu.scu.csaserver.vo.Res;
import edu.scu.csaserver.vo.ResCode;
import io.swagger.models.auth.In;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("unnamedProtocol")
public class UnnamedProtocolController {

    //    static String[] DATA = new String[]{"frame.len", "frame.protocols", "frame.encap_type", "eth.src", "eth.dst", "eth.type", "eth.len", "ip.src", "ip.dst", "ip.flags", "ip.proto", "ip.len", "udp.srcport", "udp.dstport", "udp.payload", "udp.length", "tcp.srcport", "tcp.dstport", "tcp.payload", "tcp.flags", "tcp.window_size"};
//    static String[] DATA_NAME = new String[]{"frameLen", "frameProtocols", "frameEncapType", "ethSrc", "ethDst", "ethType", "ethLen", "ipSrc", "ipDst", "ipFlags", "ipProto", "ipLen", "udpSrcPort", "udpDstPort", "udpPayload", "udpLength", "tcpSrcPort", "tcpDstPort", "tcpPayload", "tcpFlags", "tcpWindowSize"};
    final static String[] DATA = new String[]{"frame.len", "frame.protocols", "eth.src", "eth.dst", "ip.src", "ip.dst", "udp.srcport", "udp.dstport", "tcp.srcport", "tcp.dstport",};
    final static String[] DATA_NAME = new String[]{"frameLen", "frameProtocols", "ethSrc", "ethDst", "ipSrc", "ipDst", "udpSrcPort", "udpDstPort", "tcpSrcPort", "tcpDstPort"};


    final static String[] APP_NAME = new String[]{"bgp", "http", "http2", "icp", "isakmp", "ldap", "ntp", "ospf", "pcep", "rip", "ripng", "rpc", "rtsp", "sdp", "sip", "snmp", "ssh", "tacacs", "tls", "bootp", "dhcp", "dns", "ftp", "imap", "irc", "iscsi", "kerberos", "ldap", "nntp", "pop", "radius", "rlogin", "sctp", "smtp", "tcp", "telnet", "tftp", "time", "udp", "vxlan", "websocket"};
    final static String[][] APP_LAYER = new String[][]{
            {"bgp.type", "bgp.cap.length", "bgp.local_pref", "bgp.next_hop", "bgp.origin"},
            {"http.location", "http.user_agent", "http.content_type", "http.content_length", "http.host"},
            {"http2.flags", "http2.headers.location", "http2.headers.content_type", "http2.headers.content_length", "http2.length"},
            {"icp.length", "icp.opcode", "icp.rtt", "icp.url", "icp.version"},
            {"isakmp.length", "isakmp.version", "isakmp.nextpayload", "isakmp.messageid", "isakmp.flags"},
            {"ldap.controls", "ldap.size", "ldap.type", "ldap.state", "ldap.flags"},
            {"ntp.flags", "ntp.org", "ntp.delta_time", "ntp.ext.type"},
            {"ospf.packet_length", "ospf.srcrouter", "ospf.version", "ospf.msg", "ospf.metric"},
            {"pcep.attribute", "pcep.bandwidth", "pcep.flags", "pcep.msg", "pcep.version"},
            {"rip.ip", "rip.next_hop", "rip.metric", "rip.netmask", "rip.version"},
            {"ripng.rte.ipv6_prefix", "ripng.cmd", "ripng.reserved", "ripng.rte", "ripng.version"},
            {"rpc.argument_length", "rpc.array.len", "rpc.msgtyp", "rpc.fraglen", "rpc.version"},
            {"rtsp.length", "rtsp.data", "rtsp.channel", "rtsp.method", "rtsp.url"},
            {"sdp.time", "sdp.uri", "sdp.media_title", "sdp.owner", "sdp.version"},
            {"sip.Content-Type", "sip.Method", "sip.Path", "sip.Allow", "sip.auth"},
            {"snmp.agent_addr", "snmp.data", "snmp.msgID", "snmp.engineid.ipv4", "snmp.version"},
            {"ssh.mac", "ssh.payload", "ssh.host_key.data", "ssh.protocol", "ssh.message_code"},
            {"tacacs.destaddr", "tacacs.destport", "tacacs.password", "tacacs.username", "tacacs.version"},
            {"tls.app_data_proto", "tls.handshake.client_cert_vrfy.sig", "tls.handshake.version", "tls.record.content_type"},
            {"bootp.id", "bootp.hw.addr", "bootp.ip.client", "bootp.ip.your", "bootp.ip.server", "bootp.flags"},
            {"dhcp.id", "dhcp.client", "dhcp.server", "dhcp.flags", "dhcp.type", "dhcp.cookie"},
            {"dns.id", "dns.a", "dns.ns", "dns.resp.type", "dns.resp.name", "dns.cname", "dns.flags"},
            {"ftp.active.cip", "ftp.active.port", "ftp.request.command", "ftp.request.arg", "ftp.response.arg", "ftp.response.code"},
            {"imap.request.command", "imap.request", "imap.request.username", "imap.request.username", "imap.response", "imap.response.command", "imap.response.status"},
            {"irc.request.command", "irc.request.command_parameter", "irc.request.trailer", "irc.response.command", "irc.response.command_parameter", "irc.response.trailer"},
            {"iscsi.cid", "iscsi.flags", "iscsi.cmdsn", "iscsi.opcode", "iscsi.ahs"},
            {"kerberos.addr_ip", "kerberos.adtype", "kerberos.advalue", "kerberos.Authenticator", "kerberos.factors", "kerberos.groups"},
            {"ldap.sid", "ldap.type", "ldap.value", "ldap.version", "ldap.size", "ldap.name"},
            {"nntp.request", "nntp.response"},
            {"pop.data.fragment", "pop.request", "pop.request.command", "pop.request.data", "pop.response", "pop.response.data"},
            {"radius.time", "radius.id", "radius.length", "radius.authenticator", "radius.Authorization_Signature"},
            {"rlogin.client_startup_flag", "rlogin.client_user_name", "rlogin.control_message", "rlogin.data", "rlogin.magic_cookie", "rlogin.server_user_name", "rlogin.window_size"
            },
            {"sctp.srcport", "sctp.dstport", "sctp.cookie", "sctp.data_sid", "sctp.chunk_type", "sctp.chunk_value"},
            {"smtp.req", "smtp.req.command", "smtp.req.parameter", "smtp.response", "smtp.response.code", "smtp.rsp.parameter", "smtp.auth.username", "smtp.auth.password"},
            {"tcp.srcport", "tcp.dstport", "tcp.flags", "tcp.window_size", "tcp.len"},
            {"telnet.auth.name", "telnet.auth.type", "telnet.auth.cmd", "telnet.enc.key_id", "telnet.enc.type", "telnet.enc.cmd", "telnet.data"},
            {"tftp.fragment", "tftp.opcode", "tftp.type", "tftp.source_file", "tftp.destination_file", "tftp.nextwindowsize"
            },
            {"time.time", "time.response"},
            {"udp.srcport", "udp.dstport", "udp.stream", "udp.length"},
            {"vxlan.ver", "vxlan.vni", "vxlan.flags", "vxlan.gbp", "vxlan.next_proto"},
            {"websocket.fragment", "websocket.opcode", "websocket.payload_length", "websocket.payload", "websocket.payload.text"},

    };


    static final int pageSize = 20;
    @Value("${self.unnamed-protocol.redis-table-name}")
    private String redisTableName;

    @Value("${self.unnamed-protocol.unknown-status-key}")
    private String ukStatusKey;

    @Value("${self.unnamed-protocol.unknown-chart-key}")
    private String ukChartKey;

    @Value("${self.unnamed-protocol.tshark-upload-pro}")
    private String tsharkUploadPro;

    @Value("${self.unnamed-protocol.tshark-upload-dev}")
    private String tsharkUploadDev;

    @Value("${self.unnamed-protocol.expire-time}")
    private Long expiredTime;

    @Value("${self.unnamed-protocol.tshark.path}")
    private String tsharkPath;
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


    @Autowired
    private AsyncUPService asyncUPService;


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @CrossOrigin
    @GetMapping("chart")
    public Object getChart(@RequestParam(value = "file", required = true) String file) {
        if (hasKey(file)) {
            return Res.success(redisTemplate.opsForHash().get(redisTableName, file));
        } else {
            Res<Object> ret = firstGenData(file);
            return Objects.requireNonNullElseGet(ret, () -> Res.success(redisTemplate.opsForHash().get(redisTableName, file)));
        }

    }

    @CrossOrigin
    @GetMapping("detail")
    public Object getDetail(@RequestParam(value = "file", required = true) String file, @RequestParam(value = "pageNo", required = false) Integer pageNo, @RequestParam(value = "protocol", required = false) String proto) {
        if (pageNo == null)
            pageNo = 0;
        Map<String, Object> data = new HashMap<>();
        if (proto == null) {
            if (!hasKey(file) && firstGenData(file) != null) {
                return Res.genRes(ResCode.FILE_NOT_EXIST);
            }
            getDetailData(data, file, pageNo);
        } else {
            String status = asyncUPService.status(file, proto);
            if (status == null) {//not operated yet
                Res<Object> ret = fisrtGenAppData(file, proto);
                if (ret != null)//bad ret val
                    return ret;
                return Res.genRes(ResCode.WAIT_A_MOMENT);
            } else if (status.equals(doing)) {
                return Res.genRes(ResCode.WAIT_A_MOMENT);
            } else if (status.equals(empty)) {
                return Res.genRes(ResCode.ANALYZE_EMPTY_DATA);
            } else if (status.equals(error)) {
                return Res.genRes(ResCode.ANALYZE_FAILED);
            } else {
                getDetailData(data, file, proto, pageNo);
            }
        }
        data.put("pageSize", pageSize);
        data.put("pageNo", pageNo);
        return Res.success(data);
    }

    @CrossOrigin
    @GetMapping("fileList")
    public Object getFile() throws FileNotFoundException {
        String filePath;
        if (isWindows())
            filePath = tsharkUploadDev;
        else
            filePath = tsharkUploadPro;
        File file = new File(filePath);
        if (!file.exists() || !file.isDirectory()) {
            return Res.genRes(ResCode.DIR_NOT_EXIST);
        }
        List<FileVo> list = new ArrayList<>();
        for (File f : Objects.requireNonNull(file.listFiles())) {
            if (f.getName().endsWith("pcap") || f.getName().endsWith("pcapng"))
            list.add(new FileVo(f.getName(), f.lastModified(), f.length()));
        }
        return Res.success(list);
    }

    @CrossOrigin
    @PostMapping("upload")
    public Object upload(@RequestParam("file") MultipartFile file) throws FileNotFoundException {
        ResCode opCode = saveFile(file);
        if (opCode.equals(ResCode.SUCCESS)) {
            asyncUPService.genUnknown(tsharkUploadPro + file.getOriginalFilename());
        }
        return Res.genRes(opCode);
    }


    private String genUnknownKey(String filename, String protocol) {
        return filename + "_" + protocol;
    }


    @CrossOrigin
    @GetMapping("unknownChart")
    public Object getUkChart(@RequestParam(name = "file", required = true) String filename) {
        String stKey = genUnknownKey(filename, ukStatusKey);
        String chartKey = genUnknownKey(filename, ukChartKey);
        if (!asyncUPService.hasKey(redisTableName, stKey))
            return Res.genRes(ResCode.FILE_NOT_EXIST);
        String status = asyncUPService.status(filename, ukStatusKey);
        assert status != null;
        if (status.equals("done")) {
            return Res.success(redisTemplate.opsForHash().get(redisTableName, chartKey));
        } else if (status.equals("error")) {
            return Res.genRes(ResCode.ANALYZE_FAILED);
        } else {
            return Res.genRes(ResCode.WAIT_A_MOMENT);
        }
    }

    @CrossOrigin
    @GetMapping("unknownData")
    public Object getUkData(@RequestParam(name = "file", required = true) String filename,
                            @RequestParam(name = "protocol", required = true) String proto,
                            @RequestParam(name = "pageNo", required = false) Integer pageNo) {
        if (pageNo == null)
            pageNo = 0;
        int st = pageNo * pageSize;
        int end = st + pageSize;
        String key = asyncUPService.genKey(filename,proto);
        String stKey = genUnknownKey(filename, ukStatusKey);
        if (!asyncUPService.hasKey(redisTableName, stKey))//状态信息
            return Res.genRes(ResCode.FILE_NOT_EXIST);
        if (!hasKey(key))//协议信息
            return Res.genRes(ResCode.PROTOCOL_NOT_EXIST);

        String status = asyncUPService.status(filename, ukStatusKey);
        assert status != null;
        long count =0;
        List<Object> data = new ArrayList<>();
        if (status.equals("done")) {
            data.addAll(Objects.requireNonNull(redisTemplate.opsForList().range(key, st, end - 1)));
            count = Objects.requireNonNull(redisTemplate.opsForList().size(key));
        } else if (status.equals("error")) {
            return Res.genRes(ResCode.ANALYZE_FAILED);
        } else {
            return Res.genRes(ResCode.WAIT_A_MOMENT);
        }
        Map<String,Object> ret = new HashMap<>();
        ret.put("data",data);
        ret.put("count",count);
        long pageNum = count / pageSize;
        if (count % pageSize != 0)
            ++pageNum;
        ret.put("pageCount",pageNum);
        return Res.success(ret);
    }

    @PostMapping("multiUpload")
    public Object multiUpload(@RequestParam("file") MultipartFile[] files) throws FileNotFoundException {
        Map<String, String> map = new HashMap<>();
        boolean allSuccess = true;
        for (MultipartFile f : files) {
            ResCode code = saveFile(f);
            if (code.equals(ResCode.SUCCESS)) {
                map.put(f.getOriginalFilename(), "上传成功");
            } else {
                allSuccess = false;
                map.put(f.getOriginalFilename(), code.getMsg());
            }
        }
        if (allSuccess)
            return Res.genRes(ResCode.SUCCESS);
        else
            return Res.genResWithData(ResCode.UPLOAD_FAIL, map);
    }


    private ResCode saveFile(MultipartFile file) throws FileNotFoundException {
        if (file.isEmpty()) {
            return ResCode.UPLOAD_FAIL;
        }
        String filename = file.getOriginalFilename(); //获取上传文件原来的名称
        assert filename != null;
        String[] tmp = filename.split("\\.");
        if (tmp.length < 2 || !"pcap".equals(tmp[1])) {
            return ResCode.UPLOAD_FAIL;
        }
        return doUpload(file, filename);
    }

    private ResCode doUpload(MultipartFile file, String filename) throws FileNotFoundException {
        String filePath;
        if (isWindows())
            filePath = tsharkUploadDev;
        else
            filePath = tsharkUploadPro;
        File temp = new File(filePath);
        if (!temp.exists()) {
            temp.mkdirs();
        }
        for (String cur : Objects.requireNonNull(temp.list()))//限制同名文件
            if (cur.equals(filename))
                return ResCode.UPLOAD_FAIL;
        File localFile = new File(filePath + filename);
        localFile.setLastModified(System.currentTimeMillis());
        try {
            file.transferTo(localFile); //把上传的文件保存至本地
        } catch (IOException e) {
            e.printStackTrace();
            return ResCode.UPLOAD_FAIL;
        }

        return ResCode.SUCCESS;
    }

    private Res<Object> firstGenData(String file) {
        System.out.println("first gen data:" + file);
        try {
            String filePath;
            if (isWindows())
                filePath = tsharkUploadDev;
            else
                filePath = tsharkUploadPro;
            filePath = filePath + file;
            File fileDisk = new File(filePath);
            if (!fileDisk.exists()) {
                return Res.genRes(ResCode.FILE_NOT_EXIST);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(tsharkPath).append(" -r ").append(filePath);
            for (String t : DATA)
                sb.append(" -e ").append(t);
            sb.append(" -T fields").append(" -E separator=^");
            String[] cmd = new String[]{"sh", "-c", sb.toString()};

            System.out.println(Arrays.toString(cmd));

            Process proc = Runtime.getRuntime().exec(cmd);
            InputStream stdin = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(stdin, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);

            String line = "";
            List<String> chartList = new ArrayList<>();
            List<Map<String, Object>> tableData = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] tmp = line.split("\\^");
                chartList.add(tmp[1]);
                Map<String, Object> mp = new HashMap<>();
                for (int i = 0; i < DATA_NAME.length; i++) {
                    Object val = null;
                    if (i < tmp.length)
                        val = tmp[i];
                    mp.put(DATA_NAME[i], val);
                }
                tableData.add(mp);
            }
            proc.waitFor();

            Map<String, Object> charData = getChartData(chartList);
            splitFile(fileDisk, ((Map<String, Integer>) charData.get("upLayer")));
            Map[] objArr = new HashMap[tableData.size()];
            for (int i = 0; i < tableData.size(); i++) {
                objArr[i] = tableData.get(i);
            }
            redisTemplate.opsForList().rightPushAll(file, objArr);
//            redisTemplate.opsForList().rightPushAll(file+"_tcp",tcpArr);
//            redisTemplate.opsForList().rightPushAll(file+"_udp",udpArr);
            redisTemplate.expire(file, expiredTime, TimeUnit.SECONDS);//设置过期时间
//            redisTemplate.expire(file+"_tcp",expiredTime, TimeUnit.SECONDS);//设置过期时间
//            redisTemplate.expire(file+"_udp",expiredTime, TimeUnit.SECONDS);//设置过期时间
            redisTemplate.opsForHash().put(redisTableName, file, charData);
            return null;
        } catch (Exception e) {
            e.printStackTrace();

            return Res.genRes(ResCode.FILE_NOT_EXIST);
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toUpperCase().contains("WINDOWS");
    }

    private Map<String, Object> getChartData(List<String> list) {
        Map<String, Integer> upLayer = new HashMap<>();
        Map<String, Integer> allLayer = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        int uk = 0;
        for (String s : list) {
            String[] arrs = s.split(":");
            int idx = -1;
            for (int i = 0; i < arrs.length; i++) {
                if (arrs[i].equals("ethertype"))
                    continue;
                allLayer.computeIfPresent(arrs[i], (k, v) -> v + 1);
                allLayer.putIfAbsent(arrs[i], 1);
                if (arrs[i].equals("ip")) {
                    idx = i;
                    break;
                }
            }
            if (idx != -1) {
                if (arrs.length >= idx + 3) {
                    upLayer.computeIfPresent(arrs[arrs.length - 1], (k, v) -> v + 1);
                    upLayer.putIfAbsent(arrs[arrs.length - 1], 1);
                }
                if (arrs.length > idx + 1) {
                    if (arrs[idx + 1].equals("tcp") || arrs[idx + 1].equals("udp")) {
                        upLayer.computeIfPresent(arrs[idx + 1], (k, v) -> v + 1);
                        upLayer.putIfAbsent(arrs[idx + 1], 1);
                    }
                }
            }
            if (arrs[arrs.length - 1].equals("data"))
                uk++;
        }

        allLayer.putAll(upLayer);
        data.put("upLayer", upLayer);
        data.put("allLayer", allLayer);
        data.put("allProtocolNum", allLayer.keySet().size());
        data.put("upProtocolNum", upLayer.keySet().size());
        data.put("packetNum", list.size());
        data.put("unnamedProtocolNum", uk);
        data.put("unnamedProtocolType", 1);
        return data;
    }

    private void splitFile(File file, Map<String, Integer> upLayer) {
        List<String> protocols = upLayer.keySet().stream().toList();
        String[] protos = new String[protocols.size()];
        for (int i = 0; i < protos.length; i++)
            protos[i] = protocols.get(i);
        asyncUPService.splitFile(file, protos);
    }

    private void delExpiredData(String key) {
        redisTemplate.opsForHash().delete(redisTableName, key);
        for (String t : APP_NAME) {
            redisTemplate.opsForHash().delete(redisTableName, asyncUPService.genKey(key, t));
            redisTemplate.delete(asyncUPService.genKey(key, t));
        }
    }

    private boolean hasKey(String key) {
        boolean hk = Boolean.TRUE.equals(redisTemplate.hasKey(key));
        if (!hk)
            delExpiredData(key);
        return hk;
    }

    private void getDetailData(Map<String, Object> data, String file, Integer pageNo) {
        int st = pageSize * pageNo;
        data.put("data", redisTemplate.opsForList().range(file, st, st + pageSize - 1));
        Long sz = redisTemplate.opsForList().size(file);
        data.put("count", sz);
        long pageNum = sz / pageSize;
        if (sz % pageSize != 0)
            ++pageNum;
        data.put("pageCount", pageNum);

    }

    private void getDetailData(Map<String, Object> data, String file, String proto, Integer pageNo) {
        int st = pageSize * pageNo;
        data.put("data", redisTemplate.opsForList().range(asyncUPService.genKey(file, proto), st, st + pageSize - 1));
        Long sz = redisTemplate.opsForList().size(asyncUPService.genKey(file, proto));
        data.put("count", sz);
        long pageNum = sz / pageSize;
        if (sz % pageSize != 0)
            ++pageNum;
        data.put("pageCount", pageNum);
    }

    private Res<Object> fisrtGenAppData(String file, String proto) {
        try {
            String filePath;
            if (isWindows())
                filePath = tsharkUploadDev;
            else
                filePath = tsharkUploadPro;
            filePath = filePath + file;
            File fileDisk = new File(filePath);
            if (!fileDisk.exists()) {
                System.out.println(file + " 不存在");
                return Res.genRes(ResCode.FILE_NOT_EXIST);
            }
            StringBuilder sb = new StringBuilder();
            int idx = -1;
            for (int i = 0; i < APP_NAME.length && idx == -1; i++)
                if (APP_NAME[i].equals(proto))
                    idx = i;
            if (idx == -1)
                return Res.genRes(ResCode.PROTOCOL_NOT_SUPPORT);
            asyncUPService.genData(fileDisk, APP_NAME[idx], APP_LAYER[idx]);
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return Res.genRes(ResCode.FILE_NOT_EXIST);
        }
    }

}
