package com.panic.service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Jean Benjamin Bayona on 7/23/16.
 */
@RestController
public class SenderController {

    @RequestMapping(value = "/send")
    public String sendSMS() {

        String callback = "http://devapi.globelabs.com.ph/smsmessaging/v1/outbound/4775/requests?access_token=[YOUR ACCESS TOKEN]";

        RestTemplate restTemplate = new RestTemplate();
        String result =  restTemplate.getForObject(callback, String.class);

        return result;
        //4775
    }
}
