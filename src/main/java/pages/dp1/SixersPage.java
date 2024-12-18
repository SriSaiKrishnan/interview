package pages.dp1;

import base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.ElementUtils;
import java.util.ArrayList;
import java.util.List;

public class SixersPage extends BasePage {

    @FindBy(id = "search-icon-desktop")
    private WebElement eleSearch;

    @FindBy(name = "search")
    private WebElement eleSearchText;

    @FindBy(xpath = "//button[@data-testid='nav-search-close-icon']")
    private WebElement eleCloseSearch;

    @FindBy(xpath = "(//a[@href='/sixers/tickets'])[1]")
    private WebElement eleTicket;

    private String eleTicketsMenu = "((//nav[@aria-label='submenu'])[1]//li/a)";

    private ElementUtils elementUtils;

    private static final Logger logger = LogManager.getLogger(SixersPage.class);

    public SixersPage(WebDriver driver){
        super(driver);
        elementUtils = new ElementUtils(driver);
    }

    public SixersPage navigateToSixersPage(String url){
        elementUtils.navigateTo(url);
        return new SixersPage(driver);
    }

    public String verifyPageIsNavigated(){
        String actualTitle = elementUtils.getPageTitle();
        return actualTitle;
    }

    public SixersPage getTicketMenuCount(){
        List<WebElement> ticketMenus = elementUtils.locateElements("xpath", eleTicketsMenu);
        int ticketMenuCount = ticketMenus.size();
        logger.info("Ticket Menu Count is " + ticketMenuCount);
        return new SixersPage(driver);
    }

    public SixersPage clickAndSearch(WebElement eleSearch, String text){
        elementUtils.click(eleSearch);
        elementUtils.sendKeys(eleSearchText,text);
        elementUtils.click(eleCloseSearch);
        return new SixersPage(driver);
    }

    public SixersPage mouseOverTicketMenu(){
        elementUtils.mouseOverElement(eleTicket);
        return new SixersPage(driver);
    }

    public SixersPage verifyTicketMenu(String text){
        clickAndSearch(eleSearch,text);
        clickAndSearch(eleSearch,text);
        clickAndSearch(eleSearch,text);
        clickAndSearch(eleSearch,text);
        clickAndSearch(eleSearch,text);
        clickAndSearch(eleSearch,text);
        List<String> ticketMenuList = new ArrayList<>();
        List<WebElement> ticketMenus = elementUtils.locateElements("xpath", eleTicketsMenu);
        for (int i=0; i<ticketMenus.size();i++){
            WebElement ticketSlide = elementUtils.locateElement("xpath",eleTicketsMenu+"[" + (i+1) +"]");
            mouseOverTicketMenu();
            String ticketMenu = ticketSlide.getText();
            logger.info(ticketMenu);
            ticketMenuList.add(ticketMenu);
        }
        return new SixersPage(driver);
    }

}
