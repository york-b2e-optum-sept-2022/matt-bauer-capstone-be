package net.yorksolutions.mattbauercapstonebe;

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
public class JsonWebToken {

    private class StorageObject {
        String userId;
        Key key;
        String jwt;
    }

    private ArrayList<StorageObject> activeUsers = new ArrayList<>();

    public static String getNewSecret(){
        return (UUID.randomUUID().toString() + UUID.randomUUID().toString() +
                UUID.randomUUID().toString()).replaceAll("-", "");
    }

    public Key getNewKey(String secret){
        return new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
    }

    public String getNewJwt(Key key, String userSessionId){
        Instant now = Instant.now();
        return Jwts.builder()
                .setId(userSessionId)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(5L, ChronoUnit.MINUTES)))
                .signWith(key)
                .compact();
    }

    public void addActiveUser(Key key, String id, String jwt){
        StorageObject newUser = new StorageObject();
                newUser.userId = id;
        newUser.jwt=jwt;
                newUser.key = key;
        System.out.println(newUser.jwt);
        this.activeUsers.add(0, newUser);
        System.out.println(
                newUser.userId + " " +
                newUser.key + " " +
                newUser.jwt
        );
        System.out.println(this.activeUsers.size());
    }


    public boolean validateUserResponse(String jwt){
        StorageObject userToValidate = this.activeUsers.stream().filter(user -> jwt.equals(user.jwt)).findFirst().orElse(null);
        if(userToValidate != null) {
            Jws<Claims> parsedJwt = Jwts.parserBuilder().setSigningKey(userToValidate.key).build().parseClaimsJws(jwt);
            System.out.println(" parsed jwt userid" +parsedJwt.getBody().getId());
            System.out.println("stored user id" + userToValidate.userId);
            if (parsedJwt.getBody().getId().equals(userToValidate.userId))
                return true;
        else return false;
        }
        return false;
    }

    public void endUserSession(String jwt){
        System.out.println("before: " + this.activeUsers.size());
        StorageObject userSessionToEnd = this.activeUsers.stream().filter(user -> jwt.equals(user.jwt)).findFirst().orElse(null);
        if(userSessionToEnd != null)
            this.activeUsers.remove(userSessionToEnd);
        System.out.println("after :" + this.activeUsers.size());
    }
}
