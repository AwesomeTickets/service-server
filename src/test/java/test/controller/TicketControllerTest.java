package test.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import test.RestControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TicketControllerTest extends RestControllerTest {
    private static final String TEST_AVAILABLE_PHONE = "18812345678";
    private static final String TEST_UNAVAILABLE_PHONE = "18813572648";
    private static final String TEST_INVALID_PHONE = "99912345678";
    private static final String TEST_UNVERIFIED_PHONE = "18812345679";

    public static class BuyTicketResult {
        private int movieOnShowId;
        private int[][] seats;
        private String phoneNum;
        private String ticketCode;
    }

    @Test
    public void testBuyTicket() throws Exception {
        // No exception
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", TEST_AVAILABLE_PHONE)
            .param("seats", "1,1,1,2,1,3"))
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
            .andExpect(jsonPath("$.phoneNum").value(TEST_AVAILABLE_PHONE))
            .andExpect(jsonPath("$.ticketCode").isString());
        // SEAT_UNAVAILABLE
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", TEST_AVAILABLE_PHONE)
            .param("seats", "1,1,1,2,1,3"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(200));
        // PHONE_INVALID_FORMAT
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", TEST_INVALID_PHONE)
            .param("seats", "1,1,1,2,1,3"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(100));
        // SEAT_NOT_FOUND
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", TEST_AVAILABLE_PHONE)
            .param("seats", "1,1,0,0,1,3"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(201));
        // PHONE_NOT_VERIFIED
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", TEST_UNVERIFIED_PHONE)
            .param("seats", "1,1,1,2,1,3"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(202));
        // PURCHASE_UNAVAILABLE
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", TEST_UNAVAILABLE_PHONE)
            .param("seats", "1,1,1,2,1,3"))
            .andExpect(status().isForbidden())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(203));
    }

    @Test
    public void testCheckTicket() throws Exception {
        // Buy tickets first
        MvcResult res = mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", TEST_AVAILABLE_PHONE)
            .param("seats", "2,1,2,2,2,3,2,4"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn();
        String correctTicketCode = mapper.readValue(res.getResponse().getContentAsString(),
            BuyTicketResult.class).ticketCode;
        // PHONE_INVALID_FORMAT
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_INVALID_PHONE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(100));
        // TICKET_CODE_NOT_FOUND
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", "12345678910")
            .param("phoneNum", TEST_AVAILABLE_PHONE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(301));
        // PHONE_MISMATCH
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_UNVERIFIED_PHONE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(300));
        // Check ticket
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_AVAILABLE_PHONE))
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
            .andExpect(jsonPath("$.phoneNum").value(TEST_AVAILABLE_PHONE));
        // TICKET_CHECKED
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_AVAILABLE_PHONE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(302));
    }

    @Test
    public void testGetTicketInfo() throws Exception {
        // Buy tickets first
        MvcResult res = mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", TEST_AVAILABLE_PHONE)
            .param("seats", "3,1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn();
        String correctTicketCode = mapper.readValue(res.getResponse().getContentAsString(),
            BuyTicketResult.class).ticketCode;
        // PHONE_INVALID_FORMAT
        mockMvc.perform(post("/resource/ticket/info")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_INVALID_PHONE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(100));
        // TICKET_CODE_NOT_FOUND
        mockMvc.perform(post("/resource/ticket/info")
            .param("ticketCode", "12345678910")
            .param("phoneNum", TEST_AVAILABLE_PHONE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(301));
        // PHONE_MISMATCH
        mockMvc.perform(post("/resource/ticket/info")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_UNVERIFIED_PHONE))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(300));
        // Get info
        mockMvc.perform(post("/resource/ticket/info")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_AVAILABLE_PHONE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(4)))
            .andExpect(jsonPath("$.movieOnShowId").value(1))
            .andExpect(jsonPath("$.seats").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.seats[0][0]").value(3))
            .andExpect(jsonPath("$.seats[0][1]").value(1))
            .andExpect(jsonPath("$.valid").value(true))
            .andExpect(jsonPath("$.phoneNum").value(TEST_AVAILABLE_PHONE));
        // Check ticket
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_AVAILABLE_PHONE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        // Get info
        mockMvc.perform(post("/resource/ticket/info")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", TEST_AVAILABLE_PHONE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(4)))
            .andExpect(jsonPath("$.movieOnShowId").value(1))
            .andExpect(jsonPath("$.seats").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.seats[0][0]").value(3))
            .andExpect(jsonPath("$.seats[0][1]").value(1))
            .andExpect(jsonPath("$.valid").value(false))
            .andExpect(jsonPath("$.phoneNum").value(TEST_AVAILABLE_PHONE));
    }
}
