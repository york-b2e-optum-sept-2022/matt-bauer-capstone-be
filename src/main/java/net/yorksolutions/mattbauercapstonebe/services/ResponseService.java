package net.yorksolutions.mattbauercapstonebe.services;

import net.yorksolutions.mattbauercapstonebe.dtos.JwtDTO;
import net.yorksolutions.mattbauercapstonebe.dtos.ResponseDTO;
import net.yorksolutions.mattbauercapstonebe.modules.FinishedProcess;
import net.yorksolutions.mattbauercapstonebe.repositories.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ResponseService {

    ResponseRepository responseRepository;
    JsonWebTokenService jsonWebToken;

    @Autowired
    public ResponseService(ResponseRepository responseRepository, JsonWebTokenService jsonWebToken) {

        this.responseRepository = responseRepository;
        this.jsonWebToken = jsonWebToken;
    }
    public JwtDTO startNewSurvey(String userId) {
        JwtDTO jwtResponse = new JwtDTO();
        jwtResponse.jwt = this.jsonWebToken.createJws(userId);
        return jwtResponse;
    }
    public void cancelResponse(String jwt) {
        this.jsonWebToken.endUserSession(jwt);
    }

    public ResponseDTO create(ResponseDTO process) {
        if (this.jsonWebToken.validateUserResponse(process.jwt)) {
            this.jsonWebToken.endUserSession(process.jwt);
            this.responseRepository.save(process.response);
            return process;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public Iterable<FinishedProcess> getAll() {
        return this.responseRepository.findAll();
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
