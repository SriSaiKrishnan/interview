package runners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import listeners.ExtentCucumberListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

@Listeners(ExtentCucumberListener.class)
@CucumberOptions(
        features = "src/test/java/features", // Path to feature files
        glue = {"stepdefinitions", "hooks"}, // Step definition and hooks package
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber-reports.json"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

        private static final Logger logger = LogManager.getLogger(TestRunner.class);
        public static  JsonNode testData;
        private static ExtentReports extent;
        private static ExtentTest test;

        @BeforeClass
        public void beforeClass() throws IOException {
                // Load JSON file for test data
                ObjectMapper mapper = new ObjectMapper();
                testData = mapper.readTree(new File("src/test/resources/testData.json"));
                System.out.println(testData);
        }

        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios() {
                return super.scenarios();
        }

}
