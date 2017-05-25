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
        // Logout
        mockMvc.perform(post(URI_USER_LOGOUT)
            .param("phoneNum", TEST_PHONE_USER)
            .session(session))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.phoneNum").value(TEST_PHONE_USER));
        assertExpired(true);
        // Login
        MvcResult res = mockMvc.perform(post(URI_USER_LOGIN)
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
    public void testQueryTicketHistory() throws Exception {
        // Buy four tickets
        MvcResult res1 = mockMvc.perform(post(URI_TICKET_BUY)
            .param("movieOnShowId", "1")
            .param("phoneNum", TEST_PHONE_USER)
            .param("seats", "1,1")
            .session(session))
            .andExpect(status().isOk())
            .andReturn();
        String ticketCode1 = objMapper.readValue(res1.getResponse().getContentAsString(),
            TicketControllerTest.BuyTicketResult.class).ticketCode;
        MvcResult res2 = mockMvc.perform(post(URI_TICKET_BUY)
            .param("movieOnShowId", "2")
            .param("phoneNum", TEST_PHONE_USER)
            .param("seats", "2,1,2,2")
            .session(session))
            .andExpect(status().isOk())
            .andReturn();
        String ticketCode2 = objMapper.readValue(res2.getResponse().getContentAsString(),
            TicketControllerTest.BuyTicketResult.class).ticketCode;
        MvcResult res3 = mockMvc.perform(post(URI_TICKET_BUY)
            .param("movieOnShowId", "3")
            .param("phoneNum", TEST_PHONE_USER)
            .param("seats", "3,1,3,2,3,3")
            .session(session))
            .andExpect(status().isOk())
            .andReturn();
        String ticketCode3 = objMapper.readValue(res3.getResponse().getContentAsString(),
            TicketControllerTest.BuyTicketResult.class).ticketCode;
        MvcResult res4 = mockMvc.perform(post(URI_TICKET_BUY)
            .param("movieOnShowId", "4")
            .param("phoneNum", TEST_PHONE_USER)
            .param("seats", "4,1,4,2,4,3,4,4")
            .session(session))
            .andExpect(status().isOk())
            .andReturn();
        String ticketCode4 = objMapper.readValue(res4.getResponse().getContentAsString(),
            TicketControllerTest.BuyTicketResult.class).ticketCode;
        // Invalidate ticket 2 and 4
        mockMvc.perform(post(URI_TICKET_CHECK)
            .param("ticketCode", ticketCode2)
            .param("phoneNum", TEST_PHONE_USER))
            .andExpect(status().isOk());
        mockMvc.perform(post(URI_TICKET_CHECK)
            .param("ticketCode", ticketCode4)
            .param("phoneNum", TEST_PHONE_USER))
            .andExpect(status().isOk());
        // Check history
        mockMvc.perform(get(URI_USER_HISTORY, TEST_PHONE_USER)
            .session(session))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.count").value(4))
            .andExpect(jsonPath("$.data").value(Matchers.hasSize(4)))

            .andExpect(jsonPath("$.data[0].*").value(Matchers.hasSize(4)))
            .andExpect(jsonPath("$.data[0].code").value(ticketCode3))
            .andExpect(jsonPath("$.data[0].valid").value(true))
            .andExpect(jsonPath("$.data[0].movieOnShowId").value(3))
            .andExpect(jsonPath("$.data[0].seats").value(Matchers.hasSize(3)))
            .andExpect(jsonPath("$.data[0].seats[0][0]").value(3))
            .andExpect(jsonPath("$.data[0].seats[1][0]").value(3))
            .andExpect(jsonPath("$.data[0].seats[2][0]").value(3))

            .andExpect(jsonPath("$.data[1].*").value(Matchers.hasSize(4)))
            .andExpect(jsonPath("$.data[1].code").value(ticketCode1))
            .andExpect(jsonPath("$.data[1].valid").value(true))
            .andExpect(jsonPath("$.data[1].movieOnShowId").value(1))
            .andExpect(jsonPath("$.data[1].seats").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.data[1].seats[0][0]").value(1))

            .andExpect(jsonPath("$.data[2].*").value(Matchers.hasSize(4)))
            .andExpect(jsonPath("$.data[2].code").value(ticketCode4))
            .andExpect(jsonPath("$.data[2].valid").value(false))
            .andExpect(jsonPath("$.data[2].movieOnShowId").value(4))
            .andExpect(jsonPath("$.data[2].seats").value(Matchers.hasSize(4)))
            .andExpect(jsonPath("$.data[2].seats[0][0]").value(4))
            .andExpect(jsonPath("$.data[2].seats[1][0]").value(4))
            .andExpect(jsonPath("$.data[2].seats[2][0]").value(4))
            .andExpect(jsonPath("$.data[2].seats[3][0]").value(4))

            .andExpect(jsonPath("$.data[3].*").value(Matchers.hasSize(4)))
            .andExpect(jsonPath("$.data[3].code").value(ticketCode2))
            .andExpect(jsonPath("$.data[3].valid").value(false))
            .andExpect(jsonPath("$.data[3].movieOnShowId").value(2))
            .andExpect(jsonPath("$.data[3].seats").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.data[3].seats[0][0]").value(2))
            .andExpect(jsonPath("$.data[3].seats[1][0]").value(2));
    }

    @Test
    public void testRegisterFailures() throws Exception {
        // PHONE_INVALID_FORMAT
        mockMvc.perform(post(URI_USER_REGISTER)
            .param("phoneNum", TEST_PHONE_INVALID)
            .param("password", TEST_PASSWORD_VALID)
            .param("smsCode", TEST_SMS_CODE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(100));
        // PASSWORD_INVALID_FORMAT
        mockMvc.perform(post(URI_USER_REGISTER)
            .param("phoneNum", TEST_PHONE_USER)
            .param("password", TEST_PASSWORD_INVALID)
            .param("smsCode", TEST_SMS_CODE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(400));
        // SMS_MISMATCH
        mockMvc.perform(post(URI_USER_REGISTER)
            .param("phoneNum", TEST_PHONE_USER)
            .param("password", TEST_PASSWORD_VALID)
            .param("smsCode", "000000"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(102));
        // PHONE_REGISTERED
        mockMvc.perform(post(URI_USER_REGISTER)
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
        // PHONE_INVALID_FORMAT
        mockMvc.perform(post(URI_USER_LOGIN)
            .param("phoneNum", TEST_PHONE_INVALID)
            .param("password", TEST_PASSWORD_VALID))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(100));
        // PASSWORD_INVALID_FORMAT
        mockMvc.perform(post(URI_USER_LOGIN)
            .param("phoneNum", TEST_PHONE_USER)
            .param("password", TEST_PASSWORD_INVALID))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(400));
        // USER_NOT_FOUND
        mockMvc.perform(post(URI_USER_LOGIN)
            .param("phoneNum", TEST_PHONE_NOT_EXISTS)
            .param("password", TEST_PASSWORD_VALID))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(202));
        // PASSWORD_MISMATCH
        mockMvc.perform(post(URI_USER_LOGIN)
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
        // PHONE_INVALID_FORMAT
        mockMvc.perform(post(URI_USER_LOGOUT)
            .param("phoneNum", TEST_PHONE_INVALID)
            .session(session))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(100));
        // SESSION_NOT_FOUND
        mockMvc.perform(post(URI_USER_LOGOUT)
            .param("phoneNum", TEST_PHONE_USER))
            .andExpect(status().isForbidden())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(403));
    }

    private void assertExpired(boolean predicate) throws Exception {
        mockMvc.perform(get(URI_USER_SESSION_CHECK)
            .session(session))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.expire").value(predicate));
    }
}
