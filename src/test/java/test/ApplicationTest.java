package test;

import com.tickets.business.services.MovieService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tickets.business.entities.User;
import com.tickets.business.services.UserService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ApplicationTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Test
    public void testUser()  {
        String username = "Alice", password = "123456";
        assertFalse(userService.hasUsername(username));
        userService.create(new User(username, password));
        assertTrue(userService.hasUsername(username));
        assertTrue(userService.permitLogin(username, password));
        assertFalse(userService.permitLogin(username, "XXX"));
    }
}
