import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class SingletonBrowserClass {

    private static String os = "Linux";
    private static String browser = "chrome";
    private static String version = "40";
    private static String userName = "";
    private static String accessKey = "";
    private static String url = "local";

    // instance of singleton class
    private static SingletonBrowserClass instanceOfSingletonBrowserClass=null;

    private WebDriver driver;

    // Constructor
    private SingletonBrowserClass(){
        if (url.equals("local")) {
            if (browser.equals("chrome")) {
                System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                driver = new ChromeDriver();
            }
            else if (browser.equals("firefox")) {
                System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
                driver = new FirefoxDriver();
            }
            else {
                System.out.println("Browser is not supported. Init chrome browser.");
                System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                driver = new ChromeDriver();
            }
        }
        else {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platform", os);
            capabilities.setCapability("browserName", browser);
            capabilities.setCapability("version", version);
            capabilities.setCapability("username", userName);
            capabilities.setCapability("accessKey", accessKey);
            try {
                driver = new RemoteWebDriver(new URL(url), capabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void init(String url, String browser, String version,
                            String os, String userName, String accessKey){

        SingletonBrowserClass.url = url;
        SingletonBrowserClass.browser = browser;
        SingletonBrowserClass.version = version;
        SingletonBrowserClass.os = os;
        SingletonBrowserClass.userName = userName;
        SingletonBrowserClass.accessKey = accessKey;
    }

    // TO create instance of class
    public static SingletonBrowserClass getInstanceOfSingletonBrowserClass(){
        if(instanceOfSingletonBrowserClass==null){
            instanceOfSingletonBrowserClass = new SingletonBrowserClass();
        }
        return instanceOfSingletonBrowserClass;
    }

    // To get driver
    public WebDriver getDriver()
    {
        return driver;
    }



}
