package net.yorksolutions.mattbauercapstonebe.services;

import io.jsonwebtoken.*;
import net.yorksolutions.mattbauercapstonebe.JsonWebToken;
import net.yorksolutions.mattbauercapstonebe.ResponseDTO;
import net.yorksolutions.mattbauercapstonebe.modules.FinishedProcess;
import net.yorksolutions.mattbauercapstonebe.repositories.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

@Service
public class ResponseService {

    ResponseRepository responseRepository;
    JsonWebToken jsonWebToken;

    @Autowired
    public ResponseService(ResponseRepository responseRepository, JsonWebToken jsonWebToken) {

        this.responseRepository = responseRepository;
        this.jsonWebToken = jsonWebToken;
    }

    public FinishedProcess create(ResponseDTO process) {
        if (this.jsonWebToken.validateUserResponse(process.jwt)) {
            this.jsonWebToken.endUserSession(process.jwt);
            return this.responseRepository.save(process.response);
        }
        return new FinishedProcess();
        //TODO return with throw error
    }

    public Iterable<FinishedProcess> getAll() {
        return this.responseRepository.findAll();
    }

    public void createJws() {
        String userId = UUID.randomUUID().toString();
        System.out.println("user id: " + userId);
        String secret = JsonWebToken.getNewSecret();
        Key key = this.jsonWebToken.getNewKey(secret);
        String jwt = this.jsonWebToken.getNewJwt(key, userId);
        System.out.println(jwt);
        this.jsonWebToken.addActiveUser(key, userId, jwt);
        System.out.println("validate token");
        System.out.println(this.jsonWebToken.validateUserResponse(jwt));
        if (this.jsonWebToken.validateUserResponse(jwt))
            this.jsonWebToken.endUserSession(jwt);
    }

    public String startNewSurvey(String userId) {
        String secret = JsonWebToken.getNewSecret();
        Key key = this.jsonWebToken.getNewKey(secret);
        String jwt = this.jsonWebToken.getNewJwt(key, userId);
        this.jsonWebToken.addActiveUser(key, userId, jwt);
        System.out.println(jwt);
        return jwt;
    }

    public ResponseDTO updateCurrentResponse(ResponseDTO response) {

        return response;
    }

    public void cancelResponse(String jwt) {
        this.jsonWebToken.endUserSession(jwt);
    }
}


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
