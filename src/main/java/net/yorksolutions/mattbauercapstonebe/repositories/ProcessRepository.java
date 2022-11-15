package net.yorksolutions.mattbauercapstonebe.repositories;

import net.yorksolutions.mattbauercapstonebe.modules.Process;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessRepository extends CrudRepository<Process, Long> {

}
