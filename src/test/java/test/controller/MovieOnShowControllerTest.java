package test.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import test.RestControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MovieOnShowControllerTest extends RestControllerTest {
    private static final String TEST_DATE = "2017-05-01";

    public static class MovieOnShowResult {
        private int movieOnShowId;
        private int movieId;
        private int cinemaHallId;
        private String lang;
        private String showDate;
        private String showTime;
        private float price;
    }

    @Test
    public void testGetMovieOnShowByDetails() throws Exception {
        // Get details
        MvcResult res = mockMvc.perform(get("/resource/movie-on-show/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn();
        MovieOnShowResult result = objMapper.readValue(res.getResponse().getContentAsString(), MovieOnShowResult.class);
        // Search by details
        mockMvc.perform(get("/resource/movie-on-show")
            .param("movieId", String.valueOf(result.movieId))
            .param("cinemaHallId", String.valueOf(result.cinemaHallId))
            .param("showDate", String.valueOf(result.showDate))
            .param("showTime", String.valueOf(result.showTime)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(7)))
            .andExpect(jsonPath("$.movieOnShowId").value(1))
            .andExpect(jsonPath("$.movieId").value(result.movieId))
            .andExpect(jsonPath("$.cinemaHallId").value(result.cinemaHallId))
            .andExpect(jsonPath("$.lang").isString())
            .andExpect(jsonPath("$.showDate").value(result.showDate))
            .andExpect(jsonPath("$.showTime").value(result.showTime))
            .andExpect(jsonPath("$.price").isNumber());
    }

    @Test
    public void testGetMovieOnShowByID() throws Exception {
        mockMvc.perform(get("/resource/movie-on-show/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(7)))
            .andExpect(jsonPath("$.movieOnShowId").value(1))
            .andExpect(jsonPath("$.movieId").isNumber())
            .andExpect(jsonPath("$.cinemaHallId").isNumber())
            .andExpect(jsonPath("$.lang").isString())
            .andExpect(jsonPath("$.showDate").isString())
            .andExpect(jsonPath("$.showTime").isString())
            .andExpect(jsonPath("$.price").isNumber());
    }

    @Test
    public void testGetRecentMovieOnShow() throws Exception {
        mockMvc.perform(get("/resource/movie-on-show/recent?movieId=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.count").isNumber())
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data[0].showDate").isString())
            .andExpect(jsonPath("$.data[0].cinemaId").isArray());
    }

    @Test
    public void testGetMovieOnShowByDay() throws Exception {
        mockMvc.perform(get("/resource/movie-on-show/day?showDate=" + TEST_DATE + "&cinemaId=1&&movieId=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.count").isNumber())
            .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testGetBriefMovieOnShowByDay() throws Exception {
        mockMvc.perform(get("/resource/movie-on-show/day/brief?showDate=" + TEST_DATE + "&cinemaId=1&&movieId=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.minPrice").isNumber())
            .andExpect(jsonPath("$.minPrice").isNumber())
            .andExpect(jsonPath("$.showTime").isArray());
    }
}
