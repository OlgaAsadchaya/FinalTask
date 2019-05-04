import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GmailSendEmail {
    final WebDriver driver = SingletonBrowserClass.getInstanceOfSingletonBrowserClass().getDriver();

    @FindBy(xpath = "//*[contains(@class,'T-I J-J5-Ji T-I-KE L3')]")
    private WebElement buttonSend;

    //@FindBy(xpath = "//*[@id=\":ch\"]")
    @FindBy(css = "[name=to]")
    private WebElement receiverEmailField;

    //@FindBy(xpath = "//*[@id=\":ei\"]")
    @FindBy(css = "[name=subjectbox]")
    private WebElement topicEmailField;

    @FindBy(xpath = "//*[@id=\":9s\"]")
    private WebElement sendButtonInPopup;

    @FindBy(xpath = "//*[contains(text(),'Message sent.')]")
    private WebElement messageSentTip;

    @FindBy(xpath = "//*[@class=\"gb_ma\"]")
    private WebElement gmailLogo;

    @FindBy(xpath = "//*[@class=\"T-I J-J5-Ji nu T-I-ax7 L3\"]")
    private WebElement refreshButton;


    public GmailSendEmail() {
        PageFactory.initElements(driver, this);
    }


    public void sendEmail(String user, String passwd, String topic, String to) {
        GmailLogin gmailPage = new GmailLogin();
        gmailPage.login(user, passwd);

        buttonSend.click();
        receiverEmailField.sendKeys(to);
        topicEmailField.sendKeys(topic);
        sendButtonInPopup.click();
        WebDriverWait explicitWait = new WebDriverWait(driver, 10);
        explicitWait.pollingEvery(Duration.ofSeconds(1));
        explicitWait.until(ExpectedConditions.visibilityOf(messageSentTip));

        GmailLogout a = new GmailLogout();
        a.logout();
    }

    public boolean checkEmail(String user, String passwd, String topic) {
        GmailLogin gmailPage = new GmailLogin();
        gmailPage.login(user, passwd);

        WebDriverWait explicitWait = new WebDriverWait(driver, 3);
        explicitWait.pollingEvery(Duration.ofSeconds(1));
        explicitWait.until(ExpectedConditions.visibilityOf(gmailLogo));
        refreshButton.click();

        String xpath = String.format("//span[contains(text(), '%s')]", topic);
        boolean result = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.pollingEvery(Duration.ofSeconds(1));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            result = true;
        }
        catch (TimeoutException e) {
            result = false;
        }

        GmailLogout a = new GmailLogout();
        a.logout();

        return result;
    }

}

