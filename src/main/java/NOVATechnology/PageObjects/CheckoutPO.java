package NOVATechnology.PageObjects;

import NOVATechnology.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPO extends AbstractComponent {
    WebDriver driver;

    public CheckoutPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    @FindBy(css = "[placeholder='Select Country']")
    WebElement selectCountryBox;

    @FindBy(css = ".action__submit")
    WebElement placeOrderButton;

    @FindBy(css = "button.ta-item:nth-last-of-type(1)")
    WebElement country;

    By countryResults = By.cssSelector(".ta-results");

    public void selectCountry(String countryName){
        Actions actions = new Actions(driver);
        actions.sendKeys(selectCountryBox, countryName).build().perform();
        //OR: selectCountry.click();
        waitForElementToAppear(countryResults);
        country.click();
    }

    public ConfirmationPO placeOrder(){
        placeOrderButton.click();
        return new ConfirmationPO(driver);
    }

}
