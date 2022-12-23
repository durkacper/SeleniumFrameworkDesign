package NOVATechnology.PageObjects;

import NOVATechnology.AbstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPO extends AbstractComponent {

    WebDriver driver;

    public CartPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".cartSection h3")
    private List<WebElement> cartProducts;

    @FindBy(css = "li.totalRow button")
    WebElement checkoutButton;

    public CheckoutPO goToCheckout() {
        checkoutButton.click();
        return new CheckoutPO(driver);
    }

    public boolean verifyProductDisplayed(String productName) {
        Boolean match = cartProducts.stream().anyMatch(cartProduct ->
                cartProduct.getText().equalsIgnoreCase(productName));
        return match;
    }

}
