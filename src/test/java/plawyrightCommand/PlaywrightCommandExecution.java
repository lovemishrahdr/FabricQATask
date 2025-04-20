package plawyrightCommand;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.options.SelectOption;

import browerSetup.BrowserProperties;
import utilities.TestProperties;

public class PlaywrightCommandExecution {

	Page page;
	Locator locator;
	private static final Logger logger = LogManager.getLogger(PlaywrightCommandExecution.class.getName());
	public static final int TEST_CASE_ID = 0;
	public static final int TEST_SCENARIO = 1;
	public static final int TEST_DESCRIPTION = 2;
	public static final int TEST_KEYWORD = 3;
	public static final int TEST_OBJECT = 4;
	public static final int TEST_DATA = 5;
	public static final int TEST_EXPECTED_DATA = 6;
	public static final int TEST_SUITE_NAME = 7;
	TestProperties testProp = TestProperties.getInstance();
	public static Map<String, String> hashmap = new HashMap<String, String>();

	public PlaywrightCommandExecution(Page page) {
		this.page = page;
		testProp.initProperties();
	}

	public void commandExecutor(ArrayList<String> dataList) {

		String keyword = dataList.get(TEST_KEYWORD).toLowerCase();
		switch (keyword) {

		case "click":
			clickElement(dataList);
			break;

		case "verifytitle":
			verifyPageTitle(dataList);
			break;

		case "elementvisible":
			elementVisible(dataList);
			break;

		case "generaterandomtext":
			generateAlphaNumericText(dataList);
			break;

		case "entertext":
			enterText(dataList);
			break;

		case "selectbyvalue":
			selectByValue(dataList);
			break;

		case "selectbyindex":
			selectByIndex(dataList);
			break;

		case "selectbyvisibletext":
			selectByVisibleText(dataList);
			break;

		case "storeelementtext":
			storeElementText(dataList);
			break;

		case "pause":
			pause(dataList);
			break;

		case "verifyelementtext":
			verifyElementText(dataList);
			break;

		case "verifynetworkresponse":
			verifyNetworkResponse(dataList);
			break;

		default:
			logger.info("No keyword matching. Please provide a correct keyword.");
			assertFalse(false);
			break;

		}

	}

	public void clickElement(ArrayList<String> dataList) {
		try {
			logger.info("Clicking element: " + dataList.get(TEST_OBJECT));
			page.click(dataList.get(TEST_OBJECT));
			assertTrue(true);
		} catch (Exception e) {
			logger.info(e.getMessage());
			assertFalse(true);
		}
	}

	public void verifyPageTitle(ArrayList<String> dataList) {
		try {
			logger.info("Page Title is: " + page.title());
			if (page.title().contentEquals(dataList.get(TEST_EXPECTED_DATA))) {
				logger.info("Page title matched with given data: " + dataList.get(TEST_EXPECTED_DATA));
				assertTrue(true);
			} else {
				logger.info("Page title not matched with given data: " + dataList.get(TEST_EXPECTED_DATA));
				assertFalse(true);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			assertFalse(true);
		}
	}

	public void elementVisible(ArrayList<String> dataList) {
		try {
			logger.info("Verifiying if element is visible: " + dataList.get(TEST_OBJECT));
			if (page.waitForSelector(dataList.get(TEST_OBJECT)).isVisible()) {
				logger.info("Element is visible");
				assertTrue(true);
			} else {
				logger.info("Element is not visible: " + dataList.get(TEST_OBJECT));
				assertFalse(true);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			assertFalse(true);
		}
	}

	public void generateAlphaNumericText(ArrayList<String> dataList) {
		try {
			StringBuilder textBuilder = new StringBuilder();
			StringBuilder numberBuilder = new StringBuilder();
			String generatedText = "";
			String testData = dataList.get(TEST_DATA).toLowerCase();
			Random random = new Random();
			String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			for (int i = 0; i < 6; i++) {
				textBuilder.append(alphabet.charAt(random.nextInt(alphabet.length())));
			}
			String num = "1234567890";
			for (int i = 0; i < 6; i++) {
				numberBuilder.append(num.charAt(random.nextInt(num.length())));
			}

			if (testData.equalsIgnoreCase("text")) {
				generatedText = generatedText + textBuilder.toString();
			} else if (testData.equalsIgnoreCase("number")) {
				generatedText = generatedText + numberBuilder.toString();
			} else {
				generatedText = generatedText + textBuilder.toString() + numberBuilder.toString();
			}

			if (!dataList.get(TEST_EXPECTED_DATA).isEmpty()) {
				String variableName = dataList.get(TEST_EXPECTED_DATA);
				hashmap.put(variableName, generatedText);
				logger.info("Generated Mail ID: " + generatedText);
				logger.info("Stored in: " + variableName);
				assertTrue(true);
			} else {
				logger.info("No variable name is given in expected output column");
				assertFalse(true);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			assertFalse(true);
		}
	}

	public void enterText(ArrayList<String> dataList) {
		try {
			String testData = dataList.get(TEST_DATA);
			if (hashmap.containsKey(testData)) {
				logger.info("Entering text on element: " + hashmap.get(testData));
				page.fill(dataList.get(TEST_OBJECT), hashmap.get(testData));
			} else {
				logger.info("Entering text on element: " + testData);
				page.fill(dataList.get(TEST_OBJECT), testData);
			}
			assertTrue(true);
		} catch (Exception e) {
			logger.info(e.getMessage());
			assertFalse(true);
		}
	}

	public void selectByValue(ArrayList<String> dataList) {
		try {
			String value = String.valueOf(dataList.get(TEST_DATA));
			logger.info("Select dropdown element " + dataList.get(TEST_OBJECT));
			logger.info("Selecting drop down by value: " + value);
			page.selectOption(dataList.get(TEST_OBJECT), value);
			assertTrue(true);
		} catch (Exception e) {
			logger.info(e.getMessage());
			assertFalse(true);
		}
	}

	public void selectByVisibleText(ArrayList<String> dataList) {
		try {
			logger.info("Select dropdown element " + dataList.get(TEST_OBJECT));
			logger.info("Selecting drop down by visible text: " + dataList.get(TEST_DATA));
			String label;
			if (hashmap.containsKey(dataList.get(TEST_DATA)))
				label = hashmap.get(dataList.get(TEST_DATA));
			else
				label = dataList.get(TEST_DATA);
			page.selectOption(dataList.get(TEST_OBJECT), new SelectOption().setLabel(label));
			assertTrue(true);
		} catch (Exception e) {
			logger.info(e.getMessage());
			assertFalse(true);
		}
	}

	public void selectByIndex(ArrayList<String> dataList) {
		try {
			int index = Integer.valueOf(dataList.get(TEST_DATA));
			logger.info("Select dropdown element " + dataList.get(TEST_OBJECT));
			logger.info("Selecting drop down by visible text: " + dataList.get(TEST_DATA));
			page.locator(dataList.get(TEST_OBJECT)).selectOption(new SelectOption().setIndex(index));
			assertTrue(true);
		} catch (Exception e) {
			logger.info(e.getMessage());
			assertFalse(true);
		}
	}

	public void storeElementText(ArrayList<String> dataList) {
		try {
			logger.info("Storing text from element " + dataList.get(TEST_OBJECT));
			String elementText = page.locator(dataList.get(TEST_OBJECT)).innerText();
			if (!dataList.get(TEST_EXPECTED_DATA).isEmpty()) {
				String variableName = dataList.get(TEST_EXPECTED_DATA);
				hashmap.put(variableName, elementText);
				logger.info("Variable Name: " + variableName);
				logger.info("Value: " + elementText);
				assertTrue(true);
			} else {
				logger.info("No variable name is given in expected output column");
				assertFalse(true);
			}
			assertTrue(true);
		} catch (Exception e) {
			logger.info(e.getMessage());
			assertFalse(true);
		}
	}

	public void pause(ArrayList<String> dataList) {
		try {
			logger.info("Pausing script for: " + dataList.get(TEST_DATA));
			int time = Integer.valueOf(dataList.get(TEST_DATA));
			Thread.sleep(time * 1000);
			assertTrue(true);
		} catch (Exception e) {
			logger.info(e.getMessage());
			assertFalse(true);
		}
	}

	public void verifyEquality(ArrayList<String> dataList) {
		try {
			logger.info("Verifying two variable value");
			String[] inputText = dataList.get(TEST_DATA).split("|");
			String firstVariable = "";
			firstVariable = hashmap.containsKey(inputText[0]) ? hashmap.get(inputText[0]) : inputText[0];
			String secondVariable = "";
			secondVariable = hashmap.containsKey(inputText[1]) ? hashmap.get(inputText[1]) : inputText[1];
			if (firstVariable.equals(secondVariable)) {
				logger.info(
						"First variable: " + firstVariable + " is not equal to the second variable: " + secondVariable);
				assertTrue(true);
			} else {
				logger.info("First variable: " + firstVariable + " is equal to the second variable: " + secondVariable);
				assertFalse(true);
			}

		} catch (Exception e) {

		}

	}

	public void verifyElementText(ArrayList<String> dataList) {
		try {
			logger.info("Verifying text from element " + dataList.get(TEST_OBJECT));
			String elementText = page.locator(dataList.get(TEST_OBJECT)).innerText();
			String expectedText = dataList.get(TEST_EXPECTED_DATA);
			if (elementText.equals(expectedText)) {
				logger.info("Element text: " + elementText + " is equal to the given value: " + expectedText);
				assertTrue(true);
			} else {
				logger.info("Element text: " + elementText + " is not equal to the given value: " + expectedText);
				assertFalse(true);
			}
			assertTrue(true);
		} catch (Exception e) {
			logger.info(e.getMessage());
			assertFalse(true);
		}
	}

	public void verifyNetworkResponse(ArrayList<String> dataList) {
		try {
			String body = null;

			for (Response response : BrowserProperties.getInstance().getResponseNetwork()) {
				try {
					if (response.request().url().contains(dataList.get(TEST_DATA))) {
						body = new String(response.body(), StandardCharsets.UTF_8);
						logger.info("Response Body: " + body);
					}
				} catch (Exception e) {
					continue;
				}
				if (body != null) {
					break;
				}
			}
			if (body != null) {
				String[] pair = dataList.get(TEST_EXPECTED_DATA).split(",");
				String key = pair[0];
				String value = pair[1];
				JSONObject json = new JSONObject(body);

				if (json.has(key)) {
					boolean hasValue = json.get(key).toString().equals(value);
					if (hasValue) {
						logger.info("JSON object response has key|value pair matched with input");
						assertTrue(true);
					} else {
						logger.info("JSON object response has key but incorrect value matched with input");
						assertFalse(true);
					}
				} else {
					logger.info("No such key in JSON exist!");
					assertFalse(true);
				}
			} else {
				logger.info("No Request Call with non-empty body matches with given input pattern URL");
				assertFalse(true);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			assertFalse(true);
		}
	}

}
