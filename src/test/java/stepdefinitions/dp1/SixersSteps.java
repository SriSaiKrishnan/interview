package stepdefinitions.dp1;

import factory.DriverFactory;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.dp1.SixersPage;
import runners.TestRunner;

public class SixersSteps {

    private WebDriver driver;
    private SixersPage sixersPage;
    private JsonNode sixersData;

    public SixersSteps(){
       sixersData =  TestRunner.testData.path("Sixers");
    }

    @Given("the user launches the sixers page")
    public void the_user_launches_the_sixers_page(){
        driver = DriverFactory.getDriver();
        sixersPage = new SixersPage(driver);
    }

    @When("the user is navigated to sixers page")
    public void the_user_navigated_to_sixers_page(){
        sixersPage.navigateToSixersPage(sixersData.path("url").asText());
    }

    @Then("the user count the ticket slides")
    public void the_user_count_the_ticket_slides(){
        sixersPage.getTicketMenuCount();
    }

    @Then("the user retrieves the ticket slides titles")
    public void the_user_retrieves_the_ticket_slides_titles(){
        sixersPage.verifyTicketMenu(sixersData.path("searchTest").asText());
    }

}
