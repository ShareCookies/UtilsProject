package com.china.hcg.token;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @desc 测试tokenid
 **/
public  class tokenidTest {
       public static void main(String[] args) throws Exception{
//        for (String s : shortNames){
//            System.out.println(tokenidTest.genericTokenid(s));
//        }
	    System.out.println(tokenidTest.genericTokenid("1300-lvms"));

    }
    public static String genericTokenid(String shortName) throws UnsupportedEncodingException{
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
        String username = shortName;
        String dt = sdf2.format(new Date());
        String code = DigestUtils.md5Hex(username + dt);
        String token = "username=" + username + "&dt=" + dt + "&code=" + code;
        String tokenid = Base64.encodeBase64String(token.getBytes("UTF-8"));
        return tokenid;
    }
//    public static String randomGeneToken (){
//        Random random = new Random();
//        try {
//            return   tokenidTest.genericTokenid(shortNames[random.nextInt(1000)]);
//        }catch (UnsupportedEncodingException e){
//            e.printStackTrace();
//        }
//        return null;
//    }
    /**
     * 验证tokenid
     *
     * @param tokenid
     * @return
     * @throws UnsupportedEncodingException
     */
    public static  boolean checkToken(String tokenid) throws UnsupportedEncodingException {
        String token = URLDecoder.decode(new String(Base64.decodeBase64(tokenid), "UTF-8"), "UTF-8");
        Map<String, String> params = new HashMap(16);
        String[] queryArr = token.split("&");

        String dt;
        for (int i = 0; i < queryArr.length; ++i) {
            dt = queryArr[i];
            String[] kv = dt.split("=", 2);
            if (kv.length != 2) {
                params.put(kv[0], "");
            } else {
                params.put(kv[0], kv[1]);
            }
        }

        String username = (String) params.get("username");

        dt = (String) params.get("dt");
        String code = (String) params.get("code");
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(dt) && !StringUtils.isEmpty(code)) {
            if (!DigestUtils.md5Hex(username + dt).equalsIgnoreCase(code)) {
                return false;
            } else {
                //根据用户简称单点登入-生成用户信息
//                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//                UserDetails userDetails = userDetailsService.loadUserByShortName(username);
//                securityContext.setAuthentication(
//                        new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
//                SecurityContextHolder.setContext(securityContext);
                return true;
            }
        }
        return false;
    }

}
