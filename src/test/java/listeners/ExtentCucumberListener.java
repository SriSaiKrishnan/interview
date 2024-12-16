package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.IOException;

public class ExtentCucumberListener implements ITestListener {

    private static ExtentReports extent;
    private static ExtentTest test;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    static {
        // Initialize ExtentReports with ExtentSparkReporter (instead of ExtentHtmlReporter)
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/extent-report.html");
        sparkReporter.config().setDocumentTitle("Cucumber Test Report");
        sparkReporter.config().setReportName("Cucumber Test Execution Results");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);  // Use ExtentSparkReporter
    }

    @Override
    public void onTestStart(ITestResult result) {
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, result.getMethod().getMethodName() + " is Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            try {
                String screenshotPath = DriverFactory.captureScreenshot(driver, result.getMethod().getMethodName());
                test.addScreenCaptureFromPath(screenshotPath);
            } catch (IOException e) {
                e.printStackTrace();
                test.fail("Failed to capture screenshot: " + e.getMessage());
            }
        } else {
            test.fail("WebDriver session is not available");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, result.getMethod().getMethodName() + " is Skipped");
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        extent.flush();
    }
}
