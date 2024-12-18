package hooks;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;

import static factory.DriverFactory.takeScreenshot;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtentReports() {
        if (extent == null) {
            // Define the HTML Reporter
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/reports/extent-report.html");
            htmlReporter.config().setTheme(Theme.STANDARD);
            htmlReporter.config().setDocumentTitle("Test Automation Report");
            htmlReporter.config().setReportName("Cucumber TestNG Framework");

            // Attach the reporter to ExtentReports
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
        }
        return extent;
    }

    public static void attachScreenshotToReport(ExtentTest test, WebDriver driver, String testName) {
        String base64Screenshot = takeScreenshot(driver,testName);

        if (base64Screenshot != null) {
            test.addScreenCaptureFromBase64String(base64Screenshot, "Screenshot for: " + testName);
        } else {
            test.fail("Failed to capture screenshot for: " + testName);
        }
    }

}
