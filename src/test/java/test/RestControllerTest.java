package test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@Transactional
public abstract class RestControllerTest extends BaseTest {
    protected static final String URI_MOVIE = "/resource/movie/{movieId}";
    protected static final String URI_MOVIE_ON = "/resource/movie/on";
    protected static final String URI_MOVIE_SOON = "/resource/movie/soon";
    protected static final String URI_MOVIE_POPULAR = "/resource/movie/popular";
    protected static final String URI_CINEMA = "/resource/cinema/{cinemaId}";
    protected static final String URI_HALL = "/resource/cinema-hall/{cinemaHallId}";
    protected static final String URI_HALL_LAYOUT = "/resource/cinema-hall/{cinemaHallId}/seat-layout";
    protected static final String URI_MOVIE_ON_SHOW = "/resource/movie-on-show/{movieOnShowId}";
    protected static final String URI_MOVIE_ON_SHOW_INFO = "/resource/movie-on-show";
    protected static final String URI_MOVIE_ON_SHOW_RECENT = "/resource/movie-on-show/recent";
    protected static final String URI_MOVIE_ON_SHOW_DAY = "/resource/movie-on-show/day";
    protected static final String URI_MOVIE_ON_SHOW_DAY_BRIEF = "/resource/movie-on-show/day/brief";
    protected static final String URI_SEAT_UNAVAILABLE = "/resource/seat/unavailable";
    protected static final String URI_SMS_GET = "/resource/sms/{phoneNum}";
    protected static final String URI_SMS_VERIFY = "/resource/sms/{phoneNum}/check";
    protected static final String URI_USER_REGISTER = "/resource/user";
    protected static final String URI_USER_LOGIN = "/resource/session";
    protected static final String URI_USER_LOGOUT = "/resource/session/drop";
    protected static final String URI_USER_SESSION_CHECK = "/resource/session/check";
    protected static final String URI_USER_HISTORY = "/resource/user/{phoneNum}/ticket/history";
    protected static final String URI_TICKET_BUY = "/resource/ticket";
    protected static final String URI_TICKET_CHECK = "/resource/ticket/check";
    protected static final String URI_TICKET_INFO = "/resource/ticket/info";

    protected static final String TEST_SMS_CODE = "448291";
    protected static final String TEST_PHONE_USER = "13512345678";
    protected static final String TEST_PHONE_UNAVAILABLE = "18813572468";
    protected static final String TEST_PHONE_INVALID = "99912345678";
    protected static final String TEST_PHONE_NOT_EXISTS = "18812345679";
    protected static final String TEST_PASSWORD_VALID = "ab3456";
    protected static final String TEST_PASSWORD_INVALID = "123456";

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;
    protected ObjectMapper objMapper;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objMapper = new ObjectMapper();
        objMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }
}
