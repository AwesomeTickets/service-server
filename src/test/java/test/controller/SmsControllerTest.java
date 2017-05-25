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

    @Test
    @Ignore
    public void testSendSms() throws Exception {
        // PHONE_INVALID_FORMAT
        mockMvc.perform(get(URI_SMS_GET, TEST_PHONE_INVALID))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(100));
        // Success
        mockMvc.perform(get(URI_SMS_GET, TEST_PHONE_USER))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.phoneNum").value(TEST_PHONE_USER));
    }

    @Test
    public void testVerifySms() throws Exception {
        // PHONE_INVALID_FORMAT
        mockMvc.perform(post(URI_SMS_VERIFY, TEST_PHONE_INVALID)
            .param("code", TEST_SMS_CODE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(100));
        // Success
        mockMvc.perform(post(URI_SMS_VERIFY, TEST_PHONE_USER)
            .param("code", TEST_SMS_CODE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.phoneNum").value(TEST_PHONE_USER));
    }
}
