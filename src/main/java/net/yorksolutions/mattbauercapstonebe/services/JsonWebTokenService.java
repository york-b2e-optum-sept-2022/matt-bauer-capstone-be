package net.yorksolutions.mattbauercapstonebe.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

@Service
public class JsonWebTokenService {

    private class userSession {
        String userId;
        Key key;
        String jwt;
    }

    private ArrayList<userSession> activeUsers = new ArrayList<>();

    public String createJws(String userId) {
        String secret = JsonWebTokenService.getNewSecret();
        Key key = this.getNewKey(secret);
        String jwt = this.getNewJwt(key, userId);
        this.addActiveUser(key, userId, jwt);
        return jwt;
    }

    private static String getNewSecret(){
        return (UUID.randomUUID().toString() + UUID.randomUUID().toString() +
                UUID.randomUUID().toString()).replaceAll("-", "");
    }

    private Key getNewKey(String secret){
        return new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
    }

    private String getNewJwt(Key key, String userSessionId){
        Instant now = Instant.now();
        return Jwts.builder()
                .setId(userSessionId)
                //issued now
                .setIssuedAt(Date.from(now))
                //expires in 5 minutes
                .setExpiration(Date.from(now.plus(5L, ChronoUnit.MINUTES)))
                .signWith(key)
                .compact();
    }

    private void addActiveUser(Key key, String id, String jwt){
        userSession newUser = new userSession();
                newUser.userId = id;
        newUser.jwt=jwt;
                newUser.key = key;
        this.activeUsers.add(0, newUser);
    }


    public boolean validateUserResponse(String jwt){
        userSession userToValidate = this.activeUsers.stream().filter(user -> jwt.equals(user.jwt)).findFirst().orElse(null);
        if(userToValidate != null) {
            Jws<Claims> parsedJwt = Jwts.parserBuilder().setSigningKey(userToValidate.key).build().parseClaimsJws(jwt);
            if (parsedJwt.getBody().getId().equals(userToValidate.userId))
                return true;
        else return false;
        }
        return false;
    }

    public void endUserSession(String jwt){
        userSession userSessionToEnd = this.activeUsers.stream().filter(user -> jwt.equals(user.jwt)).findFirst().orElse(null);
        if(userSessionToEnd != null)
            this.activeUsers.remove(userSessionToEnd);
    }
}
