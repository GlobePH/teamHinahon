package com.panic.service.repositories;

import com.panic.service.model.Reporter;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;


/**
 * Created by Jean Benjamin Bayona on 7/23/16.
 */
@Transactional
public interface ReporterRepo extends CrudRepository<Reporter, Integer>{

    Reporter findByMsisdn(String msisdn);
}
