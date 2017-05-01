package test;

import com.awesometickets.util.Verifier;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class VerifierTest extends BaseTest {

    @Test
    public void testSendSmsCode() {
        Assert.assertTrue(Verifier.getInstance().sendSmsCode("13538773281"));
    }

    @Test
    public void testVerifySmsCode() {
        Assert.assertTrue(Verifier.getInstance().verifySmsCode("13538773281", "491121"));
    }
}
