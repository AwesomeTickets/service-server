package test.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import test.RestControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CinemaHallControllerTest extends RestControllerTest {

    @Test
    public void testGetCinemaHallWithoutSeatByID() throws Exception {
        mockMvc.perform(get(URI_HALL, "1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(3)))
            .andExpect(jsonPath("$.cinemaHallId").value(1))
            .andExpect(jsonPath("$.cinemaId").isNumber())
            .andExpect(jsonPath("$.hallName").isString());
    }

    @Test
    public void testGetCinemaHallWithSeatByID() throws Exception {
        mockMvc.perform(get(URI_HALL_LAYOUT, "1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.cinemaHallId").value(1))
            .andExpect(jsonPath("$.seatLayout").isString());
    }
}
