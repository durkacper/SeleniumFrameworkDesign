package NOVATechnologyTests;

import NOVATechnology.PageObjects.*;
import NOVATechnologyTests.TestComponents.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class SubmitOrderTest extends BaseTest {

    String productName = "ZARA COAT 3";

    //submit order
    @Test
    public void submitOrder() throws IOException {

        ProductCataloguePO productCataloguePO = landingPagePO.loginApplication("john.brown@mail.com", "Password123");

        //wait until products are loaded
        //grab all products into list
        List<WebElement> products = productCataloguePO.getProductList();

        //iterate on list using Streams and add product to cart
        //wait until "Product Added to Cart" notification is displayed (temporary notification), then go to Cart
        //and wait until "loading page animation" disappeared (in this case the "invisibilityOf" is quicker than "invisibilityOfElementLocated")
        productCataloguePO.addProductToCart(productName);

        //go to the Cart page
        CartPO cartPO = productCataloguePO.goToCartPage();

        //Cart Page - grab all products in Cart into list
        //iterate on list using Stream
        //assert if our product is added
        //go to the Checkout
        Boolean match = cartPO.verifyProductDisplayed(productName);
        Assert.assertTrue(match);
        CheckoutPO checkoutPO = cartPO.goToCheckout();

        //Checkout -> select the country using Actions
        //wait until suggestion of country is shown and select Poland
        checkoutPO.selectCountry("Pol");
        ConfirmationPO confirmationPO = checkoutPO.placeOrder();

        //Assert if "THANK YOU" information is displayed
        String confirmationMessageText = confirmationPO.getConfirmationText();
        Assert.assertTrue(confirmationMessageText.equalsIgnoreCase("Thankyou for the order."));

    }


    //verify that the ZARA COAT 3 is displayed in the orders page
    //URGENT -> run this test only if "submitOrder" is executed
    @Test(dependsOnMethods = {"submitOrder"})
    public void orderHistory(){

        ProductCataloguePO productCataloguePO = landingPagePO.loginApplication("john.brown@mail.com", "Password123");
        OrdersPO ordersPO = productCataloguePO.goToOrdersPage();
        Assert.assertTrue(ordersPO.verifyOrderDisplayed(productName));
    }


}

