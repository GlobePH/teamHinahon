package com.panic.service.services;

import com.panic.service.model.EmergencyType;
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
public class EmergencyService {

    @Autowired
    private EmergencyRepo repository;

    public List<EmergencyType> findAll() {
        return repository.findAll();
    }

    public EmergencyType findByName(String name) {
        return repository.findByName(name);
    }
}
