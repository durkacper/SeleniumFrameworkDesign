package NOVATechnologyTests.TestComponents;

import NOVATechnology.PageObjects.LandingPagePO;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    public LandingPagePO landingPagePO;

    //explicit wait
    //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    public WebDriver initializeDriver() throws IOException {

        //properties class - get the property from GlobalData file
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//NOVATechnology//Resources//GlobalData.properties"); //System.getProperty("user.dir") - dynamic file path
        properties.load(fileInputStream);
        String browserName = properties.getProperty("browser");

        // setup chromedriver (here we can add if condition and choose with what browser we want to execute tests, chrome, firefox...)
        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            //Firefox....
        }
        //global wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //start with max window
        driver.manage().window().maximize();
        return driver;
    }



    @BeforeMethod (alwaysRun = true)
    public LandingPagePO launchApplication() throws IOException {

        driver = initializeDriver();
        landingPagePO = new LandingPagePO(driver);
        landingPagePO.goTo();
        return landingPagePO;
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(){
        driver.close();
    }

}
