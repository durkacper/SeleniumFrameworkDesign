package NOVATechnology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {

    public static void main(String[] args) {

        // setup chromedriver and global wait
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://rahulshettyacademy.com/client");

        //login
        driver.findElement(By.id("userEmail")).sendKeys("john.brown@mail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Password123");
        driver.findElement(By.id("login")).click();

        // grab all products into list
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

        //iterate using Streams
        WebElement prod = products.stream().filter(product ->
                product.findElement(By.cssSelector("b")).getText().equals("ZARA COAT 3")).findFirst().orElse(null);
        prod.findElement(By.cssSelector("button.btn.w-10")).click();

        //iterate classic
        /*
        for (int i = 0; i < products.size(); i++) {
            String productName = products.get(i).findElement(By.cssSelector("b")).getText();
            if (productName.equals("ZARA COAT 3")) {
                driver.findElement(By.cssSelector("button.btn.w-10")).click();
            }
        }
         */

    }


}

