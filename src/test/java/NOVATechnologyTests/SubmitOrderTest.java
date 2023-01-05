package NOVATechnologyTests;

import NOVATechnology.PageObjects.*;
import TestComponents.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SubmitOrderTest extends BaseTest {

    String productName = "ZARA COAT 3";

    //submit order
    @Test(dataProvider = "getData", groups = {"purchaseTests"})
    public void submitOrder(HashMap<String, String> input) throws IOException {

        ProductCataloguePO productCataloguePO = landingPagePO.loginApplication(input.get("mail"), input.get("pass"));

        //wait until products are loaded
        //grab all products into list
        List<WebElement> products = productCataloguePO.getProductList();

        //iterate on list using Streams and add product to cart
        //wait until "Product Added to Cart" notification is displayed (temporary notification), then go to Cart
        //and wait until "loading page animation" disappeared (in this case the "invisibilityOf" is quicker than "invisibilityOfElementLocated")
        productCataloguePO.addProductToCart(input.get("productName"));

        //go to the Cart page
        CartPO cartPO = productCataloguePO.goToCartPage();

        //Cart Page - grab all products in Cart into list
        //iterate on list using Stream
        //assert if our product is added
        //go to the Checkout
        Boolean match = cartPO.verifyProductDisplayed(input.get("productName"));
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
    public void orderHistory() {

        ProductCataloguePO productCataloguePO = landingPagePO.loginApplication("john.brown@mail.com", "Password123");
        OrdersPO ordersPO = productCataloguePO.goToOrdersPage();
        Assert.assertTrue(ordersPO.verifyOrderDisplayed(productName));
    }


    //screenshot after test failed
    public String  getScreenshot(String testCaseName) throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir") + "/reports/" + testCaseName +".png");
        FileUtils.copyFile(source, file);
        return System.getProperty("user.dir") + "/reports/" + testCaseName + ".png";
    }


    @DataProvider
    public Object[][] getData() throws IOException {

        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "/src/test/java/Data/PurchaseOrder.json");
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("mail", "john.brown@mail.com");
//        map.put("pass", "Password123");
//        map.put("productName", "ZARA COAT 3");
//
//        HashMap<String, String> map1 = new HashMap<String, String>();
//        map1.put("mail", "carlos.santana@mail.com");
//        map1.put("pass", "Guitar123");
//        map1.put("productName", "ADIDAS ORIGINAL");

}

