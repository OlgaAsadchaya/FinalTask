import io.qameta.allure.AllureId;
import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GmailSendEmailTest {
    final WebDriver driver = SingletonBrowserClass.getInstanceOfSingletonBrowserClass().getDriver();

    String login = "seleniumtests10@gmail.com";
    String password = "060788avavav";

    String login2 = "seleniumtests30@gmail.com";
    String password2 = "060788avavav";

    @BeforeAll
    static void beforeAll() {
        String url = System.getProperty("url", "local");
        String browser = System.getProperty("browser", "chrome");
        String version = System.getProperty("version", "");
        String os = System.getProperty("os", "");
        String userName = System.getProperty("userName", "");
        String accessKey = System.getProperty("accessKey", "");

        SingletonBrowserClass.init(url, browser, version, os, userName, accessKey);
    }

    @BeforeEach
    void beforeEach() {
        driver.manage().deleteAllCookies();
    }

    @AfterAll
    static void tearAll() {
        SingletonBrowserClass.getInstanceOfSingletonBrowserClass().getDriver().close();
    }

    @Description("Verify the ability to send emails")
    @AllureId("GM-3")
    @Test
    public void testSendEmail() {
        int rnd = (int )(Math.random() * 10000 + 1);
        String uniqTopic = Integer.toString(rnd);

        GmailSendEmail gmail = new GmailSendEmail();
        gmail.sendEmail(login, password, uniqTopic, login2);

        GmailLogout a = new GmailLogout();
        a.logout();

        assertTrue(gmail.checkEmail(login2, password2, uniqTopic));
    }

    @Description("Verify that sent email appears in Sent Mail folder")
    @AllureId("GM-4")
    @Test
    public void testSendEmail2() {
        int rnd = (int )(Math.random() * 10000 + 1);
        String uniqTopic = Integer.toString(rnd);

        GmailSendEmail gmail = new GmailSendEmail();
        gmail.sendEmail(login, password, uniqTopic, login2);
        assertTrue(gmail.checkSentFolder(login, password, uniqTopic));
    }

    @Description("Verify that deleted email is listed in Trash")
    @AllureId("GM-5")
    @Test
    public void testDeletedEmail() {
        int rnd = (int )(Math.random() * 10000 + 1);
        String uniqTopic = Integer.toString(rnd);

        GmailSendEmail gmail = new GmailSendEmail();
        gmail.sendEmail(login, password, uniqTopic, login2);

        GmailLogout a = new GmailLogout();
        a.logout();

        gmail.deleteEmail(login2, password2, uniqTopic);
        assertTrue(gmail.checkTrashFolder(login2, password2, uniqTopic));
    }
}
