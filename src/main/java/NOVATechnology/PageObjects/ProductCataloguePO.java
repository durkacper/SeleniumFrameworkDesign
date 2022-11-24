package NOVATechnology.PageObjects;

import NOVATechnology.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductCataloguePO extends AbstractComponent {

    WebDriver driver;

    //constructor
    public ProductCataloguePO(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".mb-3")
    List<WebElement> products;

    @FindBy(css = ".ng-animating")
    WebElement loadingSpinner;


    By productsBy = By.cssSelector(".mb-3");
    By addToCart = By.cssSelector("button.btn.w-10");
    By productAddedToCartMessage = By.cssSelector("#toast-container");

    public List<WebElement> getProductList() {
        waitForElementToAppear(productsBy);
        return products;
    }

    public WebElement getProductByName(String productName) {
        WebElement prod = getProductList().stream().filter(product ->
                product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
        return prod;
    }

    public void addProductToCart(String productName) {
        WebElement prod = getProductByName(productName);
        prod.findElement(addToCart).click();
        waitForElementToAppear(productAddedToCartMessage);  //wait until "Product Added to Cart" notification is displayed
        waitForElementToDisappear(loadingSpinner);
    }
}
