package test.util;

import com.awesometickets.web.RedisHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseTest;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RedisHelperTest extends BaseTest {

    @Autowired
    private RedisHelper redisHelper;

    @Test
    public void testStringKV() throws Exception {
        String key = "key", value = "value";
        assertFalse(redisHelper.exists(key));
        redisHelper.set(key, value);
        assertTrue(redisHelper.get(key) != null);
        assertTrue(redisHelper.get(key).equals(value));
        redisHelper.del(key);
    }
}
