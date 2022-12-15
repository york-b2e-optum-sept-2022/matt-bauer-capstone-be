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
        //generate a new jwt, add userid to active users, and return jwt to client
        jwtResponse.jwt = this.jsonWebToken.createJws(userId);
        return jwtResponse;
    }
    public void cancelResponse(String jwt) {
        //client has canceled survey before completion, remove active user associated with jwt
        this.jsonWebToken.endUserSession(jwt);
    }

    public ResponseDTO create(ResponseDTO process) {
        //validate if jwt received with finished survey holds the userId
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

