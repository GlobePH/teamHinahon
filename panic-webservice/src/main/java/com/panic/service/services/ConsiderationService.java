package com.panic.service.services;

import com.panic.service.model.Consideration;
import com.panic.service.model.EmergencyType;
import com.panic.service.repositories.ConsiderationRepo;
import com.panic.service.repositories.EmergencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by tabs on 7/23/16.
 */
@Service
@Transactional
public class ConsiderationService {

    @Autowired
    private ConsiderationRepo repository;

    public List<Consideration> findAll(Long eTypeId) {
//        return repository.findAll();
        return repository.findByEmergencyTypeId(eTypeId);
    }

}
