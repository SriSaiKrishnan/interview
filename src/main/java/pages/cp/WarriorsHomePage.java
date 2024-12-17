package pages.cp;

import base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.ElementUtils;

import java.util.List;

public class WarriorsHomePage extends BasePage {

    private ElementUtils elementUtils;

    private static final Logger logger = LogManager.getLogger(WarriorsHomePage.class);

    @FindBy(xpath = "//div[contains(text(), 'x') and contains(@class, 'p-2')]")
    private WebElement eleClosePopUp;

    @FindBy(css = "a.accent-primary-border[href='https://shop.warriors.com/']")
    private WebElement eleShop;

    @FindBy(xpath = "(//a[@title=\"Men's\" and contains(@href, 'golden-state-warriors-men')])[1]")
    private WebElement eleMens;

    @FindBy(xpath = "//nav[@aria-label='header-secondary-menu']/ul/li/a/span")
    private WebElement eleMenu;

    @FindBy(xpath = "(//a[@title = 'News & Features'])[1]")
    private WebElement eleNewsAndFeatures;


    public WarriorsHomePage(WebDriver driver){
        super(driver);
        elementUtils = new ElementUtils(driver);
    }

    public WarriorsHomePage closePopUp(){
        logger.info("Closing the pop up");
        elementUtils.click(eleClosePopUp);
        return new WarriorsHomePage(driver);
    }

    public WarriorsHomePage mouseOverShop(){
        logger.info("Mouseover the shop link");
        elementUtils.mouseOverElement(eleShop);
        return new WarriorsHomePage(driver);
    }

    public WarriorsHomePage mouseOverMenu(){
        logger.info("Mouseover the menu link");
        elementUtils.waitUntilPageLoadComplete();
        elementUtils.mouseOverElement(eleMenu);
        return new WarriorsHomePage(driver);
    }

    public GoldenWarriorsPage clickOnMens(){
        logger.info("Clicking the mens link");
        elementUtils.click(eleMens);
        elementUtils.switchToNewWindow();
        return new GoldenWarriorsPage(driver);
    }

    public NewsAndFeaturePage clickOnNewAndFeatures() {
        logger.info("Clicking the News and Features link");
        elementUtils.waitUntilPageLoadComplete();
        elementUtils.mouseOverElement(eleMenu);
        elementUtils.click(eleNewsAndFeatures);
        return new NewsAndFeaturePage(driver);
    }

}
