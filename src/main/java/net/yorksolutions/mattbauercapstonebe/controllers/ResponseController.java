package net.yorksolutions.mattbauercapstonebe.controllers;

import net.yorksolutions.mattbauercapstonebe.ResponseDTO;
import net.yorksolutions.mattbauercapstonebe.modules.FinishedProcess;
import net.yorksolutions.mattbauercapstonebe.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/response")
@CrossOrigin
public class ResponseController {

    ResponseService responseService;

    @Autowired
    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @PostMapping
    public FinishedProcess create(@RequestBody ResponseDTO process){
        return this.responseService.create(process);
    }

    @GetMapping
    public Iterable<FinishedProcess> getAll(){
        return this.responseService.getAll();
    }

    @GetMapping("/jwt-test")
    public void getJws(){
        this.responseService.createJws();
    }

    @PostMapping("/start")
    public String startNewSurvey(@RequestBody String userId){
        return this.responseService.startNewSurvey(userId);
    }

    @PutMapping()
    public ResponseDTO updateCurrentResponse(@RequestBody ResponseDTO response){
        return this.responseService.updateCurrentResponse(response);
    }

    @DeleteMapping("/cancel")
    public void cancelResponse(@RequestBody String jwt){
        this.responseService.cancelResponse(jwt);
    }

}

