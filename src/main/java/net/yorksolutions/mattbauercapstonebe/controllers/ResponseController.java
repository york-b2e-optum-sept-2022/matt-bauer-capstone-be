package net.yorksolutions.mattbauercapstonebe.controllers;

import net.yorksolutions.mattbauercapstonebe.modules.FinishedProcess;
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
    public FinishedProcess create(@RequestBody FinishedProcess process){
        return this.responseService.create(process);
    }

    @GetMapping
    public Iterable<FinishedProcess> getAll(){
        return this.responseService.getall();
    }


}

