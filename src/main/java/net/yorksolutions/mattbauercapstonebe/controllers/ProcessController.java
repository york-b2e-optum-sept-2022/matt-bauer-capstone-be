package net.yorksolutions.mattbauercapstonebe.controllers;

import net.yorksolutions.mattbauercapstonebe.modules.Process;
import net.yorksolutions.mattbauercapstonebe.services.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/process")
@CrossOrigin
public class ProcessController {

    ProcessService processService;

    @Autowired
    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }

    @PostMapping
    public Process create(@RequestBody String newProcessTitle){
        return this.processService.create(newProcessTitle);
    }

    @GetMapping
    public Iterable<Process> getAll(){
        return this.processService.getAll();
    }

    @PutMapping
    public Process update(@RequestBody Process process){
        return this.processService.update(process);
    }

    @DeleteMapping
    public void delete(@RequestParam long id){
        this.processService.delete(id);
    }


}
