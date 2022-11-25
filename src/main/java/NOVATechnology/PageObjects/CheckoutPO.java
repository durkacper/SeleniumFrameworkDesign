package NOVATechnology.PageObjects;

import NOVATechnology.AbstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPO extends AbstractComponent {

    public CheckoutPO(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }




}
