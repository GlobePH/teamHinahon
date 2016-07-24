package com.panic.service.controller;

import com.panic.service.model.Consideration;
import com.panic.service.model.EmergencyType;
import com.panic.service.services.ConsiderationService;
import com.panic.service.services.EmergencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by tabs on 7/23/16.
 */
@RestController
@RequestMapping("/{emergencyTypeId}/considerations")
public class ConsiderationController {

    private static final Logger logger = LoggerFactory
            .getLogger(ConsiderationController.class);

    @Autowired
    private ConsiderationService considerationService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Consideration> all(@PathVariable("emergencyTypeId") Long emergencyTypeId) {
        logger.info("EMERGENCY TYPE: " + emergencyTypeId);

        return considerationService.findAll(emergencyTypeId);
    }
}
