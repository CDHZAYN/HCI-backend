package com.example.hci.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.hci.dao.dto.User;
import com.example.hci.exception.MyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtConfig {
    private static String secret;
    private static long expire;
    private Key key;

    @Value("${jwt.secret}")
    public void setJwtSecret(String jwtSecret) { //通过set让static方法读取配置文件中的值
        JwtConfig.secret = jwtSecret;
    }

    @Value("${jwt.expire}")
    public void setExpire(long expire) { //通过set让static方法读取配置文件中的值
        JwtConfig.expire = expire;
    }

    public String createJWT(User user){
        Date date  = new Date();
        Date expireDate = new Date(date.getTime() + expire);
        return JWT.create()
                .withClaim("username", user.getUsername())
                .withClaim("password", user.getPassword())
                .withClaim("email", user.getEmail())
                .withExpiresAt(expireDate)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(secret));
    }

    public Map<String, Claim> parseJwt(String token){
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        }catch (TokenExpiredException e){
            throw new MyException("A0230", "用户登录已过期");
        }catch (JWTVerificationException | IllegalArgumentException e){
            throw new MyException("A0200", "登录失败");
        }
    }
}
