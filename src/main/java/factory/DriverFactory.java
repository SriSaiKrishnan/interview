package factory;

import constants.AppConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import utilities.WriteToCSV;
import utilities.WriteToTextFile;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class DriverFactory {

    Properties properties;
    OptionsManager optionsManager;
    WriteToTextFile writeToTextFile;
    WriteToCSV writeToCSV;
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);

    public WebDriver initDriver(Properties properties){

        String browserName = properties.getProperty("browser");
        logger.info("browser name is : " + browserName);
        optionsManager = new OptionsManager(properties);
        writeToTextFile = new WriteToTextFile();
        writeToCSV = new WriteToCSV();
        switch (browserName.toLowerCase().trim()) {
            case "chrome":
                if (Boolean.parseBoolean(properties.getProperty("remote"))) {
                    // run tcs on remote machine/grid:
                    initRemoteDriver("chrome");
                } else {
                    // run it in local
                    tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
                }
                break;

            case "firefox":
                if (Boolean.parseBoolean(properties.getProperty("remote"))) {
                    initRemoteDriver("firefox");
                } else {
                    tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
                }
                break;

            case "edge":
                if (Boolean.parseBoolean(properties.getProperty("remote"))) {
                    initRemoteDriver("edge");
                } else {
                    tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
                }
                break;
            default:
                logger.info("plz pass the right browser name..." + browserName);
        }

        Dimension dim = new Dimension(1600, 800);
        getDriver().manage().window().setSize(dim);
        getDriver().manage().deleteAllCookies();
        getDriver().get(properties.getProperty("url"));// loginPage
        writeToTextFile.deleteFileIfExists(AppConstants.USER_DIR + AppConstants.TEXT_FILE_PATH );
        writeToCSV.deleteFileIfExists(AppConstants.USER_DIR + AppConstants.CSV_FILE_PATH);
        return getDriver();

    }

    /**
     * init remote driver to run test cases on remote (grid) machine
     *
     * @param browserName
     */
    private void initRemoteDriver(String browserName) {
        logger.info("Rnning it on GRID...with browser: " + browserName);

        try {
            switch (browserName) {
                case "chrome":
                   tlDriver.set(
                            new RemoteWebDriver(new URL(properties.getProperty("huburl")), optionsManager.getChromeOptions()));
                    break;
                case "firefox":
                   tlDriver.set(
                            new RemoteWebDriver(new URL(properties.getProperty("huburl")), optionsManager.getFirefoxOptions()));
                    break;
                case "edge":
                    tlDriver.set(new RemoteWebDriver(new URL(properties.getProperty("huburl")), optionsManager.getEdgeOptions()));
                    break;

                default:
                    logger.info("please pass the right browser on grid..");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * get the local thread copy of the driver
     *
     * @return
     */
    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    /**
     * this is used to init the properties from the .properties file
     *
     * @return this returns properties (prop)
     */
    public Properties initProp() {

        properties = new Properties();
        FileInputStream fileInputStream = null;

        String envName = System.getProperty("env");
        logger.info("Running test suite on environment--->" + envName);
        if (envName == null) {
            logger.info("Environment name is not given, hence running it on QA environment....");
            try {
                fileInputStream = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                switch (envName.trim().toLowerCase()) {
                    case "dev":
                        fileInputStream = new FileInputStream(AppConstants.CONFIG_DEV_FILE_PATH);
                        break;
                    case "qa":
                        fileInputStream = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
                        break;
                    case "prod":
                        fileInputStream = new FileInputStream(AppConstants.CONFIG_FILE_PATH);
                        break;
                    default:
                        logger.info("please pass the right env name.." + envName);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;

    }

    public void quitDriver() {
        if (tlDriver.get() != null) {
            try {
                tlDriver.get().quit(); // Quit the driver instance
                logger.info("Driver instance quit successfully.");
            } catch (Exception e) {
                logger.error("Error while quitting the driver: ", e);
            } finally {
                tlDriver.remove(); // Remove the driver instance from ThreadLocal
            }
        }
    }

    public static String takeScreenshot(WebDriver driver, String testName) {
        // Create a timestamp for unique naming
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        try {
            // Capture the screenshot as Base64
            String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

            logger.info("Screenshot captured for test: " + testName + " at " + timestamp);
            return base64Screenshot; // Return Base64 string
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
