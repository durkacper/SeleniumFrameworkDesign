package NOVATechnologyTests;

import NOVATechnology.PageObjects.*;
import NOVATechnologyTests.TestComponents.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class ErrorValidationsTest extends BaseTest {

    @Test
    public void loginFail() throws IOException {

        landingPagePO.loginApplication("wrong@mail.com", "wrong pass");
        landingPagePO.getErrorMessage();
        Assert.assertEquals("Incorrect email or password.", landingPagePO.getErrorMessage());
    }


}

