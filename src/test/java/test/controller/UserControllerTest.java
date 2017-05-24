package test.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MvcResult;
import test.RestSessionControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends RestSessionControllerTest {

    @Test
    public void testLoginLogout() throws Exception {
        final String loginURL = "/resource/session";
        final String logoutURL = "/resource/session/drop";
        // Logout
        mockMvc.perform(post(logoutURL)
            .param("phoneNum", TEST_PHONE_USER)
            .session(session))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.phoneNum").value(TEST_PHONE_USER));
        assertExpired(true);
        // Login
        MvcResult res = mockMvc.perform(post(loginURL)
            .param("phoneNum", TEST_PHONE_USER)
            .param("password", TEST_PASSWORD_VALID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.phoneNum").value(TEST_PHONE_USER))
            .andReturn();
        session = (MockHttpSession) res.getRequest().getSession(false);
        assertSession(session);
        assertExpired(false);
    }

    @Test
    public void testRegisterFailures() throws Exception {
        final String url = "/resource/user";
        // PHONE_INVALID_FORMAT
        mockMvc.perform(post(url)
            .param("phoneNum", TEST_PHONE_INVALID)
            .param("password", TEST_PASSWORD_VALID)
            .param("smsCode", TEST_SMS_CODE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(100));
        // PASSWORD_INVALID_FORMAT
        mockMvc.perform(post(url)
            .param("phoneNum", TEST_PHONE_USER)
            .param("password", TEST_PASSWORD_INVALID)
            .param("smsCode", TEST_SMS_CODE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(400));
        // SMS_MISMATCH
        mockMvc.perform(post(url)
            .param("phoneNum", TEST_PHONE_USER)
            .param("password", TEST_PASSWORD_VALID)
            .param("smsCode", "000000"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(102));
        // PHONE_REGISTERED
        mockMvc.perform(post(url)
            .param("phoneNum", TEST_PHONE_USER)
            .param("password", TEST_PASSWORD_VALID)
            .param("smsCode", TEST_SMS_CODE))
            .andExpect(status().isForbidden())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(401));
    }

    @Test
    public void testLoginFailures() throws Exception {
        final String url = "/resource/session";
        // PHONE_INVALID_FORMAT
        mockMvc.perform(post(url)
            .param("phoneNum", TEST_PHONE_INVALID)
            .param("password", TEST_PASSWORD_VALID))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(100));
        // PASSWORD_INVALID_FORMAT
        mockMvc.perform(post(url)
            .param("phoneNum", TEST_PHONE_USER)
            .param("password", TEST_PASSWORD_INVALID))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(400));
        // USER_NOT_FOUND
        mockMvc.perform(post(url)
            .param("phoneNum", TEST_PHONE_NOT_EXISTS)
            .param("password", TEST_PASSWORD_VALID))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(202));
        // PASSWORD_MISMATCH
        mockMvc.perform(post(url)
            .param("phoneNum", TEST_PHONE_USER)
            .param("password", "aa1111"))
            .andExpect(status().isForbidden())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(402));
    }

    @Test
    public void testLogoutFailures() throws Exception {
        final String url = "/resource/session/drop";
        // PHONE_INVALID_FORMAT
        mockMvc.perform(post(url)
            .param("phoneNum", TEST_PHONE_INVALID)
            .session(session))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(100));
        // SESSION_NOT_FOUND
        mockMvc.perform(post(url)
            .param("phoneNum", TEST_PHONE_USER))
            .andExpect(status().isForbidden())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(403));
    }

    private void assertExpired(boolean predicate) throws Exception {
        mockMvc.perform(get("/resource/session/check")
            .session(session))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.expire").value(predicate));
    }
}
