package com.panic.service.controller;

import com.panic.service.model.EmergencyType;
import com.panic.service.services.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by tabs on 7/23/16.
 */
@RestController
@RequestMapping("/emergency-types")
public class EmergencyController {

    @Autowired
    private EmergencyService emergencyService;

    @RequestMapping(method = RequestMethod.GET)
    public List<EmergencyType> all() {
        return emergencyService.findAll();
    }
}
