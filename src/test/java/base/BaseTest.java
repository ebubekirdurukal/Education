package base;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class BaseTest {

    protected static WebDriver driver;
    protected static WebDriverWait webDriverWait;
    private static Logger logger = LoggerFactory.getLogger(BaseTest.class);
    public static String browserChrome="chrome";
    public static String browserFirefox="firefox";
    DesiredCapabilities capabilities = new DesiredCapabilities();



    // bu kısım her senaryodan önce tekrar çalışır.
    //deneme değişikliği
    @BeforeScenario
    public void setUp() throws Exception {

        String baseUrl = "https://www.hepsiburada.com/";
        String selectPlatform = "win";
        String selectBrowser = "chrome";

        logger.info("************************************  BeforeScenario  ************************************");
        logger.info("************************************key   " + System.getProperty("key") + "   key************************************");

        if (StringUtils.isEmpty(System.getenv("key"))){
            logger.info("Local cihazda " + selectPlatform + " ortamında " + browserChrome + " browserında test ayağa kalkacak");
            if ("win".equalsIgnoreCase(selectPlatform)){
                if ("chrome".equalsIgnoreCase(selectBrowser)){
                    ChromeOptions options = new ChromeOptions();
                    capabilities .setCapability("browserName",browserChrome);
                    DesiredCapabilities capabilities = new DesiredCapabilities();
                    LoggingPreferences loggingPreferences = new LoggingPreferences();
                    loggingPreferences.enable(LogType.BROWSER, Level.ALL);
                    capabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
                    options.merge(capabilities);
                    Map<String, Object> prefs = new HashMap<String, Object>();
                    prefs.put("profile.default_content_setting_values.notifications", 2);
                    options.setExperimentalOption("prefs", prefs);
                    options.addArguments("--kiosk");
                    options.addArguments("--disable-notifications");
                    options.addArguments("--disable-popup-blocking");
                    options.addArguments("--start-fullscreen");
                    System.setProperty("webdriver.chrome.driver","webdriver/chromedriver.exe");
                    driver = new ChromeDriver(options);
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(30 * 1000));


                }else if ("firefox".equalsIgnoreCase(selectBrowser)){
                    FirefoxOptions options = new FirefoxOptions();
                    capabilities .setCapability("browserName",browserFirefox);
                    Map<String, Object> prefs = new HashMap<String, Object>();
                    prefs.put("profile.default_content_setting_values.notifications", 2);
                    options.addArguments("--kiosk");
                    options.addArguments("--disable-notifications");
                    options.addArguments("--start-fullscreen");
                    FirefoxProfile profile = new FirefoxProfile();
                    capabilities.setCapability("marionette",true);
                    System.setProperty("webdriver.gecko.driver","web_driver/geckodriver.exe");
                    driver = new FirefoxDriver(options);
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(30 * 1000));
                }


            }else if("mac".equalsIgnoreCase(selectPlatform)){
                if ("chrome".equalsIgnoreCase(selectBrowser)){
                    ChromeOptions options = new ChromeOptions();
                    capabilities .setBrowserName(browserChrome);
                    Map<String, Object> prefs = new HashMap<String, Object>();
                    prefs.put("profile.default_content_setting_values.notifications", 2);
                    options.setExperimentalOption("prefs", prefs);
                    options.addArguments("--kiosk");
                    options.addArguments("--disable-notifications");
                    options.addArguments("--start-fullscreen");
                    System.setProperty("webdriver.chrome.driver","web_driver/chromedriver");
                    driver = new ChromeDriver(options);
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(30 * 1000));
                }else if ("firefox".equalsIgnoreCase(selectBrowser)){
                    FirefoxOptions options = new FirefoxOptions();
                    capabilities .setBrowserName(browserFirefox);
                    Map<String, Object> prefs = new HashMap<String, Object>();
                    prefs.put("profile.default_content_setting_values.notifications", 2);
                    options.addArguments("--kiosk");
                    options.addArguments("--disable-notifications");
                    options.addArguments("--start-fullscreen");
                    FirefoxProfile profile = new FirefoxProfile();
                    capabilities.setCapability(FirefoxDriver.PROFILE,profile);
                    capabilities.setCapability("marionette",true);
                    System.setProperty("webdriver.gecko.driver","web_driver/geckodriver");
                    driver = new FirefoxDriver(options);
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(30 * 1000));
                }

            }

        }

        else {
            logger.info("************************************   Testiniumda test ayağa kalkacak   ************************************");
            ChromeOptions options = new ChromeOptions();
            capabilities .setBrowserName(browserChrome);

            options.addArguments("disable-translate");

            options.addArguments("--disable-notifications");

            options.addArguments("--start-fullscreen");

            Map<String, Object> prefs = new HashMap<>();

            options.setExperimentalOption("prefs", prefs);

            capabilities.setCapability(ChromeOptions.CAPABILITY, options);

            capabilities.setCapability("key", System.getenv("key"));

            browserChrome = System.getenv("browser");

           // driver = new RemoteWebDriver(new URL("https://hubclouddev.testinium.com/wd/hub"), capabilities);
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);


        }

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(30 * 1000));

        driver.manage().window().maximize();

        driver.get(baseUrl);

    }
    @AfterScenario

    public void tearDown() {
        driver.quit();

    }

}
