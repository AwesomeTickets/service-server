package test.controller;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import test.RestControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class SmsControllerTest extends RestControllerTest {
    private static final String TEST_INVALID_PHONE = "99912345678";
    private static final String TEST_VALID_PHONE = "15012901007";
    private static final String TEST_SMS_CODE = "739043";

    @Test
    @Ignore
    public void testSendSms() throws Exception {
        // PHONE_INVALID_FORMAT
        mockMvc.perform(get("/resource/sms/" + TEST_INVALID_PHONE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(100));
        // Success
        mockMvc.perform(get("/resource/sms/" + TEST_VALID_PHONE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.phoneNum").value(TEST_VALID_PHONE));
    }

    @Test
    public void testVerifySms() throws Exception {
        // PHONE_INVALID_FORMAT
        mockMvc.perform(post("/resource/sms/" + TEST_INVALID_PHONE + "/check")
            .param("code", TEST_SMS_CODE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(100));
        // Success
        mockMvc.perform(post("/resource/sms/" + TEST_VALID_PHONE + "/check")
            .param("code", TEST_SMS_CODE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.phoneNum").value(TEST_VALID_PHONE));
    }
}
