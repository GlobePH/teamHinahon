package com.panic.service.controller;

import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.panic.service.MessageType;
import com.panic.service.model.Consideration;
import com.panic.service.model.EmergencyType;
import com.panic.service.model.Reporter;
import com.panic.service.model.Responder;
import com.panic.service.repositories.ReporterRepo;
import com.panic.service.repositories.ResponderRepo;
import com.panic.service.services.ConsiderationService;
import com.panic.service.services.EmergencyService;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jean Benjamin Bayona on 7/23/16.
 */
@RestController
public class ReceiverController {

    private static final Logger logger = LoggerFactory
            .getLogger(ReceiverController.class);

    @Autowired
    ResponderRepo responderRepo;

    @Autowired
    ReporterRepo reporterRepo;

    @Autowired
    private EmergencyService emergencyService;

    @Autowired
    private ConsiderationService considerationService;

    @RequestMapping(value = "/callback")
    public String redirectUrl(@RequestParam(value = "code", required = false) String code,
                              @RequestParam(value = "access_token", required = false) String accessToken,
                              @RequestParam(value = "subscriber_number", required = false) String subscriber_number) {

        String s = "code: " + code + "\n" + "accessToken: " + accessToken + "\n" + "subscriber_number: " + subscriber_number;
        logger.info("CALLBACK_RESPONSE: " + s);
        return s;
    }

    @RequestMapping(value = "/notify")
    public void notifyUrl(@RequestBody Map<String, Object> payload) throws Exception {
        logger.info("PAYLOAD: " + payload);
        //TODO: do something here...!!

        Map<String, Object> msg = (Map<String, Object>) payload.get("inboundSMSMessageList");
        ArrayList<Object> msgList = (ArrayList) msg.get("inboundSMSMessage");
        Map<String, Object> msgAttr = (Map<String, Object>) msgList.get(0);

        String sender = msgAttr.get("senderAddress").toString();
        String msisdn = sender.substring(sender.length() - 10, sender.length());
        String msgContent = msgAttr.get("message").toString();

        logger.info("MSISDN: " + msisdn);
        Reporter reporter = reporterRepo.findByMsisdn(msisdn);
        logger.info("REPORTER : " + JSON.toJSONString(reporter));
        logger.info("EMERGENCY TYPE: " + msgContent);

        String reporterLocation = getLocation(reporter.getAccessToken(), reporter.getMsisdn(), "10");
        String reporterReverseGeocodedLocation = parseLocationToAddress(reporterLocation);

        ArrayList<Responder> responders = (ArrayList<Responder>) responderRepo.findAll();

        Responder responder = findNearestResponder(reporterLocation, responders);

        MessageType messageType = JSON.parseObject(msgContent, MessageType.class);

        if (messageType.getSource().equalsIgnoreCase("citizen")) {
            String strEmergencyType = messageType.getEmergencyType();
            logger.info("STR EMERGENCY TYPE: " + strEmergencyType);
            EmergencyType eType = emergencyService.findByName(messageType.getEmergencyType());


            if (eType != null) {
                List<String> listOfConsiderations = new ArrayList<>();

                for (Consideration con: considerationService.findAll(eType.getId())) {
                    logger.info("con: " + con.getName());
                    listOfConsiderations.add(con.getName());
                }

                logger.info("CONSIDERATIONS: " + listOfConsiderations);
            } else {

            }
        }

        logger.info("MESSAGE TYPE: " + JSON.toJSONString(messageType));


        sendSMS(responder, messageType.getEmergencyType(), reporterReverseGeocodedLocation);

    }

    private void sendSMS(Responder responder, String message, String address) throws Exception {
        logger.info("RESPONDER: " + JSON.toJSONString(responder));

        String messageTemplate = "{ 'type': '%s', 'location': '%s' }";
        String messageContent = String.format(messageTemplate, message, address);

        logger.info("MESSAGE CONTENT: " + messageContent);

        String url = "https://devapi.globelabs.com.ph/smsmessaging/v1/outbound/21586684/requests";

        HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                .header("Content-Type", "application/json")
                .queryString("access_token", responder.getAccessToken())
                .body("{ \"outboundSMSMessageRequest\": { \"clientCorrelator\": \"123456\", \"senderAddress\": \"tel:21586684\", \"outboundSMSTextMessage\": { \"message\": \"" + messageContent + "\" }, \"address\": [ \"tel:+63" + responder.getMsisdn() + "\" ] } }")
                .asJson();

        logger.info("SEND_JSON_RESPONSE: " + jsonResponse.getBody().toString());
    }

    public static JsonNode callLocAPI(String accessToken, String msisdn, String accuracy) throws UnirestException{
        String locationAPI = "https://devapi.globelabs.com.ph/location/v1/queries/location?access_token=" + accessToken +
                             "&address=" + msisdn +
                             "&requestedAccuracy=" + accuracy;

        HttpResponse<JsonNode> jsonResponse = Unirest.get(locationAPI)
                .header("Content-Type", "application/json")
                .asJson();

        return jsonResponse.getBody();
    }

    public String getLocation(String accessToken, String msisdn, String accuracy) throws UnirestException, JSONException {
        JSONObject response = callLocAPI(accessToken, msisdn, accuracy).getObject();
        JSONObject terminalLocationList = response.getJSONObject("terminalLocationList");
        JSONObject terminalLocation = terminalLocationList.getJSONObject("terminalLocation");
        JSONObject currentLocation = terminalLocation.getJSONObject("currentLocation");
        String latitude = currentLocation.getString("latitude");
        String longitude = currentLocation.getString("longitude");

        logger.info("LAT: " + latitude);
        logger.info("LONG: " + longitude);

        return latitude + "," + longitude;
    }

    public Responder findNearestResponder(String reporter, ArrayList<Responder> responders) throws Exception {
        Responder responder = null;
        double distanceHolder = 0;

        for (int i = 0; i < responders.size(); i++) {
            String msisdn = responders.get(i).getMsisdn();
            String accessToken = responders.get(i).getAccessToken();

            String reporterLocation[] = reporter.split(",");
            String responderLocation[] = getLocation(accessToken, msisdn, "10").split(",");

            double reporterLocationX = Double.parseDouble(reporterLocation[0]);
            double reporterLocationY = Double.parseDouble(reporterLocation[1]);

            double responderLocationX = Double.parseDouble(responderLocation[0]);
            double responderLocationY = Double.parseDouble(responderLocation[1]);

            double distance = Math.sqrt(Math.pow((responderLocationX - reporterLocationX), 2) +
                                        Math.pow((responderLocationY - reporterLocationY), 2));

            if(responder == null){
                responder = responders.get(i);
                distanceHolder = distance;
            } else {
                if(distance > distanceHolder) {
                    distanceHolder = distance;
                    responder = responders.get(i);
                }
            }

        }

        return responder;
    }

    @RequestMapping(value = "/reverse")
    public String parseLocationToAddress(String location) throws Exception {
        String coordinates[] = location.split(",");
        String reverseGeocodingAPI = "http://nominatim.openstreetmap.org/reverse?format=json&lat=" + coordinates[0] + "&lon=" + coordinates[1] + "&zoom=18&addressdetails=1";

        HttpResponse<JsonNode> jsonResponse = Unirest.get(reverseGeocodingAPI)
                .header("Content-Type", "application/json")
                .asJson();

        JsonNode responseNode = jsonResponse.getBody();
        String displayName = responseNode.getObject().getString("display_name");
        logger.info("ADDRESS: " + displayName);
        return displayName;
    }
}
