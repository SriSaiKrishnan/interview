package stepdefinitions.cp;

import factory.DriverFactory;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.cp.GoldenWarriorsPage;
import pages.cp.WarriorsHomePage;
import runners.TestRunner;

public class WarriorsSteps {

    private WebDriver driver;
    private WarriorsHomePage warriorsHomePage;
    private JsonNode warriorsData;

    public WarriorsSteps(){
       warriorsData = TestRunner.testData.path("Warriors");  // Accessing JSON data
    }

    @Given("the user launches the warriors page")
    public void the_user_launches_the_bulls_page(){
        driver = DriverFactory.getDriver();
        warriorsHomePage = new WarriorsHomePage(driver);
    }

    @When("the user mouse over on shop link")
        public void the_user_mouse_over_on_shop_link(){
        warriorsHomePage.closePopUp();
        warriorsHomePage.mouseOverShop();
        }

    @Then("the user is on golden warriors page")
    public void the_user_is_on_golden_warriors_page(){
        warriorsHomePage.clickOnMens().selectJackets()
                .extractJacketData();
        Assert.assertEquals(warriorsData.path("goldenwarriors-title").asText(),warriorsHomePage.verifyPageIsNavigated());
    }

    @When("the_user_mouse_over_on_sub_menu")
    public void the_user_mouse_over_on_sub_menu() {
        warriorsHomePage.closePopUp();
        warriorsHomePage.mouseOverMenu();
    }

    @Then("the user is on News and Features Page to count the total videos")
    public void the_user_is_On_News_and_Features_Page_to_count_the_total_videos(){
        warriorsHomePage.clickOnNewAndFeatures()
                .getTotalVideoCount(warriorsData.path("searchTest").asText());
        Assert.assertEquals(warriorsData.path("newandfeatures-title").asText(),warriorsHomePage.verifyPageIsNavigated());
    }

}
