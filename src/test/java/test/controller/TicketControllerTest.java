package test.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import test.RestSessionControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TicketControllerTest extends RestSessionControllerTest {

    public static class BuyTicketResult {
        private int movieOnShowId;
        private int[][] seats;
        private String phoneNum;
        private String ticketCode;
    }

    @Test
    public void testBuyTicket() throws Exception {
        // BAD_REQUEST
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", TEST_PHONE_USER)
            .param("seats", "1,1,0")
            .session(session))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(0));
        // SESSION_NOT_FOUND
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", TEST_PHONE_USER)
            .param("seats", "1,1,1,2,1,3"))
            .andExpect(status().isForbidden())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(403));
        // No exception
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", TEST_PHONE_USER)
            .param("seats", "1,1,1,2,1,3")
            .session(session))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(4)))
            .andExpect(jsonPath("$.movieOnShowId").value(1))
            .andExpect(jsonPath("$.seats").value(Matchers.hasSize(3)))
            .andExpect(jsonPath("$.seats[0][0]").value(1))
            .andExpect(jsonPath("$.seats[0][1]").value(1))
            .andExpect(jsonPath("$.seats[1][0]").value(1))
            .andExpect(jsonPath("$.seats[1][1]").value(2))
            .andExpect(jsonPath("$.seats[2][0]").value(1))
            .andExpect(jsonPath("$.seats[2][1]").value(3))
            .andExpect(jsonPath("$.phoneNum").value(TEST_PHONE_USER))
            .andExpect(jsonPath("$.ticketCode").isString());
        // SEAT_UNAVAILABLE
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", TEST_PHONE_USER)
            .param("seats", "1,1,1,2,1,3")
            .session(session))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(200));
        // PHONE_INVALID_FORMAT
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", TEST_PHONE_INVALID)
            .param("seats", "1,1,1,2,1,3")
            .session(session))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(100));
        // SEAT_NOT_FOUND
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", TEST_PHONE_USER)
            .param("seats", "1,1,0,0,1,3")
            .session(session))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(201));
    }

    @Test
    public void testCheckTicket() throws Exception {
        // Buy tickets first
        MvcResult res = mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", TEST_PHONE_USER)
            .param("seats", "2,1,2,2,2,3,2,4")
            .session(session))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn();
        String correctTicketCode = objMapper.readValue(res.getResponse().getContentAsString(),
            BuyTicketResult.class).ticketCode;
        // PHONE_INVALID_FORMAT
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_PHONE_INVALID))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(100));
        // TICKET_CODE_NOT_FOUND
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", "12345678910")
            .param("phoneNum", TEST_PHONE_USER))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(301));
        // USER_NOT_FOUND
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_PHONE_NOT_EXISTS))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(202));
        // PHONE_MISMATCH
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_PHONE_UNAVAILABLE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(300));
        // Check ticket
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_PHONE_USER))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(3)))
            .andExpect(jsonPath("$.movieOnShowId").value(1))
            .andExpect(jsonPath("$.seats").value(Matchers.hasSize(4)))
            .andExpect(jsonPath("$.seats[0][0]").value(2))
            .andExpect(jsonPath("$.seats[0][1]").value(1))
            .andExpect(jsonPath("$.seats[1][0]").value(2))
            .andExpect(jsonPath("$.seats[1][1]").value(2))
            .andExpect(jsonPath("$.seats[2][0]").value(2))
            .andExpect(jsonPath("$.seats[2][1]").value(3))
            .andExpect(jsonPath("$.seats[3][0]").value(2))
            .andExpect(jsonPath("$.seats[3][1]").value(4))
            .andExpect(jsonPath("$.phoneNum").value(TEST_PHONE_USER));
        // TICKET_CHECKED
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_PHONE_USER))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(302));
    }

    @Test
    public void testGetTicketInfo() throws Exception {
        // Buy tickets first
        MvcResult res = mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", TEST_PHONE_USER)
            .param("seats", "3,1")
            .session(session))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn();
        String correctTicketCode = objMapper.readValue(res.getResponse().getContentAsString(),
            BuyTicketResult.class).ticketCode;
        // PHONE_INVALID_FORMAT
        mockMvc.perform(post("/resource/ticket/info")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_PHONE_INVALID))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(100));
        // TICKET_CODE_NOT_FOUND
        mockMvc.perform(post("/resource/ticket/info")
            .param("ticketCode", "12345678910")
            .param("phoneNum", TEST_PHONE_USER))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(301));
        // USER_NOT_FOUND
        mockMvc.perform(post("/resource/ticket/info")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_PHONE_NOT_EXISTS))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(202));
        // PHONE_MISMATCH
        mockMvc.perform(post("/resource/ticket/info")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_PHONE_UNAVAILABLE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.info").isString())
            .andExpect(jsonPath("$.code").value(300));
        // Get info
        mockMvc.perform(post("/resource/ticket/info")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_PHONE_USER))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(4)))
            .andExpect(jsonPath("$.movieOnShowId").value(1))
            .andExpect(jsonPath("$.seats").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.seats[0][0]").value(3))
            .andExpect(jsonPath("$.seats[0][1]").value(1))
            .andExpect(jsonPath("$.valid").value(true))
            .andExpect(jsonPath("$.phoneNum").value(TEST_PHONE_USER));
        // Check ticket
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_PHONE_USER))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        // Get info
        mockMvc.perform(post("/resource/ticket/info")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_PHONE_USER))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(4)))
            .andExpect(jsonPath("$.movieOnShowId").value(1))
            .andExpect(jsonPath("$.seats").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.seats[0][0]").value(3))
            .andExpect(jsonPath("$.seats[0][1]").value(1))
            .andExpect(jsonPath("$.valid").value(false))
            .andExpect(jsonPath("$.phoneNum").value(TEST_PHONE_USER));
    }
}
