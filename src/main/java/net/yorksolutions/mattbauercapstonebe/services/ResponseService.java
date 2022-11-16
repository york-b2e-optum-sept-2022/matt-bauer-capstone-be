package net.yorksolutions.mattbauercapstonebe.services;

import net.yorksolutions.mattbauercapstonebe.modules.FinishedProcess;
import net.yorksolutions.mattbauercapstonebe.repositories.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    ResponseRepository responseRepository;

    @Autowired
    public ResponseService(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    public FinishedProcess create(FinishedProcess process) {
        return this.responseRepository.save(process);
    }

    public Iterable<FinishedProcess> getall() {
        return this.responseRepository.findAll();
    }
}
