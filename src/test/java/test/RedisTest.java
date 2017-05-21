package test;

import com.awesometickets.web.RedisService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RedisTest extends BaseTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void testSaveAndGet() throws Exception {
        String key = "key";
        String value = "value";
        assertFalse(redisService.getSingleValueByKey(key) != null);
        redisService.setSingleValue(key, value);
        assertTrue(redisService.getSingleValueByKey(key) != null);
        assertTrue(redisService.getSingleValueByKey(key).equals(value));
    }
}
