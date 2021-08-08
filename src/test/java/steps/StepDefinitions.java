package steps;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class StepDefinitions {

    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void invokeBrowser(){
        System.out.println("Open browser");
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void teardown() {
        System.out.println("Close browser");
        driver.quit();
    }
    
    @AfterStep
	public void addScreenshot(Scenario scenario) throws IOException {
    	if(scenario.isFailed()) {
		  File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		  byte[] fileContent = FileUtils.readFileToByteArray(screenshot);
		  scenario.attach(fileContent, "image/png", "screenshot");
    	}
	}

    @Given("^Open online calculator application$")
    public void launchOnlineCalculator() {
    	System.out.println("Launching Online Calculator.");
        driver.get("https://www.online-calculator.com/full-screen-calculator/");
    }

    @When("I perform operation on two values and press CE button")
    public void i_enter_following_values_and_press_CE_button(DataTable dataTable) throws AWTException {
        driver.switchTo().frame("fullframe");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("canvas")));
        Map<String, String> rows = dataTable.asMap(String.class, String.class);

        Actions actions = new Actions(driver);
        Action action = actions.moveToElement(driver.findElement(By.id("canvas")))
                .sendKeys(rows.get("value1"))
                .sendKeys(rows.get("operator"))
                .sendKeys(rows.get("value2"))
                .sendKeys(Keys.ENTER)
                .build();
        System.out.println("Performing " + rows.get("operator") + " operation on values: " + rows.get("value1") + " and " + rows.get("value2"));
        action.perform();
    }
    
    @When("I perform operations on several values and press CE button")
    public void i_perform_operations_on_several_values_and_press_ce_button(DataTable dataTable) {
    	driver.switchTo().frame("fullframe");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("canvas")));
        Map<String, String> rows = dataTable.asMap(String.class, String.class);
        
        System.out.println("Performing the operation/s " + rows.get("operators") + " on numbers " + rows.get("values"));
        
        String[] values = rows.get("values").split(",");
        String[] operators = rows.get("operators").split(",");
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.id("canvas")));
        if(operators.length == 1) {
        	for(String value : values) {
        		actions.sendKeys(value).sendKeys(operators[0]);
        	}
        } else {
        	for(int i=0; i<values.length-1; i++) {
        		actions.sendKeys(values[i]).sendKeys(operators[i]);
        	}
        	actions.sendKeys(values[values.length-1]).sendKeys(Keys.ENTER);
        }
    	actions.build().perform();
    }

    @Then("I should be able to see")
    public void i_should_be_able_to_see(DataTable dataTable) {
        Map<String, String> expectedRow = dataTable.asMap(String.class, String.class);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String actualValue = js.executeScript("return exportRoot.showscreen_txt.text").toString();
        System.out.println("Verifying if actual value: " + actualValue + " is equal to expected value: " + expectedRow.get("expected"));
        Assert.assertEquals(expectedRow.get("expected"), actualValue);
    }



}
