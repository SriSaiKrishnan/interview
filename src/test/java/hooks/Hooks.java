package hooks;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import reports.ExtentManager;

import java.util.Properties;

import static reports.ExtentManager.attachScreenshotToReport;

public class Hooks {

    private static WebDriver driver;
    private Properties properties;
    private DriverFactory driverFactory;
    private static final Logger logger = LogManager.getLogger(Hooks.class);
    private static ExtentReports extent = ExtentManager.getExtentReports();
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Before
    public void setUp(Scenario scenario) {
        logger.info("Initializing WebDriver...");
        driverFactory = new DriverFactory();
        properties = driverFactory.initProp();
        driver = driverFactory.initDriver(properties);
        String testName = scenario.getName();
        assert testName != null && !testName.isEmpty() : "Test name must not be null or empty";
        ExtentTest test = extent.createTest(testName);
        extentTest.set(test);
        extentTest.get().log(Status.INFO, "Starting Scenario: " + testName);
    }

    @After
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {
            if (scenario.isFailed()) {
                // Capture and attach screenshot
                extentTest.get().fail("Scenario Failed: " + scenario.getName());
                attachScreenshotToReport(extentTest.get(), driver, scenario.getName());
            } else {
                extentTest.get().pass("Scenario passed: " + scenario.getName());
            }
        } else {
            extentTest.get().pass("Scenario Passed: " + scenario.getName());
        }
        // Clean up the ThreadLocal variable
        extentTest.remove();
        ExtentManager.getExtentReports().flush();
        logger.info("Closing WebDriver...");
        driverFactory.quitDriver();
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

}
