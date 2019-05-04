import io.qameta.allure.AllureId;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GmailLoginLogoutTest {
    final WebDriver driver = SingletonBrowserClass.getInstanceOfSingletonBrowserClass().getDriver();

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

    @AfterEach
    void tearDown() {
        driver.manage().deleteAllCookies();
    }

    @AfterAll
    static void tearAll() {
        SingletonBrowserClass.getInstanceOfSingletonBrowserClass().getDriver().close();
    }


    @Description("Login and logout to/from gmail")
    @AllureId("GM-1/2")
    @ParameterizedTest
    @CsvSource({
            "seleniumtests10@gmail.com, 060788avavav",
            "seleniumtests30@gmail.com, 060788avavav",
            "seleniumtests50@gmail.com, 060788avavav"
})
    public void testGmailLoginLogout(ArgumentsAccessor argumentsAccessor){
        String login = (String) argumentsAccessor.getString(0);
        String password = (String) argumentsAccessor.get(1);

        GmailLogin gmailPage = new GmailLogin();
        gmailPage.login(login, password);
        String userName = gmailPage.getUserName();
        assertEquals(login, userName);

        GmailLogout a = new GmailLogout();
        a.logout();
        String txt = a.getPassowrdFieldLabel();
        assertEquals(txt,"Введите пароль");
    }
}
