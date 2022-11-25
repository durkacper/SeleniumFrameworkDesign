package NOVATechnology;

import NOVATechnology.PageObjects.CartPO;
import NOVATechnology.PageObjects.LandingPagePO;
import NOVATechnology.PageObjects.ProductCataloguePO;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class SubmitOrderTest {

    public static void main(String[] args) {

        String productName = "ZARA COAT 3";

        // setup chromedriver and global wait
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        //global wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //start with max window
        driver.manage().window().maximize();

        //login
        LandingPagePO landingPage = new LandingPagePO(driver);
        landingPage.goTo();
        ProductCataloguePO productCataloguePO = landingPage.loginAplication("john.brown@mail.com", "Password123");

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
        cartPO.goToCheckout();








        //Checkout -> select the country using Actions
        Actions actions = new Actions(driver);
        actions.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "Pol").build().perform();
        //simple solution, also valid:
        //driver.findElement(By.cssSelector("[placeholder='Select Country']")).sendKeys("Pol");

        //wait until suggestion of country is shown and select Poland
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
        driver.findElement(By.cssSelector("button.ta-item:nth-last-of-type(1)")).click();
        driver.findElement(By.cssSelector(".action__submit")).click();

        //Assert if "THANK YOU" information is displayed
        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase( "Thankyou for the order."));

        //close the browser
        driver.close();
    }


}
