package utilities;

import constants.AppConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import report.ExtentLogger;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ElementUtils {
    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(ElementUtils.class);

    public ElementUtils(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForVisibility(WebElement element) {
        logger.info("Waiting for the element to be visible " + element);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.visibilityOf(element));
        }catch (Exception e){
            logger.error("Failed to wait for element to be visivle ",element,e);
        }

    }

    public void waitForAllElements(List<WebElement> elements) {
        logger.info("Waiting for all the elements to be visible " + elements);
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.visibilityOfAllElements(elements));
        }catch (Exception e){
            logger.error("Failed to wait for all the elements to be visivle ",e);
        }
    }


    public void click(WebElement element) {
        logger.info("Click on the element " + element);
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            wait.until(ExpectedConditions.visibilityOf(element));
            element.click();
        } catch (Exception e) {
            logger.error("Failed to click the element ", element, e);
        }
    }

    public void sendKeys(WebElement element, String text) {
        logger.info("Enter the text " + element);
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear(); // Optional: Clear the field before entering text
            element.sendKeys(text);
        } catch (Exception e) {
            logger.error("Failed to enter the text ", element, e);
        }
    }

    public String getText(WebElement element) {
        logger.info("Getting the text of the Web Element " + element);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
           logger.error("Failed to get the text of the Web Element ", element, e);
        }
        return element.getText();
    }

    public List<WebElement> locateElements(String locator, String locValue) {
        logger.info("Finding all the elements " + locator + " " + locValue);
        List<WebElement> elements = new ArrayList<>();
        try {
            switch (locator.toLowerCase().trim()) {
                case ("id"):
                    elements = driver.findElements(By.id(locValue));
                    break;
                case ("link"):
                    elements = driver.findElements(By.linkText(locValue));
                    break;
                case ("xpath"):
                    elements = driver.findElements(By.xpath(locValue));
                    break;
                case ("name"):
                    elements = driver.findElements(By.name(locValue));
                    break;
                case ("class"):
                    elements = driver.findElements(By.className(locValue));
                    break;
                case ("tag"):
                    elements = driver.findElements(By.tagName(locValue));
                    break;
            }
        } catch (Exception e) {
           logger.error("Unable to locate the element", e);
        }
        return elements;
    }

    public WebElement locateElement(String locator, String locValue) {
        logger.info("Finding the element " + locator + " " + locValue);
        WebElement element = null;
        try {
            switch (locator.toLowerCase().trim()) {
                case ("id"):
                    element = driver.findElement(By.id(locValue));
                    break;
                case ("link"):
                    element = driver.findElement(By.linkText(locValue));
                    break;
                case ("xpath"):
                    element = driver.findElement(By.xpath(locValue));
                    break;
                case ("name"):
                    element = driver.findElement(By.name(locValue));
                    break;
                case ("class"):
                    element = driver.findElement(By.className(locValue));
                    break;
                case ("tag"):
                    element = driver.findElement(By.tagName(locValue));
                    break;
            }
        } catch (Exception e) {
            logger.error("Unable to locate the element", e);
        }
        return element;
    }

    public WebElement getTagName(WebElement element){
        logger.info("Get the element by tagname");
        WebElement tagname = null;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.getTagName();
        } catch (Exception e) {
           logger.error("Unable to get the element by tagname", e);
        }
        return tagname;
    }

    public void verifyExactText(WebElement element, String expectedText){
        if(getText(element).equals(expectedText)){
            logger.info("The text" + getText(element) + "matches with the value: " +expectedText);
            ExtentLogger.pass("The text: " + getText(element) + " is matched with expected text" ,true);
        }else {
            logger.error("The text" + getText(element) + "not matches with the value: " +expectedText);
        }
    }

    public WebElement getDomAttribute(WebElement element, String attribute){
        logger.info("Get the Dom Attribute");
        WebElement domAttribute = null;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.getDomAttribute(attribute);
        } catch (Exception e) {
            logger.error("Unable to get the element by tagname", e);
        }
        return domAttribute;
    }

    public void selectByVisibleText(WebElement dropdown, String visibleText) {
        logger.info("Select the dropdown by visible text " + dropdown);
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.elementToBeClickable(dropdown));
            Select select = new Select(dropdown);
            select.selectByVisibleText(visibleText);
        } catch (Exception e) {
            logger.error("Falied to the select the dropdown by visible text ", dropdown, e);
        }
    }

    public void selectByValue(WebElement dropdown, String value) {
        logger.info("Select the dropdown by value " + dropdown);
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.elementToBeClickable(dropdown));
            Select select = new Select(dropdown);
            select.selectByValue(value);
        } catch (Exception e) {
            logger.error("Failed to select the dropdown by value ", dropdown, e);
        }
    }

    public void selectByIndex(WebElement dropdown, int index) {
        logger.info("Select the dropdown by inde=x " + dropdown);
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.elementToBeClickable(dropdown));
            Select select = new Select(dropdown);
            select.selectByIndex(index);
        } catch (Exception e) {
            logger.error("Failed to select the dropdown by index ", dropdown, e);
        }
    }

    public void waitForEnabled(WebElement element) {
        logger.info("Waiting for element to get enabled " + element);
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(driver -> element.isEnabled());
        } catch (Exception e) {
            logger.error("Failed to wait for element to get enables ", element, e);
        }
    }

    public void scrollToElement(WebElement element) {
        logger.info("Scrolling to the Element " + element);
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.visibilityOf(element));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            logger.error("Failed to scroll to the element ",element, e);
        }
    }

    public void scrollAndClick(WebElement element) {
        logger.info("Scroll and clicking the Element " + element);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.visibilityOf(element));
            scrollToElement(element);
            element.click();
        } catch (Exception e) {
           logger.error("Failed to click the element ", element, e);
        }
    }

    public void doubleClick(WebElement element) {
        logger.info("Performing double click " + element);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.visibilityOf(element));
            Actions actions = new Actions(driver);
            actions.doubleClick(element).perform();
        } catch (Exception e) {
            logger.error("Failed to perform double click ", element, e);
        }
    }

    public void rightClick(WebElement element) {
        logger.info("Performing right click " + element);
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.visibilityOf(element));
            Actions actions = new Actions(driver);
            actions.contextClick(element).perform();
        } catch (Exception e) {
            logger.error("Failed to perform right click ",element, e);
        }
    }

    public void dragAndDrop(WebElement source, WebElement target) {
        logger.info("Performing drag and drop source " + source + " target " + target );
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.visibilityOf(source));
            wait.until(ExpectedConditions.visibilityOf(target));
            Actions actions = new Actions(driver);
            actions.dragAndDrop(source, target).perform();
        } catch (Exception e) {
            logger.error("Failed to perform drag and drop ", source, e);
        }
    }

    public void switchToFrame(WebElement frameElement) {
        logger.info("Switching to the Frame by Web Element " + frameElement);
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
        } catch (Exception e) {
            logger.error("Failed to switch the frame by element", e);
        }

    }

    public void switchToFrameByIndex(int index) {
        logger.info("Switching to the Frame by Index");
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
        } catch (Exception e) {
            logger.error("Failed to switch to frame by index", e);
        }

    }

    public void switchToFrameByNameOrId(String nameOrId) {
        logger.info("Switching to the Frame by Name or ID " + nameOrId);
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
        } catch (Exception e) {
            logger.error("Failed to switch to the Frame by id or name ", nameOrId, e);
        }
    }

    public void switchToDefaultContent() {
        logger.info("Switch to Default Content");
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            logger.error("Failed to switch to default content ",e);
        }

    }

    public void switchToParentFrame() {
        logger.info("Switch to parent frame");
        try{
            driver.switchTo().parentFrame();
        } catch (Exception e) {
            logger.error("Failed to switch to parent frame ",e);
        }

    }

    public void switchToNewWindow() {
        logger.info("Switching to new window");
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(driver -> driver.getWindowHandles().size() > 1);

            for (String windowHandle : driver.getWindowHandles()) {
                driver.switchTo().window(windowHandle);
            }
        } catch (Exception e) {
            logger.error("Failed to switch new window ",e);
        }

    }

    public void switchToOriginalWindow(String originalWindowHandle) {
        logger.info("Switching to original window " + originalWindowHandle);
        try {
            driver.switchTo().window(originalWindowHandle);
        } catch (Exception e) {
            logger.error("Failed to switch to original window ",e);
        }

    }


    public void acceptAlert() {
        logger.info("Accepting the alert");
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (Exception e) {
            logger.error("Failed to accept the alert ",e);
        }
    }
    public void dismissAlert() {
        logger.info("Dismissing the alert");
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.dismiss();
        } catch (Exception e) {
            logger.error("Failed to dismiss the alert ", e);
        }
    }

    public void selectCheckbox(WebElement checkbox) {
        logger.info("Slecting the checkbox " + checkbox);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.visibilityOf(checkbox));
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        } catch (Exception e) {
            logger.error("Failed to select the checkbox ", checkbox, e);
        }

    }

    public void deselectCheckbox(WebElement checkbox) {
        logger.info("Deselecting the checkbox " + checkbox);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.visibilityOf(checkbox));
            if (checkbox.isSelected()) {
                checkbox.click();
            }
        } catch (Exception e) {
            logger.error("Failed to deselect the checkbox ", checkbox, e);
        }

    }

    public void selectRadioButton(WebElement radioButton) {
        logger.info("Selecting the radio button " + radioButton);
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.visibilityOf(radioButton));
            if (!radioButton.isSelected()) {
                radioButton.click();
            }
        } catch (Exception e) {
            logger.error("Failed to selec the radio button ", radioButton, e);
        }
    }

    public void selectRadioButtonByValue(List<WebElement> radioButtons, String value) {
        logger.info("Selecting the radio button by value " + radioButtons);
        try{
            for (WebElement radioButton : radioButtons) {
                if (radioButton.getAttribute("value").equals(value) && !radioButton.isSelected()) {
                    radioButton.click();
                    return;
                }
            }
        } catch (Exception e) {
            logger.error("Failed to select the radio buttons by value ", e);
        }
    }

    public void mouseOverElement(WebElement element) {
        logger.info("Mouse Over the element " + element);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.visibilityOf(element));
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
        } catch (Exception e) {
            logger.error("Failed to mouseover ", element, e);
        }
    }

    public void uploadFile(WebElement fileInput, String filePath) {
        logger.info("Uploading the file " + fileInput);
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            wait.until(ExpectedConditions.visibilityOf(fileInput));
            fileInput.sendKeys(filePath);
        } catch (Exception e) {
            logger.error("Failed to upload the file", fileInput, e);
        }
    }

    public void navigateTo(String url) {
        logger.info("Navigating to URL " + url);
        try{
            driver.get(url);
        } catch (Exception e) {
            logger.error("Error while navigating to the new url ", url);
        }
    }

    public void waitUntilPageLoadComplete() {
        logger.info("Waiting till the page load is complete");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppConstants.GLOBAL_WAIT));
            // Wait until the page is completely loaded
            wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            logger.error("Error while waiting for the page to load");
        }
    }

    public void hoverOverWithJS(WebElement element) {
        logger.info("Performing mouseover with JS Executor");
        try {
            // Temporarily increase the size of the element for interaction
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].style.transform = 'scale(2)';", element);

            // Scroll the element into view
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);

            // Trigger mouseover event on the element
            String script = "var event = new MouseEvent('mouseover', {" +
                    "bubbles: true," +
                    "cancelable: true," +
                    "view: window" +
                    "}); arguments[0].dispatchEvent(event);";

            jsExecutor.executeScript(script, element);

            // Optionally, revert the element size back to original (reset scale)
            jsExecutor.executeScript("arguments[0].style.transform = 'scale(1)';", element);
        } catch (Exception e) {
            logger.error("Unable to mouse over ", e);
        }
    }

}