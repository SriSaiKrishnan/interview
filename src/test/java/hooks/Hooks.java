package hooks;

import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import java.util.Properties;

public class Hooks {

    private static WebDriver driver;
    private Properties properties;
    private DriverFactory driverFactory;
    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp() {
        logger.info("Initializing WebDriver...");
        driverFactory = new DriverFactory();
        properties = driverFactory.initProp();
        driver = driverFactory.initDriver(properties);
    }

    @After
    public void tearDown() {
        logger.info("Closing WebDriver...");
        driverFactory.quitDriver();
    }

}
