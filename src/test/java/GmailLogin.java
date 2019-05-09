

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class GmailLogin{
    final WebDriver driver = SingletonBrowserClass.getInstanceOfSingletonBrowserClass().getDriver();

    @FindBy(xpath = "//*[@id=\"identifierId\"]")
    private WebElement emailField;

    @FindBy(xpath = "//*[contains(@class,'RveJvd snByac')] ")
    private WebElement nextButton;

    @FindBy(css = "[name=password]")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"passwordNext\"]")
    private WebElement nextButton2;

    @FindBy(xpath = "//*[contains(@class,'gb_ya gbii')]")
    private WebElement userInfoSign;

    @FindBy(xpath = "//*[contains(@class,'gb_db')]")
    private WebElement userInfoPopupEmail;

    @FindBy(xpath = "//*[@class=\"gb_ma\"]")
    private WebElement gmailLogo;

    private final String url = "https://accounts.google.com/signin/v2/identifier?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";
    private String userName;

    public GmailLogin() {
        PageFactory.initElements(driver, this);
        driver.manage().deleteAllCookies();
        driver.get(url);
    }


    public void login(String user, String passwd) {
        emailField.sendKeys(user);
        nextButton.click();

        WebDriverWait explicitWait = new WebDriverWait(driver, 10);
        explicitWait.pollingEvery(Duration.ofSeconds(1));
        explicitWait.until(ExpectedConditions.visibilityOf(passwordField));

        passwordField.sendKeys(passwd);
        nextButton2.click();

        explicitWait.until(ExpectedConditions.visibilityOf(gmailLogo));
        userInfoSign.click();
        explicitWait.until(ExpectedConditions.visibilityOf(userInfoPopupEmail));
        userName = userInfoPopupEmail.getText();
        userInfoSign.click();
    }

    public String getUserName() {
        return userName;
    }
}

