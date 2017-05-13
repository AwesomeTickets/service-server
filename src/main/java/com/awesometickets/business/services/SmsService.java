package com.awesometickets.business.services;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * Manage sms and captcha verification.
 */
public class SmsService {
    private static final Logger Log = LoggerFactory.getLogger(SmsService.class);
    private static final String URL_ROOT = "https://api.leancloud.cn/1.1";
    private static final String URL_REQ_SMS = URL_ROOT + "/requestSmsCode";
    private static final String URL_VRF_SMS = URL_ROOT + "/verifySmsCode";
    private static final SmsService instance = new SmsService();
    private RestTemplate restTemplate;
    private ObjectMapper mapper;
    private HttpHeaders headers;

    /**
     * Return the only {@code SmsService} instance.
     */
    public static SmsService getInstance() {
        return instance;
    }

    private SmsService() {}

    /**
     * Send verification sms code.
     *
     * @param phone The phone number to receive sms code
     * @return True if sms code is sent successfully
     */
    public boolean sendSmsCode(String phone) {
        try {
            String body = new ReqSmsParam(phone).toJSON();
            Log.info("sendSmsCode() req body: " + body);
            HttpEntity<String> reqEntity = new HttpEntity<String>(body, headers);
            String res = restTemplate.postForObject(URL_REQ_SMS, reqEntity, String.class);
            Log.info("sendSmsCode() res: " + res);
            return true;
        } catch (HttpClientErrorException e) {
            Log.error("sendSmsCode() res: " + e.getMessage());
            Log.error("sendSmsCode() res: " + e.getResponseBodyAsString());
            return false;
        }
    }

    /**
     * Verify sms code.
     *
     * @param phone The phone number to receive sms code
     * @param code The sms code
     * @return True if sms code is verified successfully
     */
    public boolean verifySmsCode(String phone, String code) {
        try {
            String url = URL_VRF_SMS + "/" + code + "?mobilePhoneNumber=" + phone;
            Log.info("verifySmsCode() url: " + url);
            HttpEntity<String> reqEntity = new HttpEntity<String>(headers);
            String res = restTemplate.postForObject(url, reqEntity, String.class);
            Log.info("verifySmsCode() res: " + res);
            return true;
        } catch (HttpClientErrorException e) {
            Log.error("verifySmsCode() res: " + e.getMessage());
            Log.error("verifySmsCode() res: " + e.getResponseBodyAsString());
            return false;
        }
    }

    /**
     * Initialize a {@code SmsService} instance.
     *
     * @param id The LC id
     * @param key The LC key
     */
    public void init(String id, String key) {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converter.setWriteAcceptCharset(false);
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, converter);
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        headers = new HttpHeaders();
        headers.add("X-LC-Id", id);
        headers.add("X-LC-Key", key);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    /**
     * Parameters of sending sms request.
     */
    private class ReqSmsParam {
        private String mobilePhoneNumber;
        private int ttl = 2;
        private String op = "手机号验证";

        private ReqSmsParam(String phone) {
            mobilePhoneNumber = phone;
        }

        private String toJSON() {
            try {
                return mapper.writeValueAsString(this);
            } catch (JsonProcessingException e) {
                return null;
            }
        }
    }
}