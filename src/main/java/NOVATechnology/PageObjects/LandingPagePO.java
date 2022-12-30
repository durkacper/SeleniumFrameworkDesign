package NOVATechnology.PageObjects;

import NOVATechnology.AbstractComponents.BasePO;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPagePO extends BasePO {

    WebDriver driver;

    //constructor
    public LandingPagePO(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        this.driver = driver;

    }

    @FindBy(id = "userEmail")
    WebElement userEmail;

    @FindBy(id = "userPassword")
    WebElement userPasswrod;

    @FindBy(id = "login")
    WebElement loginButton;

    @FindBy(css = "[class*='flyInOut']")
    WebElement errorMessage;


    //instead of creating separate methods for each WebElement, we can create one complex method and use it in Test
    public ProductCataloguePO loginApplication(String mail, String pass) {
        userEmail.sendKeys(mail);
        userPasswrod.sendKeys(pass);
        loginButton.click();
        return new ProductCataloguePO(driver);
    }

    public String getErrorMessage(){
        waitForWebElementToAppear(errorMessage);
        return errorMessage.getText();
    }

    public void goTo() {
        driver.get("https://rahulshettyacademy.com/client");
    }
}
