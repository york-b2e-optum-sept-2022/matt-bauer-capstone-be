package net.yorksolutions.mattbauercapstonebe.services;

import net.yorksolutions.mattbauercapstonebe.modules.Process;
import net.yorksolutions.mattbauercapstonebe.repositories.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProcessService {

    ProcessRepository processRepository;

    @Autowired
    public ProcessService(ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    public Process create(String newProcessTitle) {
        Process process = new Process();
        process.setTitle(newProcessTitle);
        try{
            return this.processRepository.save(process);
        }
        catch (RuntimeException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public Iterable<Process> getAll() {
        return this.processRepository.findAll();
    }

    public Process update(Process process) {
        try{
            return this.processRepository.save(process);
        }
        catch (RuntimeException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public void delete(long id) {
        this.processRepository.deleteById(id);
    }
}
