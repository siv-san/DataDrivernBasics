package rough;

import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import baseClass.TestBase;
import testcases.TestDataProvider;
import testcases.TestMethods;

public class Check_2 extends TestBase {

	@Test(priority = 1, dataProvider="getData", dataProviderClass = TestDataProvider.class )
	public void validateCustomerAcc(Hashtable<String, String> data) {

		driver.get("http://www.way2automation.com/angularjs-protractor/banking/#/login");

		driver.findElement(By.cssSelector(OR.getProperty("clBtn_CSS"))).click();

		WebElement custName = driver.findElement(By.id(OR.getProperty("selCustName_ID")));
		Select select1 = new Select(custName);
		select1.selectByVisibleText((data.get("firstName"))+" "+(data.get("lastName")));
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(OR.getProperty("loginBtn_CSS"))));

		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("loginBtn_CSS"))));
		log.debug("Login Button is visible");
		driver.findElement(By.cssSelector(OR.getProperty("loginBtn_CSS"))).click();
		log.debug("Clicked login Button");

		//String cusName_expected = data.get("firstName") + " " + data.get("lastName");
		
		String cusName_actual = driver.findElement(By.cssSelector(OR.getProperty("valName_CSS"))).getText();
		System.out.println(cusName_actual);
		
		Assert.assertTrue(cusName_actual.equals((data.get("firstName"))+" "+(data.get("lastName"))));
		
		Select select = new Select(driver.findElement(By.id(OR.getProperty("selAcc_ID"))));
		List<WebElement> selAcc = select.getOptions();
		int listSize = selAcc.size();
		System.out.println(listSize);
		
		String cusAcc=null;
		
		for(int i=0; i<listSize; i++) {
			
			if(i==0) {
				System.out.println(cusAcc = selAcc.get(i).getText());
				select.selectByVisibleText(cusAcc);
				TestMethods.validateAcc(data.get("currency1"));
			}if(i==1) {
				System.out.println(cusAcc = selAcc.get(i).getText());
				select.selectByVisibleText(cusAcc);
				TestMethods.validateAcc(data.get("currency2"));
			}if(i==2) {
				System.out.println(cusAcc = selAcc.get(i).getText());
				select.selectByVisibleText(cusAcc);
				TestMethods.validateAcc(data.get("currency3"));
			}
		}
	}

	}


