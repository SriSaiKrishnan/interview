package stepdefinitions.dp2;

import factory.DriverFactory;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.dp2.BullsHomePage;
import report.ExtentReport;
import runners.TestRunner;

public class BullsSteps {

    private WebDriver driver;
    private BullsHomePage bullsHomePage;
    private JsonNode bullsData;

    public BullsSteps(){
        bullsData = TestRunner.testData.path("Bulls");  // Accessing JSON data
    }


    @Given("the user launches the bulls page")
    public void the_user_launches_the_bulls_page(){
        ExtentReport.createTest("Find all the hyperlinks in the Footer");
        driver = DriverFactory.getDriver();
        bullsHomePage = new BullsHomePage(driver);
    }

    @When("the page is loaded")
    public void the_page_is_loaded(){
    bullsHomePage.verifyHeader(bullsData.path("verificationtText").asText(), bullsData.path("url").asText());
    }

    @Then("the user get the footer link")
    public void the_user_get_the_footer_links(){
        bullsHomePage.retriveFooterLinks();
    }

}
