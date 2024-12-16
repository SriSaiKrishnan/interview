package report;

import com.aventstack.extentreports.MediaEntityBuilder;
import constants.AppConstants;
import factory.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public final class ExtentLogger {

    private ExtentLogger() {
        // Private constructor to prevent instantiation
    }

    /**
     * Logs a pass message.
     *
     * @param message The message to log
     */
    public static void pass(String message) {
        ExtentManager.getExtentTest().pass(message);
    }

    /**
     * Logs a fail message.
     *
     * @param message The message to log
     */
    public static void fail(String message) {
        ExtentManager.getExtentTest().fail(message);
    }

    /**
     * Logs a skip message.
     *
     * @param message The message to log
     */
    public static void skip(String message) {
        ExtentManager.getExtentTest().skip(message);
    }

    /**
     * Logs a warning message.
     *
     * @param message The message to log
     */
    public static void warn(String message) {
        ExtentManager.getExtentTest().warning(message);
    }

    /**
     * Logs a pass message, optionally with a screenshot.
     *
     * @param message           The message to log
     * @param isScreenshotNeeded Whether to attach a screenshot
     */
    public static void pass(String message, boolean isScreenshotNeeded) {
        try {
            if (AppConstants.SCREENSHOT_FOR_PASS.equalsIgnoreCase("yes") && isScreenshotNeeded) {
                ExtentManager.getExtentTest().pass(
                        message,
                        MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image()).build()
                );
            } else {
                ExtentManager.getExtentTest().pass(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs a fail message, optionally with a screenshot.
     *
     * @param message           The message to log
     * @param isScreenshotNeeded Whether to attach a screenshot
     */
    public static void fail(String message, boolean isScreenshotNeeded) {
        try {
            if (AppConstants.SCREENSHOT_FOR_FAIL.equalsIgnoreCase("yes") && isScreenshotNeeded) {
                ExtentManager.getExtentTest().fail(
                        message,
                        MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image()).build()
                );
            } else {
                ExtentManager.getExtentTest().fail(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Captures the current browser screen as a Base64 string.
     *
     * @return The Base64 string representation of the screenshot
     */
    private static String getBase64Image() {
        try {
            return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
