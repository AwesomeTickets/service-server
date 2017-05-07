package test.service;

import com.awesometickets.business.services.SmsService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import test.BaseTest;

@Ignore
public class SmsServiceTest extends BaseTest {

    private SmsService smsService = SmsService.getInstance();

    @Test
    public void testSendSmsCode() {
        Assert.assertTrue(smsService.sendSmsCode("15622743170"));
    }

    @Test
    public void testVerifySmsCode() {
        Assert.assertTrue(smsService.verifySmsCode("15622743170", "948595"));
    }
}
