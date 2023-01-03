package NOVATechnology.AbstractComponents;

import NOVATechnology.PageObjects.CartPO;
import NOVATechnology.PageObjects.OrdersPO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePO {

    WebDriver driver;

    @FindBy(css = "button[routerlink*='cart'] i")
    WebElement cartHeader;

    @FindBy(css = "button[routerlink*='myorders']")
    WebElement ordersHeader;

    public BasePO(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void waitForElementToAppear(By findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    public void waitForWebElementToAppear(WebElement findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(findBy));
    }

    public void waitForElementToDisappear(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public CartPO goToCartPage(){
        cartHeader.click();
        CartPO cartPO = new CartPO(driver);
        return cartPO;
    }

    public OrdersPO goToOrdersPage(){
        ordersHeader.click();
        OrdersPO ordersPO = new OrdersPO(driver);
        return ordersPO;
    }
}
