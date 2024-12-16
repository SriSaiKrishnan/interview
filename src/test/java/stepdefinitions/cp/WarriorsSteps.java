package stepdefinitions.cp;

import factory.DriverFactory;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.cp.GoldenWarriorsPage;
import pages.cp.WarriorsHomePage;
import report.ExtentReport;
import runners.TestRunner;

public class WarriorsSteps {

    private WebDriver driver;
    private WarriorsHomePage warriorsHomePage;
    private GoldenWarriorsPage goldenWarriorsPage;
    private JsonNode warriorsData;

    public WarriorsSteps(){
       warriorsData = TestRunner.testData.path("Warriors");  // Accessing JSON data
    }

    @Given("the user launches the warriors page")
    public void the_user_launches_the_bulls_page(){
        driver = DriverFactory.getDriver();
        warriorsHomePage = new WarriorsHomePage(driver);
        goldenWarriorsPage = new GoldenWarriorsPage(driver);
    }

    @When("the user clicks on mens link")
        public void the_user_clicks_on_mens_links(){
        ExtentReport.createTest("Store each Jacket Price Title and Top Seller message to a text file");
        warriorsHomePage.closePopUp();
        warriorsHomePage.mouseOverShop();
        warriorsHomePage.clickOnMens();
        }

    @Then("the user is on golden warriors page")
    public void the_user_is_on_golden_warriors_page(){
        goldenWarriorsPage.selectJackets();
        goldenWarriorsPage.extractJacketData();
    }

    @When("the user clicks on News and Features Link")
    public void the_user_clicks_On_New_and_Features_Link() {
        ExtentReport.createTest("To find the total number of videos in News and Features Page");
        warriorsHomePage.closePopUp();
        warriorsHomePage.mouseOverMenu();
        warriorsHomePage.clickOnNewAndFeatures();
    }

    @Then("the user is on News and Features Page to count the total videos")
    public void the_user_is_On_News_and_Features_Page_to_count_the_total_videos(){
        warriorsHomePage.getTotalVideoCount(warriorsData.path("searchTest").asText());
    }
}
