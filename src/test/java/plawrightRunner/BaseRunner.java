package plawrightRunner;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.microsoft.playwright.Page;

import browerSetup.BrowserProperties;
import plawyrightCommand.PlaywrightCommandExecution;
import utilities.ExcelUtil;
import utilities.TestProperties;

public class BaseRunner {

	public static Page page;
	public static PlaywrightCommandExecution command;
	ExcelUtil eUtil = new ExcelUtil();
	ArrayList<String> dataList = new ArrayList<>();
	private static final Logger logger = LogManager.getLogger(BaseRunner.class.getName());
	static TestProperties testProp = TestProperties.getInstance();
	BrowserProperties browserProp = BrowserProperties.getInstance();

	@BeforeSuite
	public void beforeSuite() {
		testProp.initProperties();
		page = browserProp.initBrowser();
		command = new PlaywrightCommandExecution(page);
		logger.info("Navigating to the URL: " + testProp.getBASEURL());
		page.navigate(testProp.getBASEURL());
	}

	@BeforeMethod
	public void beforeMethod() {
	}

	@DataProvider(name = "input")
	public Object[] getTestcase() {
		return eUtil.getTestCase();

	}

	@Test(dataProvider = "input")
	public void executeTestCase(String testID, String testScenario, String testDescription, String keyword,
			String object, String testData, String expectedData, String suiteName) {
		try {
			dataList.add(testID);// 0
			dataList.add(testScenario);// 1
			dataList.add(testDescription);// 2
			dataList.add(keyword);// 3
			dataList.add(object);// 4
			dataList.add(testData);// 5
			dataList.add(expectedData);// 6
			dataList.add(suiteName);// 7
			logger.info("==========================Executing Test Case ID - " + testID + "==========================");
			logger.info("[" + testScenario + "] -> [" + testDescription + "]");
			logger.info("Keyword To Perform: " + keyword);
			command.commandExecutor(dataList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {

		if (result.getStatus() == ITestResult.SUCCESS) {
			logger.info("Test Case Passed");
//            report.passTest();
		} else if (result.getStatus() == ITestResult.FAILURE) {
			logger.info("Test Case Failed");
//			String screenShotPath = executeCommand.screenShot();
//			System.out.println(screenShotPath);
//			report.failTest(screenShotPath, result);
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.info("Test Case Skipped");
//			report.skipTest(result);
		}

		dataList.clear();
	}

	@AfterSuite
	public void afterSuite() throws InterruptedException {
		browserProp.closePlaywright();
	}

}
