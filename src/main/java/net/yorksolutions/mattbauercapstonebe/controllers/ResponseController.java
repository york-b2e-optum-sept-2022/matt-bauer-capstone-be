package net.yorksolutions.mattbauercapstonebe.controllers;

import net.yorksolutions.mattbauercapstonebe.dtos.JwtDTO;
import net.yorksolutions.mattbauercapstonebe.dtos.ResponseDTO;
import net.yorksolutions.mattbauercapstonebe.modules.FinishedProcess;
import net.yorksolutions.mattbauercapstonebe.modules.Process;
import net.yorksolutions.mattbauercapstonebe.modules.Response;
import net.yorksolutions.mattbauercapstonebe.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseDTO create(@RequestBody ResponseDTO process){
        return this.responseService.create(process);
    }

    @GetMapping
    public Iterable<FinishedProcess> getAll(){
        return this.responseService.getAll();
    }


    @PostMapping("/start")
    public JwtDTO startNewSurvey(@RequestBody String userId){
        return this.responseService.startNewSurvey(userId);
    }

    @PutMapping("/cancel")
    public void cancelResponse(@RequestBody String jwt){
        this.responseService.cancelResponse(jwt);
    }

    //Dev and test purposes only
    @PostMapping("/load-test")
    public FinishedProcess createTestData(@RequestBody FinishedProcess newProcess){
        return this.responseService.createTestSet(newProcess);
    }
}

//    @GetMapping("/jwt-test")
//    public void getJws(){
//        this.responseService.createJws();
//    }
