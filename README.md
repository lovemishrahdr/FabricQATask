# FabricQATask 

Developed a high-level test automation framework utilizing a keyword-driven or hybrid approach.

**Tech Stack:**

- **Playwright** – For browser automation and end-to-end testing  
- **Java** – Core programming language used for test development  
- **TestNG** – Testing framework for organizing and executing test cases  
- **Log4j2** – Logging framework for capturing logs during test execution  
- **Apache POI** – For reading and writing Excel files (test data handling)  
- **Maven** – Build and dependency management tool

## 📁 Project Structure

**src/test/java** – This is the main source folder containing the following components:

1. **browserSetup/BrowserProperties.java** – Contains logic to initialize the browser based on the configuration provided in the `test.properties` file.  
2. **playwrightRunner/BaseRunner.java** – The base TestNG suite runner that sets up and executes test suites.  
3. **playwrightCommand/PlaywrightCommandExecution.java** – Implements Playwright actions encapsulated as keywords for execution.  
4. **utilities/ExcelUtil.java** – Handles loading and reading Excel files from the **TestData** folder.  
5. **utilities/TestProperties.java** – Responsible for loading the `test.properties` file from the root directory and providing access to its values.

## 📁 Test Data

The **TestData** folder contains an Excel file named **TestSheet.xlsx**, which includes a list of test cases organized under a sheet with the same name. The sheet follows the structure below:

1. **TestCase ID** – A sequential identifier for each test case.  
2. **Test Scenario** – The name of the overall scenario.  
3. **Test Case Description** – A description of the individual test case under the specified scenario.  
4. **Keyword/Action** – The keyword or Playwright command to be executed.  
5. **Object Name** – Refers to the locator used in the test step.  
6. **Test Data** – Input data required for executing the keyword/action.  
7. **Expected Output** – The expected result to validate against the action or any additional assertion data.  
8. **Suite** – Indicates the suite to which the test case belongs. *(Currently, this field has no functional impact, but it can be leveraged in the future to run suite-specific test cases.)*


## 📁 Test Properties
The root folder contains the test.properties file, which is currently configured to accept the browser name as input. While it is primarily used for this purpose, it can be further customized to run the automation based entirely on the values provided within the test.properties file.


## Execution
Here’s a rephrased version of your instructions:

To run the tests, follow these steps:

1. **Using Maven:**
   - Run `mvn clean` to clean the project.
   - Then, run `mvn test` to execute the tests.

2. **Using TestNG XML:**
   - Execute the `testng.xml` file directly in your IDE to run the tests.

3. **Running a Specific Test Class:**
   - To initiate a test case, run the `BaseRunner.java` class.
