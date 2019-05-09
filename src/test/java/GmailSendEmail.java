import org.openqa.selenium.By;
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

    @FindBy(css = "[name=to]")
    private WebElement receiverEmailField;

    @FindBy(css = "[name=subjectbox]")
    private WebElement topicEmailField;

    @FindBy(xpath = "//*[@class=\"J-J5-Ji btA\"]")
    private WebElement sendButtonInPopup;

    @FindBy(xpath = "//*[contains(text(),'Message sent.')]")
    private WebElement messageSentTip;

    @FindBy(xpath = "//*[@class=\"gb_ma\"]")
    private WebElement gmailLogo;

    @FindBy(xpath = "//*[@class=\"T-I J-J5-Ji nu T-I-ax7 L3\"]")
    private WebElement refreshButton;

    @FindBy(xpath = "//*[@class=\"TN bzz aHS-bnu\"]")
    private WebElement sentTab;

    @FindBy(xpath = "//*[@class=\"TN bzz aHS-bnx\"]")
    private WebElement trashTab;

    @FindBy(xpath = "//div[@act=\"10\"]")
    private WebElement deleteButton;


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
    }

    private boolean waitAndCheckEmail(String topic) {
        refreshButton.click();

        String xpath = String.format("//span[contains(text(), '%s')]", topic);
        By by = By.xpath(xpath);
        WebElement subjectElement =  waitAndGetElement(by);
        if (subjectElement == null) {
            return false;
        }
        return true;
    }

    private WebElement waitAndGetElement(By byElement) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.pollingEvery(Duration.ofSeconds(1));
            wait.until(ExpectedConditions.presenceOfElementLocated(byElement));
            return driver.findElement(byElement);
        }
        catch (TimeoutException e) {
            return null;
        }
    }

    public boolean checkEmail(String user, String passwd, String topic) {
        GmailLogin gmailPage = new GmailLogin();
        gmailPage.login(user, passwd);

        WebDriverWait explicitWait = new WebDriverWait(driver, 10);
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

    public boolean checkSentFolder(String user, String passwd, String topic) {
        sentTab.click();

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

    public boolean deleteEmail(String user, String passwd, String topic) {
        GmailLogin gmailPage = new GmailLogin();
        gmailPage.login(user, passwd);

        refreshButton.click();

        String xpath = String.format("//span[contains(text(), '%s')]/ancestor::tr", topic);
        By by = By.xpath(xpath);
        WebElement subjectElement =  waitAndGetElement(by);
        if (subjectElement == null) {
            return false;
        }
        subjectElement.click();
        deleteButton.click();
        return true;
    }

    public boolean checkTrashFolder(String user, String passwd, String topic) {
        trashTab.click();
        boolean result = waitAndCheckEmail(topic);

        GmailLogout a = new GmailLogout();
        a.logout();
        return result;
    }
}

