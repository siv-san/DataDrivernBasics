package testcases;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import baseClass.TestBase;
import utilities.ExcelReader;

public class BankManagerLoginTest extends TestBase {

	@Test(priority = 1)
	public void bankManagerLoginTest() {

		driver.findElement(By.cssSelector(OR.getProperty("bmlBtn_CSS"))).click();
		log.debug("Clicked Bank Manager Login Button");
		test.log(LogStatus.INFO, "Logged in as Bank Manager");

	}

	@Test(priority = 2, dependsOnMethods = "bankManagerLoginTest")
	public void addCustomerTest_01() {

		driver.findElement(By.cssSelector(OR.getProperty("addCust_CSS"))).click();
		log.debug("Clicked Add Customer Button");
		test.log(LogStatus.INFO, "Opening Add Customer page");

	}

	@Test(priority = 3, dependsOnMethods = "addCustomerTest_01", dataProvider = "getData", dataProviderClass = testcases.TestDataProvider.class)
	public void addCustomerTest_02(Hashtable<String, String> data) throws InterruptedException {

		driver.findElement(By.cssSelector(OR.getProperty("firstName_CSS"))).sendKeys(data.get("firstName"));
		driver.findElement(By.cssSelector(OR.getProperty("lastName_CSS"))).sendKeys(data.get("lastName"));
		driver.findElement(By.cssSelector(OR.getProperty("postCode_CSS"))).sendKeys(data.get("postCode"));
		driver.findElement(By.cssSelector(OR.getProperty("submitBtn_CSS"))).click();
		
		TestMethods.validateAlert(OR.getProperty("ac_alert_expected"));
	}

	@Test(priority = 4, dependsOnMethods = "addCustomerTest_02")
	public void openAccountTest_01() {

		driver.findElement(By.cssSelector(OR.getProperty("openAcc_CSS"))).click();
		log.debug("Clicked Open Account Button");
		test.log(LogStatus.INFO, "Opened Account opening page");

	}

	@Test(priority = 5, dependsOnMethods = "openAccountTest_01", dataProvider = "getData", dataProviderClass = testcases.TestDataProvider.class)
	public void openAccountTest_02(Hashtable<String, String> data) {

		for (int i = 1; i <= 3; i++) {
			WebElement cus_name = driver.findElement(By.id(OR.getProperty("selCustomer_ID")));
			Select select = new Select(cus_name);
			select.selectByVisibleText(data.get("firstName") + " " + data.get("lastName"));
			log.debug("Customer Name Selected !!!");
			test.log(LogStatus.INFO, "Customer Name Selected");

			WebElement cur_select = driver.findElement(By.id(OR.getProperty("selCurrency_ID")));
			select = new Select(cur_select);

			if (i == 1) {
				select.selectByVisibleText(data.get("currency1"));
				test.log(LogStatus.INFO, ("Selected currency : "+(data.get("currency1"))));
			} else if (i == 2) {
				select.selectByVisibleText(data.get("currency2"));
				test.log(LogStatus.INFO, ("Selected currency : "+(data.get("currency2"))));
			} else if (i == 3) {
				select.selectByVisibleText(data.get("currency3"));
				test.log(LogStatus.INFO, ("Selected currency : "+(data.get("currency3"))));
			}
			log.debug("Currency Selected !!!");
			driver.findElement(By.cssSelector(OR.getProperty("processBtn_CSS"))).click();
			log.debug("Clicked Process Button");
			TestMethods.validateAlert(OR.getProperty("oa_alert_expected"));
		}
	}

	@Test(priority = 6, dependsOnMethods = "openAccountTest_02")
	public void validateAccountOpeningTest_01() {

		driver.findElement(By.cssSelector(OR.getProperty("showCus_CSS"))).click();
		log.debug("Clicked Customer Button");
		test.log(LogStatus.INFO, "Opened Customer List page");

	}

	@Test(priority = 7, dependsOnMethods = "validateAccountOpeningTest_01", dataProvider = "getData", dataProviderClass = testcases.TestDataProvider.class)
	public void validateAccountOpeningTest_02(Hashtable<String, String> data) throws IOException, InterruptedException {

		driver.findElement(By.cssSelector(OR.getProperty("searchBtn_CSS"))).sendKeys(data.get("firstName"));
		String fName = driver.findElement(By.cssSelector(OR.getProperty("resFName_CSS"))).getText();
		String lName = driver.findElement(By.cssSelector(OR.getProperty("resLName_CSS"))).getText();
		String pCode = driver.findElement(By.cssSelector(OR.getProperty("resPCode_CSS"))).getText();
		if (fName.equals(data.get("firstName")) && lName.equals(data.get("lastName")) && pCode.equals(data.get("postCode"))) {

			log.debug("Customer account creation validated");
			test.log(LogStatus.INFO, "Customer account creation validated");

		} else {

			log.debug("Customer account creation validation failed");
			test.log(LogStatus.INFO, "Customer account creation validation failed");
		}

		driver.navigate().refresh();
		log.debug("Refreshing web page");
		TestMethods.getAccDetailsToExcel();

		// back to home page
		driver.navigate().refresh();
		log.debug("Refreshing web page");
		driver.findElement(By.cssSelector(OR.getProperty("homeBtn_CSS"))).click();
		log.debug("Clicked Home Button");
		test.log(LogStatus.INFO, "Back to Home page");

	}

}
