package browerSetup;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Request;
import com.microsoft.playwright.Response;

import utilities.TestProperties;

public class BrowserProperties {

	public static BrowserProperties getInstance() {
		if (instance == null) {
			instance = new BrowserProperties();
		}
		return instance;
	}

	private static BrowserProperties instance = null;
	Playwright playwright = Playwright.create();
	Browser browser;
	private Playwright playwrightInstance;
	private LaunchOptions launchOptions;
	private Page page;
	private BrowserContext browserContext;
	private Browser.NewContextOptions newContextOption;
	private static final Logger logger = LogManager.getLogger(BrowserProperties.class.getName());
	TestProperties testProp = new TestProperties();
	private Set<Request> requestNetwork = new LinkedHashSet<Request>();
	private Set<Response> responseNetwork = new LinkedHashSet<Response>();

	public Playwright getPlaywright() {
		return playwright;
	}

	public Set<Request> getRequestNetwork() {
		return requestNetwork;
	}

	public Set<Response> getResponseNetwork() {
		return responseNetwork;
	}

	public void setPlaywright(Playwright playwright) {
		this.playwright = playwright;
	}

	public Playwright getPlaywrightInstance() {
		return playwrightInstance;
	}

	public void setPlaywrightInstance(Playwright playwrightInstance) {
		this.playwrightInstance = playwrightInstance;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public BrowserContext getBrowserContext() {
		return browserContext;
	}

	public void setBrowserContext(BrowserContext browserContext) {
		this.browserContext = browserContext;
	}

	public Page initBrowser() {

		try {

			// Getting properties file data
			TestProperties testProp = new TestProperties();
			testProp.initProperties();

			// Starting playwright instance
			playwrightInstance = Playwright.create();
			logger.info("Creating Playwright Instance....");

			// Setting up launch options
			launchOptions = new LaunchOptions();
			ArrayList<String> arguments = new ArrayList<>();

			// Setting up browser as per properties file
			if (testProp.getBROWSER().toLowerCase().equals("chrome")) {
				logger.info("Initiating playwright with chrome browser");
				launchOptions.setChannel("chrome");
			}

			if (testProp.getBROWSER().toLowerCase().equals("edge")) {
				logger.info("Initiating playwright with edge browser");
				launchOptions.setChannel("msedge");
			}

			// Setting up browserContext Options
			newContextOption = new Browser.NewContextOptions();
			if (testProp.isHEADLESS())
				launchOptions.setHeadless(true);
			else
				launchOptions.setHeadless(false);

			newContextOption.setViewportSize(null);
			arguments.add("--start-maximized");
			newContextOption.setAcceptDownloads(true);
			newContextOption.setIgnoreHTTPSErrors(true);

			// Initiating Page instance with all above parameters
			Browser browser = playwrightInstance.chromium().launch(launchOptions.setArgs(arguments));
			browserContext = browser.newContext(newContextOption);
			page = browserContext.newPage();

			if (testProp.isNETWORKTRACKING()) {
				page.onRequest(request -> requestNetwork.add(request));
				page.onResponse(response -> responseNetwork.add(response));
			}

		} catch (Exception e) {
			logger.info("Getting following error: ");
			e.printStackTrace();
		}
		return page;
	}

	public void closePlaywright() {
		try {
			page.close();
			browserContext.close();
			playwright.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
