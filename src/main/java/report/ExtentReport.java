package report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class ExtentReport {

    // Private constructor to prevent instantiation
    private ExtentReport() {}

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static int cntr = 0;

    // Initialize the ExtentReports instance
    public static void initReports() {
        if (Objects.isNull(extent) && cntr<1) {
            cntr++;
            extent = new ExtentReports();
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/extent-report.html");
            sparkReporter.config().setDocumentTitle("Cucumber Test Report");
            sparkReporter.config().setReportName("Cucumber Test Execution Results");
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Company", "Veeva Interview");
            extent.setSystemInfo("Environment", "QA");
        }
    }

    // Flush the ExtentReports and open the report
    public static void flushReports() {
        if (Objects.nonNull(extent)) {
            extent.flush();
        }
        try {
            Desktop.getDesktop().browse(new File("target/extent-report.html").toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create a new test instance and associate it with the current thread
    public static void createTest(String testCaseName) {
        ExtentTest extentTest = extent.createTest(testCaseName);
        test.set(extentTest); // Associate the test instance with the current thread
    }

    // Retrieve the ExtentTest instance for the current thread
    public static ExtentTest getTest() {
        return test.get();
    }

    // Remove the ExtentTest instance from the current thread
    public static void removeTest() {
        test.remove();
    }
}
