package rough;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import baseClass.TestBase;
import utilities.ExcelReader;

public class Check_1 extends TestBase {

	public static Object[][] data;

	public static Object[][] getData() {

		String sheetName = "addCustomerTest";

		int rowCount = excel.getRowCount(sheetName);
		System.out.println(excel.getRowCount(sheetName));

		int colCount = excel.getColumnCount(sheetName);
		System.out.println(excel.getColumnCount(sheetName));

		data = new Object[rowCount - 1][colCount];

		// data[0][0] = "";
		for (int row = 2; row <= rowCount; row++) {

			for (int col = 0; col < colCount; col++) {

				data[row - 2][col] = excel.getCellData(sheetName, col, row);

				// System.out.println(data[row-2][col]);
			}
		}
		return data;
	}

	public static void main(String[] arg) throws IOException, InterruptedException {

		getData();
		System.out.println(data);
		
			// public static void getAccDetailsToExcel() throws IOException {
			driver = new FirefoxDriver();
			driver.get("http://www.way2automation.com/angularjs-protractor/banking/#/manager/list");
			Thread.sleep(2000);

			Properties OR = new Properties();
			FileInputStream fis = new FileInputStream(
					"F:\\Sivasankari\\LiveProjects\\DataDrivenFramework\\src\\test\\resources\\properties\\OR.properties");
			OR.load(fis);

			Workbook wb = new XSSFWorkbook();
			FileOutputStream fileout = new FileOutputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\accDetails.xlsx");
			Sheet sheet1 = wb.createSheet("custAccDetails");
			wb.write(fileout);
			fileout.close();

			ExcelReader excelWrite = new ExcelReader(
					System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\accDetails.xlsx");
			String sheetName = "custAccDetails";

			JavascriptExecutor js = (JavascriptExecutor) driver;

			for (int col = 1; col <= 4; col++) {
				// String locator = "table > thead > tr > td:nth-child(" + col + ")";
				String locator = (OR.getProperty("colNameStart_CSS")) + col + (OR.getProperty("colNameEnd_CSS"));
				System.out.println(locator);
				String colName = driver.findElement(By.cssSelector(locator)).getText();
				System.out.println(colName);
				excelWrite.addColumn(sheetName, colName);

				for (int row = 1; row <= 6; row++) {

					if (row >= 6) {
						WebElement element = driver.findElement(By.cssSelector(
								(OR.getProperty("rowStart_CSS")) + (row - 1) + (OR.getProperty("rowEnd_CSS"))));
						js.executeScript("arguments[0].scrollIntoView();", element);
					} else {
						String locator2 = (OR.getProperty("colBodyStart_CSS")) + row + (OR.getProperty("colBodyMid_CSS"))
								+ col + (OR.getProperty("colBodyEnd_CSS"));
						System.out.println(locator2);
						WebElement x = driver.findElement(By.cssSelector(locator2));
						System.out.println(x.getText());
						excelWrite.setCellData(sheetName, colName, row + 1, x.getText());

					}

				}

			}

		}
	
	
	
	}

