package pages.dp2;

import base.BasePage;
import constants.AppConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.ElementUtils;
import utilities.WriteToCSV;
import java.util.ArrayList;
import java.util.List;

public class BullsHomePage extends BasePage {

    private ElementUtils elementUtils;
    private WriteToCSV csvUtils;
    private static final Logger logger = LogManager.getLogger(BullsHomePage.class);

    @FindBy(xpath = "//h2[contains(@class,'text-center')]")
    private WebElement eleHeader;

    private String footer = "//nav[contains(@class,'text-center')]";

    public BullsHomePage(WebDriver driver){
        super(driver);
        elementUtils = new ElementUtils(driver);
        csvUtils = new WriteToCSV();
    }

    public int footerNavCount(){
        return elementUtils.locateElements("xpath",footer).size();
    }

    public BullsHomePage navigateToBullPage(String url){
        elementUtils.navigateTo(url);
        return new BullsHomePage(driver);
    }

    public String verifyPageIsNavigated(){
        String actualTitle = elementUtils.getPageTitle();
        return actualTitle;
    }

    public BullsHomePage retriveFooterLinks(){
        List<String> links = new ArrayList<>();
        for(int i=0; i<footerNavCount();i++){
            WebElement eleFooter = elementUtils.locateElement("xpath",footer+"["+(i+1)+"]");
            WebElement eleFooterTitle = (elementUtils.locateElement("xpath",footer+"["+(i+1)+"]/h2"));
            logger.info("Printing Footer Title " +elementUtils.getText(eleFooterTitle));
            //Adding Footer Title to the list
            links.add(elementUtils.getText(eleFooterTitle));
            logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            List<WebElement> footerlist = eleFooter.findElements(By.tagName("a"));
            for(int j=0; j<footerlist.size(); j++){
                logger.info("Printing Footer Links " +footerlist.get(j).getDomAttribute("href"));
                String link = footerlist.get(j).getDomAttribute("href");
                if(links.contains(link))
                {
                   logger.error("Duplicate Link " + link);
                }
                links.add(link);
            }
            csvUtils.writeCSV(links, AppConstants.CSV_FILE_PATH);
            links.clear();
            logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
        return new BullsHomePage(driver);
    }

}
