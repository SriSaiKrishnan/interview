package factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.util.Properties;

public class OptionsManager {

    private Properties properties;
    private ChromeOptions chromeOptions;
    private FirefoxOptions firefoxOptions;
    private EdgeOptions edgeOptions;
    private static final Logger logger = LogManager.getLogger(OptionsManager.class);

    public OptionsManager(Properties properties){
        this.properties = properties;
    }

    public ChromeOptions getChromeOptions(){
        chromeOptions = new ChromeOptions();
        if (Boolean.parseBoolean(properties.getProperty("headless"))) {
            logger.info("====Running tests in headless======");
            chromeOptions.addArguments("--headless");
        }
        if (Boolean.parseBoolean(properties.getProperty("incognito"))) {
            chromeOptions.addArguments("--incognito");
        }
        if (Boolean.parseBoolean(properties.getProperty("remote"))) {
            chromeOptions.addArguments("--disable-extensions");
            chromeOptions.addArguments("--disable-popup-blocking");
            chromeOptions.setCapability("browserName", "chrome");
            chromeOptions.setCapability("unhandledPromptBehavior", "accept");
        }
        return chromeOptions;
    }

    public FirefoxOptions getFirefoxOptions(){
        firefoxOptions = new FirefoxOptions();
        if (Boolean.parseBoolean(properties.getProperty("headless"))) {
            logger.info("====Running tests in headless======");
            firefoxOptions.addArguments("--headless");
        }
        if (Boolean.parseBoolean(properties.getProperty("incognito"))) {
            firefoxOptions.addArguments("--incognito");
        }
        if (Boolean.parseBoolean(properties.getProperty("remote"))) {
            firefoxOptions.addArguments("--disable-extensions");
            firefoxOptions.addArguments("--disable-popup-blocking");
            firefoxOptions.setCapability("browserName", "firefox");
            firefoxOptions.setCapability("unhandledPromptBehavior", "accept");
        }
        return firefoxOptions;
    }

    public EdgeOptions getEdgeOptions(){
        edgeOptions = new EdgeOptions();
        if (Boolean.parseBoolean(properties.getProperty("headless"))) {
            logger.info("====Running tests in headless======");
            edgeOptions.addArguments("--headless");
        }
        if (Boolean.parseBoolean(properties.getProperty("incognito"))) {
            edgeOptions.addArguments("--inPrivate");
        }
        if (Boolean.parseBoolean(properties.getProperty("remote"))) {
            edgeOptions.addArguments("--disable-extensions");
            edgeOptions.addArguments("--disable-popup-blocking");
            edgeOptions.setCapability("browserName", "edge");
            edgeOptions.setCapability("unhandledPromptBehavior", "accept");

        }
        return edgeOptions;
    }

}
