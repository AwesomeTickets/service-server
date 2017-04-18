package test;

import com.tickets.web.controller.CinemaController;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import test.BaseTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class WebAPITest extends BaseTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

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
            .andExpect(jsonPath("$.movieID").value(1))
            .andExpect(jsonPath("$.title").isString())
            .andExpect(jsonPath("$.pubdate").isString())
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
        mockMvc.perform(get("/resource/movie/on_show"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.count").isNumber())
            .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testGetComingSoonMovieIDs() throws Exception {
        mockMvc.perform(get("/resource/movie/coming_soon"))
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
            .andExpect(jsonPath("$.data[0].movieID").isNumber())
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
            .andExpect(jsonPath("$.cinemaID").value(1))
            .andExpect(jsonPath("$.name").isString())
            .andExpect(jsonPath("$.location").isString());
    }

    /**
     * Cinema hall API
     */
    @Test
    public void testGetCinemaHallWithoutSeatByID() throws Exception {
        mockMvc.perform(get("/resource/cinema_hall/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(3)))
            .andExpect(jsonPath("$.cinemaHallID").value(1))
            .andExpect(jsonPath("$.cinemaID").isNumber())
            .andExpect(jsonPath("$.name").isString());
    }

    @Test
    public void testGetCinemaHallWithSeatByID() throws Exception {
        mockMvc.perform(get("/resource/cinema_hall/1/seat_layout"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.cinemaHallID").value(1))
            .andExpect(jsonPath("$.seatLayout").isString());
    }

    /**
     * Movie on show API
     */
    @Test
    public void testGetMovieOnShowByDetails() throws Exception {
        // Not implemented
    }

    @Test
    public void testGetMovieOnShowByID() throws Exception {
        mockMvc.perform(get("/resource/movie_on_show/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(7)))
            .andExpect(jsonPath("$.movieOnShowID").value(1))
            .andExpect(jsonPath("$.movieID").isNumber())
            .andExpect(jsonPath("$.cinemaHallID").isNumber())
            .andExpect(jsonPath("$.lang").isString())
            .andExpect(jsonPath("$.showDate").isString())
            .andExpect(jsonPath("$.showTime").isString())
            .andExpect(jsonPath("$.price").isNumber());
    }

    @Test
    public void testGetRecentMovieOnShow() throws Exception {
        mockMvc.perform(get("/resource/movie_on_show/recent?movieID=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.count").isNumber())
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data[0].date").isString())
            .andExpect(jsonPath("$.data[0].cinemaID").isArray());
    }

    @Test
    public void testGetMovieOnShowByDay() throws Exception {
        mockMvc.perform(get("/resource/movie_on_show/day?date=2017-04-04&cinemaID=1&&movieID=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.count").isNumber())
            .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testGetBriefMovieOnShowByDay() throws Exception {
        mockMvc.perform(get("/resource/movie_on_show/day/brief?date=2017-04-04&cinemaID=1&&movieID=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.minPrice").isNumber())
            .andExpect(jsonPath("$.showTime").isArray());
    }

    /**
     * Seat API
     */
    @Test
    public void testGetUnavailableSeat() throws Exception {
        mockMvc.perform(get("/resource/seat/unavailable?movieOnShowID=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.count").isNumber())
            .andExpect(jsonPath("$.data").isArray());
    }
}
