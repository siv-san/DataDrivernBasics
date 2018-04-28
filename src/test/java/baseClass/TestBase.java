package baseClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import testcases.TestMethods;
import utilities.ExcelReader;
import utilities.ExtentManager;

public class TestBase {

	public static WebDriver driver;
	public static Properties OR = new Properties();
	public static Properties config = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\testdata.xlsx");
	public static TestMethods method;
	public static WebElement selection;
	public static WebDriverWait wait;
	
	//Excel Writing
	protected Workbook wb = new XSSFWorkbook();
	protected FileOutputStream fileout;
	
	//Extent Reports
	public static ExtentReports repo = ExtentManager.getInstance();
	public static ExtentTest test;

	@BeforeSuite
	public void setup() {

		//read properties setup
		try {
			fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			OR.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			config.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Browser launch setup
		if (config.getProperty("browser").equals("firefox")) {
			
			System.setProperty("webdriver.gecko.driver", (System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\geckodriver.exe"));
			driver = new FirefoxDriver();
			log.debug("Firefox Launched !!!");
		
		} else if(config.getProperty("browser").equals("chrome")) {
			
			System.setProperty("webdriver.chrome.driver", (System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe"));
			driver = new ChromeDriver();
			log.debug("Chrome Launched !!!");
			
		} else if(config.getProperty("browser").equals("ie")) {
			
			System.setProperty("webdriver.ie.driver", (System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\IEDriverServer.exe"));
			driver = new InternetExplorerDriver();
			log.debug("Internet Explorer Launched !!!");
		}
		
		//Launch website set up
		driver.get(config.getProperty("testsiteurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		
		
	}
	
	public static boolean isElementPresent(By by) {
		
		try {
			
			driver.findElement(by);
			return true;
			
		}catch(NoSuchElementException e) {
			
			log.debug("Element not found");
			return false;
			
		}
		
	}

	
	
	/*@AfterSuite
	public void tearDown() throws InterruptedException {

		if(driver != null) {
		Thread.sleep(2000);
		driver.quit();
		log.debug("Closing the Browser !!!");
		}
	}*/

}
