package NOVATechnology.PageObjects;

import NOVATechnology.AbstractComponents.BasePO;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OrdersPO extends BasePO {

    WebDriver driver;

    public OrdersPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//tbody/tr/td[2]")
    private List<WebElement> orderProducts;

    public boolean verifyOrderDisplayed(String productName) {
        Boolean match = orderProducts.stream().anyMatch(orderProduct ->
                orderProduct.getText().equalsIgnoreCase(productName));
        return match;
    }
}

