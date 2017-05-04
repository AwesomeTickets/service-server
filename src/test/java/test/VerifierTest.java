package test;

import com.awesometickets.business.services.SmsService;
import org.junit.Assert;
import org.junit.Test;

public class VerifierTest extends BaseTest {

    @Test
    public void testSendSmsCode() {
        Assert.assertTrue(SmsService.getInstance().sendSmsCode("15622743170"));
    }

    @Test
    public void testVerifySmsCode() {
        Assert.assertTrue(SmsService.getInstance().verifySmsCode("15622743170", "948595"));
    }
}
