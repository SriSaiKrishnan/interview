package pages.cp;

import base.BasePage;
import constants.AppConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.ElementUtils;
import utilities.WriteToTextFile;

import java.util.List;

public class GoldenWarriorsPage extends BasePage {

    private ElementUtils elementUtils;
    private WriteToTextFile writeToTextFile;

    private static final Logger logger = LogManager.getLogger(GoldenWarriorsPage.class);

    @FindBy(xpath = "//span[text()='Jackets']")
    private WebElement eleJackets;

    private String pagination = "(//div[@class = 'pagination-container'])[1]/ul/li";

    private String cardRow = "(//div[@class='product-card row']/div[2])";

    public GoldenWarriorsPage(WebDriver driver){
        super(driver);
        elementUtils = new ElementUtils(driver);
        writeToTextFile = new WriteToTextFile();
    }

    public GoldenWarriorsPage selectJackets(){
        logger.info("Selecting the radio button jackets");
        elementUtils.click(eleJackets);
        return new GoldenWarriorsPage(driver);
    }

    public GoldenWarriorsPage extractJacketData(){
        List<WebElement> pageCounter = elementUtils.locateElements("xpath",pagination);
        int count = pageCounter.size()-2;
        for (int i=1; i<count; i++){
            WebElement pageNumber = elementUtils.locateElement("xpath", pagination+"["+(i+1)+"]");
            pageNumber.click();
            List<WebElement> cardRows  =  elementUtils.locateElements("xpath",cardRow);
            for (int j=1; j<=cardRows.size();j++){
                WebElement text = driver.findElement(By.xpath(cardRow+"["+j+"]"));
                writeToTextFile.writeToTextFile(text.getText(), AppConstants.TEXT_FILE_PATH);
            }
        }
        return new GoldenWarriorsPage(driver);
    }

}
