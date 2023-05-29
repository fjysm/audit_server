package com.swu.audit.common.helper;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.jsonwebtoken.*;

import java.util.Date;

public class JwtHelper {
    private static long tokenExpiration = 24*60*60*1000;
    private static String tokenSignKey = "123456";

    public static String createToken(Long userId, String userName) {
        String token = Jwts.builder()
                .setSubject("USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .claim("userName", userName)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }
    public static Long getUserId(String token) {
        if(StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer)claims.get("userId");
        return userId.longValue();
    }
    public static String getUserName(String token) {
        if(StringUtils.isEmpty(token)) return "";
        Jws<Claims> claimsJws
                = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("userName");
    }
     public  static  String isValdate(String token){
         try {
             JwtParser parser = Jwts.parser();
             parser.setSigningKey(tokenSignKey); //解析token的SigningKey必须和生成token时设置密码一致
             //如果token检验通过（密码正确，有效期内）则正常执行，否则抛出异常
             Jws<Claims> claimsJws = parser.parseClaimsJws(token);
             return "pass";//true就是验证通过，放行
         }catch (ExpiredJwtException e){
             return "expire";
         }catch (UnsupportedJwtException e){
             return "illegal";
         }catch (Exception e){
            return "unlogin";
         }

     }

}
