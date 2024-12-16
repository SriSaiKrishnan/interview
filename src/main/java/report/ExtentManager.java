package report;

import com.aventstack.extentreports.ExtentTest;

public class ExtentManager {

    private ExtentManager() {
        // Private constructor to prevent instantiation
    }

    // ThreadLocal to ensure thread-safe access to ExtentTest
    private static ThreadLocal<ExtentTest> extTest = new ThreadLocal<>();

    /**
     * Retrieves the ExtentTest instance for the current thread.
     *
     * @return the ExtentTest instance associated with the current thread
     */
    public static ExtentTest getExtentTest() {
        return extTest.get();
    }

    /**
     * Sets the ExtentTest instance for the current thread.
     *
     * @param test the ExtentTest instance to be associated with the current thread
     */
    public static void setExtentTest(ExtentTest test) {
        extTest.set(test);
    }

    /**
     * Removes the ExtentTest instance associated with the current thread
     * to prevent memory leaks after the test is complete.
     */
    public static void unload() {
        extTest.remove();
    }
}
