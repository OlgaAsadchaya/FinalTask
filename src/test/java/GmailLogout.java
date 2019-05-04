import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GmailLogout {
    final WebDriver driver = SingletonBrowserClass.getInstanceOfSingletonBrowserClass().getDriver();


    @FindBy(xpath = "//*[contains(@class,'gb_ya gbii')]")
    private WebElement userInfoSign;

    @FindBy(xpath = "//*[contains(@class,'gb_0 gb_Ff gb_Nf gb_me gb_kb')]")
    private WebElement userInfoPopupLogoutButton;

    @FindBy(xpath = "//*[@id=\"headingText\"]/content")
    private WebElement headingText;

    @FindBy(css = "[name=password]")
    private WebElement passwordField;


    public GmailLogout() {
        PageFactory.initElements(driver, this);
    }


    public void logout() {
        userInfoSign.click();
        userInfoPopupLogoutButton.click();

        WebDriverWait explicitWait = new WebDriverWait(driver, 10);
        explicitWait.pollingEvery(Duration.ofSeconds(1));
        explicitWait.until(ExpectedConditions.visibilityOf(headingText));
    }

    public String getPassowrdFieldLabel() {
        return passwordField.getAttribute("aria-label");
    }
}

