package listeners;

import java.io.IOException;

import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.relevantcodes.extentreports.LogStatus;

import baseClass.TestBase;
import testcases.TestMethods;

public class ListenerClass extends TestBase implements ITestListener{

	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult arg0) {
		
		
		try {
			TestMethods.captureScreenshot();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.setProperty("org.uncommons.reportng.escape-output","false");
		Reporter.log("click to view screenshot  " + "<a target=\"blank\" href="+TestMethods.screenshotName+">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"blank\" href="+TestMethods.screenshotName+"><img src="+TestMethods.screenshotName+" width=200 height=200></img></a>");
		
		//Extent Reporting
		test.log(LogStatus.FAIL, (arg0.getName().toUpperCase()+" Failed with Exception : " + arg0.getThrowable()));
		log.debug("Adding screenshot to extent reports");
		test.log(LogStatus.FAIL, test.addScreenCapture(TestMethods.screenshotName));
		repo.endTest(test);
		repo.flush();
		
	}

	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestStart(ITestResult arg0) {
		
		test = repo.startTest(arg0.getName().toUpperCase());
		
	}

	public void onTestSuccess(ITestResult arg0) {
		
		test.log(LogStatus.PASS, (arg0.getName().toUpperCase()+" PASS"));
		
		repo.endTest(test);
		repo.flush();
		
		
	}

}
