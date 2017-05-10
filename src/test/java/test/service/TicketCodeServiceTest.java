package test.service;

import com.awesometickets.business.entities.repositories.TicketRepository;
import com.awesometickets.business.services.TicketCodeService;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseTest;


public class TicketCodeServiceTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(TicketCodeServiceTest.class);

//    @Autowired
    private TicketCodeService ticketCodeService;

//    @Test
    public void testCreateAndSaveCode() throws Exception {
        for (int i = 0; i < 5; ++i) {
            String code = ticketCodeService.genCode();
            LOG.info("Test code: " + code);
            Assert.assertFalse(ticketCodeService.hasCode(code));
            ticketCodeService.saveCode(code);
            Assert.assertTrue(ticketCodeService.hasCode(code));
        }
    }

//    @Test
    public void testDigest() throws Exception {
        Assert.assertTrue(ticketCodeService.digest("").equals("d41d8cd98f00b204e9800998ecf8427e"));
        Assert.assertTrue(ticketCodeService.digest("The quick brown fox jumps over the lazy dog")
            .equals("9e107d9d372bb6826bd81d3542a419d6"));
        Assert.assertTrue(ticketCodeService.digest("The quick brown fox jumps over the lazy dog.")
            .equals("e4d909c290d0fb1ca068ffaddf22cbd0"));
    }
}
