package test.controller;

import com.awesometickets.business.entities.Ticket;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import sun.rmi.runtime.Log;
import test.BaseTest;
import test.service.TicketServiceTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
public class RestControllerTest extends BaseTest {
    private static final Logger Log = LoggerFactory.getLogger(RestControllerTest.class);
    private static final String TEST_DATE = "2017-05-01";

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private ObjectMapper mapper;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public static class BuyTicketResult {
        private int movieOnShowId;
        private int[][] seats;
        private String phoneNum;
        private String ticketCode;
    }

    public static class MovieOnShowResult {
        private int movieOnShowId;
        private int movieId;
        private int cinemaHallId;
        private String lang;
        private String showDate;
        private String showTime;
        private float price;
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
        // Get details
        MvcResult res = mockMvc.perform(get("/resource/movie-on-show/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn();
        MovieOnShowResult result = mapper.readValue(res.getResponse().getContentAsString(), MovieOnShowResult.class);
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

    /**
     * Ticket API
     */
    @Test
    public void testBuyTicket() throws Exception {
        String correctPhoneNum = "18812345678";
        // No exception
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", correctPhoneNum)
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
            .andExpect(jsonPath("$.phoneNum").value(correctPhoneNum))
            .andExpect(jsonPath("$.ticketCode").isString());
        // SEAT_UNAVAILABLE
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", correctPhoneNum)
            .param("seats", "1,1,1,2,1,3"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(200));
        // PHONE_INVALID_FORMAT
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", "99912345678")
            .param("seats", "1,1,1,2,1,3"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(100));
        // SEAT_NOT_FOUND
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", correctPhoneNum)
            .param("seats", "1,1,0,0,1,3"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(201));
        // PHONE_NOT_VERIFIED
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", "18812345679")
            .param("seats", "1,1,1,2,1,3"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(202));
        // PURCHASE_UNAVAILABLE
        mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", "18813572648")
            .param("seats", "1,1,1,2,1,3"))
            .andExpect(status().isForbidden())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(203));
    }

    @Test
    public void testCheckTicket() throws Exception {
        String correctPhoneNum = "18812345678";
        // Buy tickets first
        MvcResult res = mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", correctPhoneNum)
            .param("seats", "2,1,2,2,2,3,2,4"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn();
        String correctTicketCode = mapper.readValue(res.getResponse().getContentAsString(),
            BuyTicketResult.class).ticketCode;
        // PHONE_INVALID_FORMAT
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", "99912345678"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(100));
        // TICKET_CODE_NOT_FOUND
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", "12345678910")
            .param("phoneNum", correctPhoneNum))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(301));
        // PHONE_MISMATCH
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", "18812345679"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(300));
        // Check ticket
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", correctPhoneNum))
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
            .andExpect(jsonPath("$.phoneNum").value(correctPhoneNum));
        // TICKET_CHECKED
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", correctPhoneNum))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(302));
    }

    @Test
    @Ignore
    public void testGetTicketInfo() throws Exception {
        String correctPhoneNum = "18812345678";
        // Buy tickets first
        MvcResult res = mockMvc.perform(post("/resource/ticket")
            .param("movieOnShowId", "1")
            .param("phoneNum", correctPhoneNum)
            .param("seats", "3,1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn();
        String correctTicketCode = mapper.readValue(res.getResponse().getContentAsString(),
            BuyTicketResult.class).ticketCode;
        // PHONE_INVALID_FORMAT
        mockMvc.perform(get("/resource/ticket/info")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", "99912345678"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(100));
        // TICKET_CODE_NOT_FOUND
        mockMvc.perform(get("/resource/ticket/info")
            .param("ticketCode", "12345678910")
            .param("phoneNum", correctPhoneNum))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(301));
        // PHONE_MISMATCH
        mockMvc.perform(get("/resource/ticket/info")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", "18812345679"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.code").value(300));
        // Get info
        mockMvc.perform(get("/resource/ticket/info")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", correctPhoneNum))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(4)))
            .andExpect(jsonPath("$.movieOnShowId").value(1))
            .andExpect(jsonPath("$.seats").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.seats[0][0]").value(3))
            .andExpect(jsonPath("$.seats[0][1]").value(1))
            .andExpect(jsonPath("$.valid").value(true))
            .andExpect(jsonPath("$.phoneNum").value(correctPhoneNum));
        // Check ticket
        mockMvc.perform(post("/resource/ticket/check")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", correctPhoneNum))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        // Get info
        mockMvc.perform(get("/resource/ticket/info")
            .param("ticketCode", correctTicketCode)
            .param("phoneNum", correctPhoneNum))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(4)))
            .andExpect(jsonPath("$.movieOnShowId").value(1))
            .andExpect(jsonPath("$.seats").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.seats[0][0]").value(3))
            .andExpect(jsonPath("$.seats[0][1]").value(1))
            .andExpect(jsonPath("$.valid").value(false))
            .andExpect(jsonPath("$.phoneNum").value(correctPhoneNum));
    }
}
