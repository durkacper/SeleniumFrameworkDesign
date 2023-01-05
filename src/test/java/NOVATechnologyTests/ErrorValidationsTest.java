package NOVATechnologyTests;

import NOVATechnology.PageObjects.*;
import TestComponents.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class ErrorValidationsTest extends BaseTest {

    @Test (groups = {"errorHandling"})
    public void loginErrorValidation() throws IOException {

        landingPagePO.loginApplication("wrong@mail.com", "wrong pass");
        landingPagePO.getErrorMessage();
        Assert.assertEquals("Incorrect email or password.", landingPagePO.getErrorMessage());
    }


    @Test
    public void productErrorValidation() throws IOException {

        String productName = "ZARA COAT 3";

        ProductCataloguePO productCataloguePO = landingPagePO.loginApplication("tom.cruise@mail.com", "Secret123");
        List<WebElement> products = productCataloguePO.getProductList();
        productCataloguePO.addProductToCart(productName);
        CartPO cartPO = productCataloguePO.goToCartPage();
        Boolean match = cartPO.verifyProductDisplayed("ZARA COAT 44");
        Assert.assertFalse(match);
    }


}

