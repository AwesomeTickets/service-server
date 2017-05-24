package test;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public abstract class RestSessionControllerTest extends RestControllerTest {
    private static final String SESSION_ATTR_PHONE = "phoneNum";
    protected MockHttpSession session;

    @Before
    @Override
    public void setup() throws Exception {
        super.setup();
        registerTestUser();
    }

    protected void assertSession(MockHttpSession session_) {
        Assert.assertTrue(session_ != null);
        Assert.assertTrue(session_.getAttribute(SESSION_ATTR_PHONE).equals(TEST_PHONE_USER));
    }

    private void registerTestUser() throws Exception {
        MvcResult res = mockMvc.perform(post("/resource/user")
            .param("phoneNum", TEST_PHONE_USER)
            .param("password", TEST_PASSWORD_VALID)
            .param("smsCode", TEST_SMS_CODE))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.phoneNum").value(TEST_PHONE_USER))
            .andReturn();
        session = (MockHttpSession) res.getRequest().getSession();
        assertSession(session);
    }
}
