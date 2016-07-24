package com.panic.service.repositories;

import com.panic.service.model.Responder;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Jean Benjamin Bayona on 7/23/16.
 */
@Transactional
public interface ResponderRepo extends CrudRepository<Responder, Integer> {

    Responder findByMsisdn(String msisdn);
}
