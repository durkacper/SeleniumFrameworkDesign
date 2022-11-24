package NOVATechnology.PageObjects;

import NOVATechnology.AbstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPO extends AbstractComponent {

    public CartPO(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".cartSection h3")
    private List<WebElement> productTitles;

    @FindBy(css = "li.totalRow button")
    WebElement checkoutButton;

    public void goToCheckout(){
        checkoutButton.click();
    }

    public boolean verifyProductDisplayed(String productName){
        Boolean match = productTitles.stream().anyMatch(cartProduct ->
                cartProduct.getText().equalsIgnoreCase(productName));
        return match;
    }

}
