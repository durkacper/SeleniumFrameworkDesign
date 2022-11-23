package NOVATechnology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.swing.*;
import java.time.Duration;
import java.util.List;

public class StandAloneTest {

    public static void main(String[] args) {

        String productName = "ZARA COAT 3";

        // setup chromedriver and global wait
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        //global wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://rahulshettyacademy.com/client");
        //explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //start with max window
        driver.manage().window().maximize();

        //login
        driver.findElement(By.id("userEmail")).sendKeys("john.brown@mail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Password123");
        driver.findElement(By.id("login")).click();

        //wait until products are loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
        //grab all products into list
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

        //iterate on list using Streams and add product to cart
        WebElement prod = products.stream().filter(product ->
                product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
        prod.findElement(By.cssSelector("button.btn.w-10")).click();

        //iterate in classic way and add product to cart
        /*
        for (int i = 0; i < products.size(); i++) {
            String productText = products.get(i).findElement(By.cssSelector("b")).getText();
            if (productText.equals(productName)) {
                driver.findElement(By.cssSelector("button.btn.w-10")).click();
            }
        }
         */

        //wait until "Product Added to Cart" notification is displayed (temporary notification), then go to Cart
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));

        //and wait until "loading page animation" disappeared
        //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
        //in this case the "invisibilityOf" is quicker than "invisibilityOfElementLocated":
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

        //go to the Cart
        driver.findElement(By.cssSelector("button[routerlink='/dashboard/cart'] i")).click();

        //grab all products in Cart into list
        List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));

        //iterate on list in classic way
        for (int i = 0; i < cartProducts.size(); i++) {
            String productInCartText = cartProducts.get(i).getText();
            Assert.assertTrue(productInCartText.equals(productName));
        }
        /*
        //iterate on list using Stream
        boolean match = cartProducts.stream().anyMatch(cartProduct ->
                cartProduct.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match);
         */

        //go to the Checkout
        driver.findElement(By.cssSelector("li.totalRow button")).click();

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

