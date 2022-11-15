package net.yorksolutions.mattbauercapstonebe.services;

import net.yorksolutions.mattbauercapstonebe.modules.Process;
import net.yorksolutions.mattbauercapstonebe.repositories.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessService {

    ProcessRepository processRepository;

    @Autowired
    public ProcessService(ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    public Process create(String newProcessTitle) {
        Process process = new Process();
        process.title = newProcessTitle;
        return this.processRepository.save(process);
    }

    public Iterable<Process> getAll() {
        return this.processRepository.findAll();
    }

    public Process update(Process process) {
        return this.processRepository.save(process);
    }
}
