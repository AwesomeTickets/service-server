package test.service;

import com.awesometickets.business.services.TicketService;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseTest;


public class TicketServiceTest extends BaseTest {

    private static final Logger Log = LoggerFactory.getLogger(TicketServiceTest.class);

    @Autowired
    private TicketService ticketService;

    @Test
    public void testCreateAndSaveCode() throws Exception {
        for (int i = 0; i < 2; ++i) {
            String code = ticketService.genCode();
            Log.info("Test code: " + code);
            Assert.assertFalse(ticketService.hasCode(code));
            ticketService.saveCode(code);
            Assert.assertTrue(ticketService.hasCode(code));
        }
    }

    @Test
    public void testDigest() throws Exception {
        Assert.assertTrue(ticketService.digest("").equals("d41d8cd98f00b204e9800998ecf8427e"));
        Assert.assertTrue(ticketService.digest("The quick brown fox jumps over the lazy dog")
            .equals("9e107d9d372bb6826bd81d3542a419d6"));
        Assert.assertTrue(ticketService.digest("The quick brown fox jumps over the lazy dog.")
            .equals("e4d909c290d0fb1ca068ffaddf22cbd0"));
    }
}
