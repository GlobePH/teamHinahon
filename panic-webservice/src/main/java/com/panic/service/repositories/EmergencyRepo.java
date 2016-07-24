package com.panic.service.repositories;

import com.panic.service.model.EmergencyType;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Created by tabs on 7/23/16.
 */
@Transactional
public interface EmergencyRepo extends JpaRepository<EmergencyType, Long> {

    EmergencyType findByName(String name);
}
