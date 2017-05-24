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
