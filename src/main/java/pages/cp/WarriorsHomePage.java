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

    @FindBy(id = "search-icon-desktop")
    private WebElement eleSearch;

    @FindBy(name = "search")
    private WebElement eleSearchText;

    @FindBy(xpath = "//button[@data-testid='nav-search-close-icon']")
    private WebElement eleCloseSearch;

    private String eleContent =  "(//ul[@data-testid='content-grid-'])[2]/li";

    private String eleContentTime = "((//ul[@data-testid='content-grid-'])[2]//time//span)";

    private  int videoCount;

    public WarriorsHomePage(WebDriver driver){
        super(driver);
        elementUtils = new ElementUtils(driver);
    }

    public void closePopUp(){
        logger.info("Closing the pop up");
        elementUtils.click(eleClosePopUp);
    }

    public void mouseOverShop(){
        logger.info("Mouseover the shop link");
        elementUtils.mouseOverElement(eleShop);
    }

    public void clickOnMens(){
        logger.info("Clicking the mens link");
        elementUtils.click(eleMens);
        elementUtils.switchToNewWindow();
    }

    public void mouseOverMenu(){
        logger.info("Mouseover the menu link");
        elementUtils.waitUntilPageLoadComplete();
        elementUtils.mouseOverElement(eleMenu);
    }

    public void clickOnNewAndFeatures() {
        logger.info("Clicking the News and Features link");
        elementUtils.waitUntilPageLoadComplete();
        elementUtils.mouseOverElement(eleMenu);
        elementUtils.click(eleNewsAndFeatures);
    }

    public void clickAndSearch(WebElement eleSearch, String text){
        elementUtils.click(eleSearch);
        elementUtils.sendKeys(eleSearchText,text);
        elementUtils.click(eleCloseSearch);
    }

    public void getTotalVideoCount(String text){
        logger.info("Finding the total video counts");
        clickAndSearch(eleSearch,text);
        clickAndSearch(eleSearch,text);
        clickAndSearch(eleSearch,text);
        clickAndSearch(eleSearch,text);
        clickAndSearch(eleSearch,text);
        clickAndSearch(eleSearch,text);
        List<WebElement> videos = elementUtils.locateElements("xpath",eleContent);
        videoCount = videos.size();
        logger.info("Total Video Counts is " +  (videoCount-1));
        getVideoCountGreaterThanThreeDays();
    }

    public void getVideoCountGreaterThanThreeDays(){
        logger.info("Finding the total video counts which is greater than 3 days");
        int videoPostedThreeDaysAgo = 0;
        for(int i=1;i<videoCount;i++){
            String timeline= elementUtils.locateElement("xpath",eleContentTime+"["+(i+1)+"]").getText();
            int time = Integer.parseInt(timeline.substring(0,1));
            if(time>=3)
                videoPostedThreeDaysAgo++;
        }
       logger.info("Total number of video count greater than 3 days" + videoPostedThreeDaysAgo);
    }

}
