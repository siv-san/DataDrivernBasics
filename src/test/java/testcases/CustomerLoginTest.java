package testcases;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import baseClass.TestBase;
import utilities.ExcelReader;

public class CustomerLoginTest extends TestBase {

	@Test(priority = 1)
	public void customerLogin() {

		driver.findElement(By.cssSelector(OR.getProperty("clBtn_CSS"))).click();
		log.debug("Clicked customer login button");
		test.log(LogStatus.INFO, "Clicked customer login button");

	}

	@Test(priority = 2, dependsOnMethods = "customerLogin", dataProvider = "getData", dataProviderClass = testcases.TestDataProvider.class)
	public void selectCustomer(Hashtable<String, String> data) throws InterruptedException {

		WebElement custName = driver.findElement(By.id(OR.getProperty("selCustName_ID")));
		Select select = new Select(custName);
		select.selectByVisibleText(data.get("firstName") + " " + data.get("lastName"));
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(OR.getProperty("loginBtn_CSS"))));
		test.log(LogStatus.INFO, "Customer selected");
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("loginBtn_CSS"))));
		log.debug("Login Button is visible");
		test.log(LogStatus.INFO, "Login Button is visible");
		driver.findElement(By.cssSelector(OR.getProperty("loginBtn_CSS"))).click();
		log.debug("Clicked login Button");
		test.log(LogStatus.INFO, ("Logging in for customer : " + (data.get("firstName") + " " + data.get("lastName"))));

	}

	@Test(priority = 3, dependsOnMethods = "selectCustomer", dataProvider = "getData", dataProviderClass = TestDataProvider.class)
	public void validateCustomerAcc(Hashtable<String, String> data) {

		String cusName_actual = driver.findElement(By.cssSelector(OR.getProperty("valName_CSS"))).getText();
		System.out.println(cusName_actual);

		Assert.assertTrue(cusName_actual.equals((data.get("firstName")) + " " + (data.get("lastName"))));
		test.log(LogStatus.INFO, "Customer Account Name Validated");

		Select select = new Select(driver.findElement(By.id(OR.getProperty("selAcc_ID"))));
		List<WebElement> selAcc = select.getOptions();
		int listSize = selAcc.size();
		System.out.println(listSize);

		String cusAcc = null;

		for (int i = 0; i < listSize; i++) {

			if (i == 0) {
				System.out.println(cusAcc = selAcc.get(i).getText());
				select.selectByVisibleText(cusAcc);
				TestMethods.validateAcc(data.get("currency1"));
			}
			if (i == 1) {
				System.out.println(cusAcc = selAcc.get(i).getText());
				select.selectByVisibleText(cusAcc);
				TestMethods.validateAcc(data.get("currency2"));
			}
			if (i == 2) {
				System.out.println(cusAcc = selAcc.get(i).getText());
				select.selectByVisibleText(cusAcc);
				TestMethods.validateAcc(data.get("currency3"));
			}
		}
	}

	@Test(priority = 4, dependsOnMethods = "validateCustomerAcc")
	public void deposit_01() {

		driver.findElement(By.cssSelector(OR.getProperty("depositBtn_CSS"))).click();
		log.debug("Clicked Deposit Button");
		test.log(LogStatus.INFO, "Deposit page opened");

	}

	@Test(priority = 5, dependsOnMethods = "deposit_01", dataProvider = "getData", dataProviderClass = TestDataProvider.class)
	public void deposit_02(Hashtable<String, String> data) {

		driver.findElement(By.cssSelector(OR.getProperty("amount_CSS"))).sendKeys(data.get("deposit"));
		driver.findElement(By.cssSelector(OR.getProperty("deposit_CSS"))).click();
		TestMethods.d = new Date();
		System.out.println(TestMethods.d);
		log.debug("Clicked Deposit button");
		test.log(LogStatus.INFO, ("Deposited : " + (data.get("deposit"))));

		TestMethods.validateAlertMessage(data, "valAlert_CSS", (data.get("depositAlert")));

	}

	/*@Test(priority = 6, dependsOnMethods = "deposit_02", dataProvider = "getData", dataProviderClass = TestDataProvider.class)
	public void validateDepositTrans(Hashtable<String, String> data) throws InterruptedException {

		driver.findElement(By.cssSelector(OR.getProperty("transBtn_CSS"))).click();
		driver.navigate().refresh();
		driver.findElement(By.cssSelector(OR.getProperty("backBtn_CSS"))).click();
		driver.findElement(By.cssSelector(OR.getProperty("transBtn_CSS"))).click();

		TestMethods.transTimeStamp("trans1_Time_CSS");
		TestMethods.validateData(data, "trans1_Amount_CSS", "deposit");
		TestMethods.validateData(data, "trans1_type_CSS", "depositType");
		Thread.sleep(2000);
		driver.findElement(By.cssSelector(OR.getProperty("backBtn_CSS"))).click();
		driver.navigate().refresh();
	}*/

	/*@Test(priority = 7, dependsOnMethods = "validateDepositTrans")
	public void withdrawl_01() {

		driver.findElement(By.cssSelector(OR.getProperty("wdrawlBtn_CSS"))).click();
		log.debug("Clicked Withdrawl Button");
		test.log(LogStatus.INFO, "Withdrawl page opened");

	}*/

	/*@Test(priority = 8, dependsOnMethods = "withdrawl_01", dataProvider = "getData", dataProviderClass = TestDataProvider.class)
	public void withdrawl_02(Hashtable<String, String> data) {

		// Invalid withdrawl
		driver.findElement(By.cssSelector(OR.getProperty("wdAmount_CSS"))).sendKeys(data.get("invalidWithdrawl"));
		driver.findElement(By.cssSelector(OR.getProperty("withdraw_CSS"))).click();
		log.debug("Invalid withdrawl : Clicked Withdraw button");
		test.log(LogStatus.INFO, ("Invalid Withdrawl of : " + (data.get("deposit"))));

		TestMethods.validateAlertMessage(data, "wdValAlert_CSS", (data.get("invalidWdrawlAlert")));
		log.debug("Invalide withdrawl test passed");
		test.log(LogStatus.INFO, "Invalide withdrawl test passed");

		// valid withdrawl
		driver.findElement(By.cssSelector(OR.getProperty("wdAmount_CSS"))).sendKeys(data.get("validWithdrawl"));
		driver.findElement(By.cssSelector(OR.getProperty("withdraw_CSS"))).click();
		log.debug("Valid withdrawl : Clicked Withdraw button");
		test.log(LogStatus.INFO, ("Valid Withdrawl of : " + (data.get("deposit"))));

		TestMethods.validateAlertMessage(data, "wdValAlert_CSS", (data.get("valWdrawlAlert")));
		log.debug("Valide withdrawl test passed");
		test.log(LogStatus.INFO, "Valide withdrawl test passed");

	}
*/
}
