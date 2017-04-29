package test;

import com.awesome_tickets.util.CodeFactory;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CodeFactoryTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(CodeFactoryTest.class);

    @Test
    public void testGetCode() throws Exception {
        for (int i = 0; i < 5; ++i) {
            LOG.info(CodeFactory.getCode());
            Assert.assertTrue(CodeFactory.getCode().length() == 10);
        }
    }

    @Test
    public void testDigest() throws Exception {
        for (int i = 0; i < 5; ++i) {
            Assert.assertTrue(CodeFactory.digest(CodeFactory.getCode()).length() == 32);
        }
    }
}
