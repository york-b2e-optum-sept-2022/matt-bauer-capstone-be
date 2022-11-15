package net.yorksolutions.mattbauercapstonebe.repositories;

import net.yorksolutions.mattbauercapstonebe.modules.FinishedProcess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends CrudRepository<FinishedProcess, Long> {
}
