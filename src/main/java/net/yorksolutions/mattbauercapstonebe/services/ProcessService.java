package net.yorksolutions.mattbauercapstonebe.services;

import net.yorksolutions.mattbauercapstonebe.modules.Process;
import net.yorksolutions.mattbauercapstonebe.repositories.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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
        try {
            return this.processRepository.save(process);
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public Iterable<Process> getAll() {
        return this.processRepository.findAll();
    }

    public Process update(Process process) {
        Optional<Process> processOpt = this.processRepository.findById(process.getId());
        if (processOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Process dbProcess = processOpt.get();
        if (dbProcess.getTitle().equals(process.getTitle())) {
            return this.processRepository.save(process);
        } else try {
            dbProcess.setTitle(process.getTitle());
            return this.processRepository.save(dbProcess);
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public void delete(long id) {
        Optional<Process> processOpt = this.processRepository.findById(id);
        if (processOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Process dbProcess = processOpt.get();
        this.processRepository.delete(dbProcess);
    }

    //Dev purposes only
    public Process createTestSet(Process newProcess) {
        return this.processRepository.save(newProcess);
    }
}
