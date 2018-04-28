package testcases;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import baseClass.TestBase;
import utilities.ExcelReader;

public class TestMethods extends TestBase {

	// Validating Alert Message
	public static void validateAlert(String alertMsg_expected) {

		log.debug("Validating Alert Message");
		Alert alert = driver.switchTo().alert();
		String alertMsg_actual = driver.switchTo().alert().getText();
		log.debug(alertMsg_actual);
		// String alertMsg_expected = OR.getProperty("alertMsg_expected");

		if (alertMsg_actual.contains(alertMsg_expected)) {

			alert.accept();
			log.debug(alertMsg_expected);
			test.log(LogStatus.INFO, alertMsg_expected);
			test.log(LogStatus.INFO, "Alert Message Validated and Handled");

		} else {

			log.debug("Failed to :" + alertMsg_actual);
			test.log(LogStatus.INFO, alertMsg_actual);

		}
	}

	static String colNames;
	static String colName;

	public static void getAccDetailsToExcel() throws IOException, InterruptedException {

		Workbook wb = new XSSFWorkbook();
		FileOutputStream fileout = new FileOutputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\accDetails.xlsx");
		Sheet sheet1 = wb.createSheet("custAccDetails");
		wb.write(fileout);
		fileout.close();

		log.debug("Excel Workbook created with sheet : custAccDetails");

		ExcelReader excelWrite = new ExcelReader(
				System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\accDetails.xlsx");
		String sheetName = "custAccDetails";

		JavascriptExecutor js = (JavascriptExecutor) driver;

		for (int col = 1; col <= 4; col++) {
			// String locator = "table > thead > tr > td:nth-child(" + col + ")";
			String locator = (OR.getProperty("colNameStart_CSS")) + col + (OR.getProperty("colNameEnd_CSS"));
			System.out.println(locator);
			colNames = driver.findElement(By.cssSelector(locator)).getText();
			System.out.println(colNames);
			
			//Remove space from Column Titles
			String colName = null;

			if (colNames.contains("First Name")) {
				colName = colNames.substring(0, 5) + colNames.substring(6);
			} else if (colNames.contains("Last Name")) {
				colName = colNames.substring(0, 4) + colNames.substring(5);
			} else if (colNames.contains("Post Code")) {
				colName = colNames.substring(0, 4) + colNames.substring(5);
			} else if (colNames.contains("Account Number")) {
				colName = colNames.substring(0, 7) + colNames.substring(8);
			}

			System.out.println(colName);
			excelWrite.addColumn(sheetName, colName);
			log.debug("Writing column : " + colName + " started");

			for (int row = 1; row <= 6; row++) {

				log.debug("Writing row " + row + " started");
				if (row >= 6) {
					WebElement element = driver.findElement(By.cssSelector(
							(OR.getProperty("rowStart_CSS")) + (row - 1) + (OR.getProperty("rowEnd_CSS"))));
					js.executeScript("arguments[0].scrollIntoView();", element);
				}
				String locator2 = (OR.getProperty("colBodyStart_CSS")) + row + (OR.getProperty("colBodyMid_CSS")) + col
						+ (OR.getProperty("colBodyEnd_CSS"));
				System.out.println(locator2);
				WebElement x = driver.findElement(By.cssSelector(locator2));
				System.out.println(x.getText());
				excelWrite.setCellData(sheetName, colName, row + 1, x.getText());
				log.debug("Writing row " + row + " completed");
			}
			log.debug("writing column : " + colName + " completed");
		}
		test.log(LogStatus.INFO, "Customer list written in new excel Workbook");
	}

	public static String screenshotName;
	public static String screenshotLocation1;
	public static String screenshotLocation2;
	
	public static void captureScreenshot() throws IOException {
		
		//define unique screenshot name
		Date d = new Date();
		screenshotName = (d.toString().replace(":", "_").replace(" ", "_"))+".jpg";
		screenshotLocation1 = (System.getProperty("user.dir"))+("\\target\\surefire-reports\\html\\")+screenshotName;
		screenshotLocation2 = (System.getProperty("user.dir")+"\\test-output\\html\\"+screenshotName);
		
		//TakesScreenshot on failure
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//add screenshot to the report - Surefire & reportng
		FileUtils.copyFile(screenshot, new File(screenshotLocation1));
		FileUtils.copyFile(screenshot, new File(screenshotLocation2));
		test.log(LogStatus.INFO, "Screenshot captured on failure of testcase");
		
	}
	
	
	public static void validateAcc(String currency_expected) {
		
		String currency_actual = driver.findElement(By.cssSelector(OR.getProperty("valCurrency_CSS"))).getText();
		System.out.println(currency_actual);
		System.out.println(currency_expected);
		
		Assert.assertTrue(currency_actual.equals(currency_expected));
		log.debug("Account details validated for : " +currency_expected+ " Account");
		test.log(LogStatus.INFO, ("Account details validated for : " +currency_expected+ " Account"));
		
	}
	
	@Test(dataProvider = "getData", dataProviderClass = TestDataProvider.class)
	public static void validateAlertMessage(Hashtable<String, String> data, String locator, String alert_expected) {
		
		String alert_actual = driver.findElement(By.cssSelector(OR.getProperty(locator))).getText();
		//String alert_expected = data.get("depositAlert");
		
		Assert.assertTrue(alert_actual.equals(alert_expected));
		log.debug(alert_actual);
		test.log(LogStatus.INFO, alert_actual);
		
	}
	
	public static Date d;
	
	public static void transTimeStamp(String locator) {
		
		//d = new Date();
		//Sat Apr 14 10:57:30 IST 2018
		String month = d.toString().substring(4, 7);
		String date = d.toString().substring(8, 10);
		String year = d.toString().substring(24);
		String time = d.toString().substring(11, 16);
		
		String timeStamp_acual = driver.findElement(By.cssSelector(OR.getProperty(locator))).getText();
		//"trans1_Time_CSS"
		Assert.assertTrue(timeStamp_acual.contains(month));
		Assert.assertTrue(timeStamp_acual.contains(date));
		Assert.assertTrue(timeStamp_acual.contains(year));
		Assert.assertTrue(timeStamp_acual.contains(time));
		
		log.debug("Time Stamp varified");
		test.log(LogStatus.INFO, "Time Stamp validated");

	}

	@Test(dataProvider = "getData", dataProviderClass = TestDataProvider.class)
	public static void validateData(Hashtable<String, String> data, String locator, String value) {
		
		String actual = driver.findElement(By.cssSelector(OR.getProperty(locator))).getText();
		System.out.println(actual +", "+ locator);
		//String type_actual = driver.findElement(By.cssSelector(OR.getProperty(type_locator))).getText();
		String expected = data.get(value);
//		String type_expected = data.get("depositType");
		
		System.out.println(expected+", "+ value);
		Assert.assertTrue(expected.contains(actual));
		
		log.debug(value +" of : " + expected + " is validated");
		test.log(LogStatus.INFO, (value +" of : " + expected + " is validated"));
		
		
	}
	
	
	
	
	
	
	
	
}
