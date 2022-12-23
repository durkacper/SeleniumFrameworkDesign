package NOVATechnology.PageObjects;

import NOVATechnology.AbstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPagePO extends AbstractComponent {

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

    //instead of creating separate methods for each WebElement, we can create one complex method and use it in Test
    public ProductCataloguePO loginAplication(String mail, String pass) {
        userEmail.sendKeys(mail);
        userPasswrod.sendKeys(pass);
        loginButton.click();
        return new ProductCataloguePO(driver);
    }

    public void goTo() {
        driver.get("https://rahulshettyacademy.com/client");
    }
}
