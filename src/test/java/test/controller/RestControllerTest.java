package test.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import test.BaseTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
public class RestControllerTest extends BaseTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private String testDate = "2017-05-01";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Movie API
     */
    @Test
    public void testGetMovieDetailsByID() throws Exception {
        mockMvc.perform(get("/resource/movie/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(11)))
            .andExpect(jsonPath("$.movieId").value(1))
            .andExpect(jsonPath("$.title").isString())
            .andExpect(jsonPath("$.pubDate").isString())
            .andExpect(jsonPath("$.length").isNumber())
            .andExpect(jsonPath("$.rating").isNumber())
            .andExpect(jsonPath("$.country").isString())
            .andExpect(jsonPath("$.movieStatus").isString())
            .andExpect(jsonPath("$.movieType").isString())
            .andExpect(jsonPath("$.movieStyle").isArray())
            .andExpect(jsonPath("$.posterSmall").isString())
            .andExpect(jsonPath("$.posterLarge").isString());
    }

    @Test
    public void testGetOnShowMovieIDs() throws Exception {
        mockMvc.perform(get("/resource/movie/on"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.count").isNumber())
            .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testGetComingSoonMovieIDs() throws Exception {
        mockMvc.perform(get("/resource/movie/soon"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.count").isNumber())
            .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testGetPopularMovies() throws Exception {
        mockMvc.perform(get("/resource/movie/popular?count=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.count").value(1))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data[0].movieId").isNumber())
            .andExpect(jsonPath("$.data[0].posterLarge").isString());
    }

    /**
     * Cinema API
     */
    @Test
    public void testGetCinemaByID() throws Exception {
        mockMvc.perform(get("/resource/cinema/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(3)))
            .andExpect(jsonPath("$.cinemaId").value(1))
            .andExpect(jsonPath("$.cinemaName").isString())
            .andExpect(jsonPath("$.cinemaAddr").isString());
    }

    /**
     * Cinema hall API
     */
    @Test
    public void testGetCinemaHallWithoutSeatByID() throws Exception {
        mockMvc.perform(get("/resource/cinema-hall/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(3)))
            .andExpect(jsonPath("$.cinemaHallId").value(1))
            .andExpect(jsonPath("$.cinemaId").isNumber())
            .andExpect(jsonPath("$.hallName").isString());
    }

    @Test
    public void testGetCinemaHallWithSeatByID() throws Exception {
        mockMvc.perform(get("/resource/cinema-hall/1/seat-layout"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.cinemaHallId").value(1))
            .andExpect(jsonPath("$.seatLayout").isString());
    }

    /**
     * Movie on show API
     */
    @Test
    public void testGetMovieOnShowByDetails() throws Exception {
        mockMvc.perform(get("/resource/movie-on-show?movieId=1&cinemaHallId=1&showDate="
            + testDate + "&showTime=13:20:00"));
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
        mockMvc.perform(get("/resource/movie-on-show/day?showDate=" + testDate + "&cinemaId=1&&movieId=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.count").isNumber())
            .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testGetBriefMovieOnShowByDay() throws Exception {
        mockMvc.perform(get("/resource/movie-on-show/day/brief?showDate=" + testDate + "&cinemaId=1&&movieId=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.minPrice").isNumber())
            .andExpect(jsonPath("$.minPrice").isNumber())
            .andExpect(jsonPath("$.showTime").isArray());
    }

    /**
     * Seat API
     */
    @Test
    public void testGetUnavailableSeat() throws Exception {
        mockMvc.perform(get("/resource/seat/unavailable?movieOnShowId=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.count").isNumber())
            .andExpect(jsonPath("$.data").isArray());
    }
}
