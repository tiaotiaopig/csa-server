package edu.scu.csaserver.utils;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class SecurityUtil {
    public String bytesToHex(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    public String encodeSHA256(String src)  {
        String res = null;
        if (src!=null&&src.length()>0){
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                digest.update(src.getBytes(StandardCharsets.UTF_8));
                byte [] buffer = digest.digest();
                res = bytesToHex(buffer);
            }catch (NoSuchAlgorithmException e){
                e.printStackTrace();
            }
        }
        return res;
    }
    public String join(String [] array){
        StringBuilder sb = new StringBuilder();
        for (String item:array){
            sb.append(item);
        }
        return sb.toString();
    }
}
