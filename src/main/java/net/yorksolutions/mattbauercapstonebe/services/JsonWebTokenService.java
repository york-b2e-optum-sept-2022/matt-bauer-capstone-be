//How JsonWebTokens are used in this project:
//   When client begins survey a userId is generated and sent to back end
//   userid is stored in a generated jwt as a claim and the jwt is returned to front end to be stored in service
//   If client cancels survey before completion jwt is sent in back end and jwt in memory is removed
//   when client finishes survey jwt is sent with response to back end, jwt is decoded to see the stored userid matches userid
//   if matched, survey is added to database and jwt and user is removed from memory
//   if not matched, an error is thrown to front end

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
    private class UserSession {
        String userId;
        Key key;
        String jwt;
    }

    private ArrayList<UserSession> activeUsers = new ArrayList<>();

    public String createJws(String userId) {
        //secret is a random 256+ bit string created by combining 3 UUIDs and removing "-"s
        String secret = JsonWebTokenService.getNewSecret();
        //uses secret as encoded string to generate a Key object with the HS256 algorithm
        Key key = this.getNewKey(secret);
        //Key is used to generate/decode jwt, userId is added as jwt claim for validation later
        String jwt = this.getNewJwt(key, userId);
        //object of userid, jwt, and key is added to array to store active users taking a survey
        this.addActiveUser(key, userId, jwt);
        return jwt;
    }

    private static String getNewSecret() {
        return (UUID.randomUUID().toString() + UUID.randomUUID().toString() +
                UUID.randomUUID().toString()).replaceAll("-", "");
    }

    private Key getNewKey(String secret) {
        return new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
    }

    private String getNewJwt(Key key, String userSessionId) {
        Instant now = Instant.now();
        Long minutesValid = 2L;
        return Jwts.builder()
                // claim id is set to userid
                .setId(userSessionId)
                //issued now
                .setIssuedAt(Date.from(now))
                //valid for minutesValid
                .setExpiration(Date.from(now.plus(minutesValid, ChronoUnit.MINUTES)))
                .signWith(key)
                .compact();
    }

    private void addActiveUser(Key key, String id, String jwt) {
        UserSession newUser = new UserSession();
        newUser.userId = id;
        newUser.jwt = jwt;
        newUser.key = key;
        this.activeUsers.add(0, newUser);
    }


    public boolean validateUserResponse(String jwt) {
        //check if the jwt returned on finished survey matches jwt field in any UserSession object in active user field
        UserSession userToValidate = this.activeUsers.stream().filter(user -> jwt.equals(user.jwt)).findFirst().orElse(null);
        if (userToValidate != null) {
            //decodes jwt using key into a Jws<Claims> object that can read the data inside the different claims
            Jws<Claims> parsedJwt = Jwts.parserBuilder().setSigningKey(userToValidate.key).build().parseClaimsJws(jwt);
            //if the id claim is the same as the UserSession object userId field, the jwt is validated and the survey response is accepted and added to database
            if (parsedJwt.getBody().getId().equals(userToValidate.userId))
                return true;
            //if it returns false jwt not validated, survey is rejected and client receives a 401 error code
            else return false;
        }
        return false;
    }

    public void endUserSession(String jwt) {
        //finds the UserSession object associated with the jwt and removes it from the active user list
        UserSession userSessionToEnd = this.activeUsers.stream().filter(user -> jwt.equals(user.jwt)).findFirst().orElse(null);
        if (userSessionToEnd != null)
            this.activeUsers.remove(userSessionToEnd);
    }
}




//Old code used to explore how to use JWTs

//   String secret2 = (UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString()).replaceAll("-", "");
//        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
//        System.out.println(secret2);
//
////        byte[] keyBytes = Decoders.BASE64.decode(secret);
////        System.out.println("Keybytes: " + keyBytes.toString());
////        Key key = Keys.hmacShaKeyFor(keyBytes);
//
//
//        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret2),
//                SignatureAlgorithm.HS256.getJcaName());
//
//        System.out.println(hmacKey);
//
//        Instant now = Instant.now();
//        String jwtToken = Jwts.builder()
//                .claim("name", "Jane Doe")
//                .claim("email", "jane@example.com")
//                .setSubject("jane")
//                .setId(UUID.randomUUID().toString())
//                .setIssuedAt(Date.from(now))
//                .setExpiration(Date.from(now.plus(5L, ChronoUnit.MINUTES)))
//                .signWith(hmacKey)
//                .compact();
//
////        String jwtString = Jwts.builder().setSubject("Joe").signWith(SignatureAlgorithm.HS512, hmacKey).compact();
//
//        System.out.println(jwtToken);
//
//        Jws<Claims> jwt = Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(jwtToken);
//
//        System.out.println(jwt);
//
//        System.out.println("New Secret String:" + JsonWebToken.getNewSecret());
//
//        //assert Jwts.parser().setSigningKey(hmacKey).parseClaimsJwt(jwtString).getBody().getSubject().equals("Joe");
//
////  Object secret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
////        System.out.println("key: " + key.toString());
////
////        String jws = Jwts.builder().setIssuer("Stormpath")
////                .setSubject("msilverman").claim("name", "Micha Silverman")
////                .claim("scope", "admins").setIssuedAt(Date.from(Instant.ofEpochSecond(1466796822L)))
////                .setExpiration(Date.from(Instant.ofEpochSecond(4622470422L)))
////                .signWith(key).compact();
////
////        System.out.println(jws);

