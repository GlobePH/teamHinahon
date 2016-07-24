package com.panic.service.repositories;

import com.panic.service.model.Consideration;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by tabs on 7/23/16.
 */
@Transactional
public interface ConsiderationRepo extends JpaRepository<Consideration, Long> {

    List<Consideration> findByEmergencyTypeId(Long eTypeId);
}
