package test.service;

import com.awesometickets.web.RedisService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseTest;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RedisServiceTest extends BaseTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void testStringKV() throws Exception {
        String key = "key", value = "value";
        assertFalse(redisService.exists(key));
        redisService.set(key, value);
        assertTrue(redisService.get(key) != null);
        assertTrue(redisService.get(key).equals(value));
        redisService.del(key);
    }
}
