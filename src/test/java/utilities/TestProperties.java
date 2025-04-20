package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestProperties {

	public static TestProperties getInstance() {
		if (instance == null) {
			instance = new TestProperties();
		}
		return instance;
	}

	private static TestProperties instance = null;
	private String BASEURL;
	private String SHEETNAME;
	private String WORKBOOKNAME;
	private String BROWSER;
	private String SUITETYPE;
	private boolean HEADLESS;
	private int WAIT_TIME;
	private boolean TRACK_NETWORK;
	private static final Logger logger = LogManager.getLogger(TestProperties.class.getName());

	public void initProperties() {
		try {
			// Initialized Properties File
			Properties prop = new Properties();
			String propertiesFilePath = System.getProperty("user.dir") + File.separator + "test.properties";
			FileInputStream testPropFile = new FileInputStream(new File(propertiesFilePath));
			prop.load(testPropFile);
			BASEURL = prop.getProperty("BASE_URL");
			WORKBOOKNAME = prop.getProperty("WORKBOOK_NAME");
			SHEETNAME = prop.getProperty("SHEET_NAME");
			BROWSER = prop.getProperty("BROWSER");
			HEADLESS = Boolean.parseBoolean(prop.getProperty("HEADLESS"));
			WAIT_TIME = Integer.parseInt(prop.getProperty("WAIT_TIME"));
			TRACK_NETWORK = Boolean.parseBoolean(prop.getProperty("TRACK_NETWORK"));
			SUITETYPE = prop.getProperty("SUITE_TYPE");
		} catch (Exception e) {
			logger.info("Error while reading properties file");
		}

	}

	public String getSUITETYPE() {
		return SUITETYPE;
	}

	public String getBASEURL() {
		return BASEURL;
	}

	public String getSHEETNAME() {
		return SHEETNAME;
	}

	public String getWORKBOOKNAME() {
		return WORKBOOKNAME;
	}

	public String getBROWSER() {
		return BROWSER;
	}

	public boolean isHEADLESS() {
		return HEADLESS;
	}

	public int getWAITITME() {
		return WAIT_TIME;
	}

	public boolean isNETWORKTRACKING() {
		return TRACK_NETWORK;
	}

}
