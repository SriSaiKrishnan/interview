package pages.cp;

import base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.ElementUtils;

import java.util.List;

public class NewsAndFeaturePage extends BasePage {

    private ElementUtils elementUtils;

    private static final Logger logger = LogManager.getLogger(WarriorsHomePage.class);

    @FindBy(id = "search-icon-desktop")
    private WebElement eleSearch;

    @FindBy(name = "search")
    private WebElement eleSearchText;

    @FindBy(xpath = "//button[@data-testid='nav-search-close-icon']")
    private WebElement eleCloseSearch;

    private String eleContent =  "(//ul[@data-testid='content-grid-'])[2]/li";

    private String eleContentTime = "((//ul[@data-testid='content-grid-'])[2]//time//span)";

    private  int videoCount;

    public NewsAndFeaturePage(WebDriver driver){
        super(driver);
        elementUtils = new ElementUtils(driver);
    }


    public NewsAndFeaturePage clickAndSearch(WebElement eleSearch, String text){
        elementUtils.click(eleSearch);
        elementUtils.sendKeys(eleSearchText,text);
        elementUtils.click(eleCloseSearch);
        return new NewsAndFeaturePage(driver);
    }

    public NewsAndFeaturePage getTotalVideoCount(String text){
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
        return new NewsAndFeaturePage(driver);
    }

    public NewsAndFeaturePage getVideoCountGreaterThanThreeDays(){
        logger.info("Finding the total video counts which is greater than 3 days");
        int videoPostedThreeDaysAgo = 0;
        for(int i=1;i<videoCount;i++){
            String timeline= elementUtils.locateElement("xpath",eleContentTime+"["+(i+1)+"]").getText();
            int time = Integer.parseInt(timeline.substring(0,1));
            if(time>=3)
                videoPostedThreeDaysAgo++;
        }
        logger.info("Total number of video count greater than 3 days" + videoPostedThreeDaysAgo);
        return new NewsAndFeaturePage(driver);
    }

}
