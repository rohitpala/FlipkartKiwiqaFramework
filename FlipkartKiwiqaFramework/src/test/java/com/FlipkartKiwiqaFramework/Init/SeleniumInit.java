package com.FlipkartKiwiqaFramework.Init;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.FlipkartKiwiqaFramework.Utility.TestData;
import com.FlipkartKiwiqaFramework.product.ProductIndexPage;
import com.FlipkartKiwiqaFramework.product.ProductVerification;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class SeleniumInit{
	public String suiteName = "";
	public String testName = "";
	public static String PayertestURL="";
	public static String testUrl;
	public static String seleniumHub; // Selenium hub IP
	public static String seleniumHubPort; // Selenium hub port
	protected String targetBrowser; // Target browser
	protected static String test_data_folder_path = null;
	public static String currentWindowHandle = "";// Get Current Window handle
	public static String browserName = "";
	public static String osName = "";
	public String HomeDir="";
	public static String browserVersion = "";
	public static String browseruse = "";
	public static String Url="";
	public static String AuthorName;
	public static String ModuleName;
	public static String Version="";
	public static String header="";
	public static int col=0;

	protected static String screenshot_folder_path = null;
	public static String currentTest; // current running test
	protected static Logger logger = Logger.getLogger("testing");
	protected WebDriver driver;
	
	/*public static com.aventstack.extentreports.ExtentReports extent;
	public static com.aventstack.extentreports.ExtentTest test;
	public static ExtentHtmlReporter htmlReporter;*/
	
	    
    //product
	protected ProductIndexPage productIndexPage;
    protected ProductVerification productVerification;
    
	/*@BeforeSuite
	public void fetchSuite() throws IOException
	{
		TestData.deletePastScreenshots(System.getProperty("user.dir") +"\\test-output\\screenshots");
		htmlReporter= new ExtentHtmlReporter(System.getProperty("user.dir") +"/test-output/IRFAutomation.html");
		htmlReporter.setAppendExisting(false);
		//htmlReporter.setAppendExisting(true);
		htmlReporter.loadXMLConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
		extent = new com.aventstack.extentreports.ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("URL", TestData.getValueFromConfig("config.properties","URL"));
		extent.setSystemInfo("User Name", TestData.getValueFromConfig("config.properties","UserName"));
		//extent.setSystemInfo("Password", TestData.getValueFromConfig("config.properties","Password"));
		extent.setSystemInfo("Browser", TestData.getValueFromConfig("config.properties","Browser"));
	}*/
	
	@BeforeTest(alwaysRun = true)
	public void fetchSuiteConfiguration(ITestContext testContext) throws IOException 
	{
		seleniumHub = testContext.getCurrentXmlTest().getParameter("selenium.host");
		seleniumHubPort = testContext.getCurrentXmlTest().getParameter("selenium.port");
		testUrl=TestData.getValueFromConfig("config.properties","URL");
		//System.out.println("Payer URL: "+PayertestURL);
	}
	/**
	 * WebDriver initialization
	 * @return WebDriver object
	 * @throws IOException
	 * @throws InterruptedException
	 */
	//@Parameters({ "Author", "Module" })
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method, ITestContext testContext,ITestResult testResult) throws IOException, InterruptedException 
	{
		/*AuthorName=Author;
		ModuleName=Module;*/
		targetBrowser =TestData.getValueFromConfig("config.properties","Browser");
		browserName=targetBrowser;
		currentTest = method.getName(); // get Name of current test.
		if(currentTest.contains("visitSearch_SmartMapPageShouldBeWorkingFine"))
		{
			targetBrowser="ie11";
		}
		URL remote_grid = new URL("http://" + seleniumHub + ":" + seleniumHubPort + "/wd/hub");
		String SCREENSHOT_FOLDER_NAME = "screenshots";
		String TESTDATA_FOLDER_NAME = "test_data";
		test_data_folder_path = new File(TESTDATA_FOLDER_NAME).getAbsolutePath();
		screenshot_folder_path = new File(SCREENSHOT_FOLDER_NAME).getAbsolutePath();
		try{
		DesiredCapabilities capability = null;		
		if (targetBrowser == null || targetBrowser.contains("firefox") || targetBrowser.equalsIgnoreCase("firefox")) 
		{
			File driverpath = new File("Resource/geckodriver.exe");
			String path1 = driverpath.getAbsolutePath();
			System.setProperty("webdriver.gecko.drive",path1);
			
			FirefoxProfile profile = new FirefoxProfile();
			//path = "C:\\Users\\KQSPL_R\\Downloads";
			profile.setPreference("dom.max_chrome_script_run_time", "999");
			profile.setPreference("dom.max_script_run_time", "999");
			profile.setPreference("browser.download.folderList", 2);
			//profile.setPreference("browser.download.dir", path);
			profile.setPreference("browser.helperApps.neverAsk.openFile",
					"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.download,manager.focusWhenStarting", false);
			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			profile.setPreference("browser.download.manager.closeWhenDone", false);
			profile.setPreference("browser.download.manager.showAlertOnComplete", false);
			profile.setPreference("browser.download.manager.useWindow", false);
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
			profile.setPreference("pdfjs.disabled", true);
			profile.setAcceptUntrustedCertificates(true);
			profile.setPreference("security.OCSP.enabled", 0);
			//profile.setEnableNativeEvents(false);
			profile.setPreference("network.http.use-cache", false);
			capability = DesiredCapabilities.firefox();
			capability.setJavascriptEnabled(true);
			capability.setCapability(FirefoxDriver.PROFILE, profile);
			osName = System.getProperty("os.name");
			HomeDir=System.getProperty("user.home");
			driver= new FirefoxDriver(capability);
			driver = new RemoteWebDriver(remote_grid, capability);
		}else if (targetBrowser.contains("ie8") || targetBrowser.equalsIgnoreCase("IE"))
		{
			capability = DesiredCapabilities.internetExplorer();
			capability.setPlatform(Platform.ANY);
			capability.setBrowserName("internet explorer");
			capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capability.setJavascriptEnabled(true);
			osName = capability.getPlatform().name();
			driver = new RemoteWebDriver(remote_grid, capability);
		}else if (targetBrowser.contains("ie9"))
		{
			capability = DesiredCapabilities.internetExplorer();
			capability.setBrowserName("internet explorer");
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capability.setJavascriptEnabled(true);
			osName = capability.getPlatform().name();
		}else if (targetBrowser.contains("ie11"))
		{
			capability = DesiredCapabilities.internetExplorer();
			File driverpath = new File("Resource/IEDriverServer.exe");
			String path1 = driverpath.getAbsolutePath();
			System.setProperty("webdriver.ie.driver",path1 );
			capability.setBrowserName("internet explorer");
			capability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capability.setCapability("nativeEvents", false);
			capability.setJavascriptEnabled(true);
			osName = capability.getPlatform().name();
			driver= new InternetExplorerDriver(capability);
			driver = new RemoteWebDriver(remote_grid, capability);
		}else if (targetBrowser.contains("chrome") || targetBrowser.equalsIgnoreCase("chrome"))
		{
			capability = DesiredCapabilities.chrome();
			File driverpath = new File("Resource/chromedriver.exe");
			String path1 = driverpath.getAbsolutePath();
			System.setProperty("webdriver.chrome.driver",path1);
			final ChromeOptions chromeOptions = new ChromeOptions();
			//chromeOptions.setBinary("/usr/bin/chromium-browser");
			//chromeOptions.addArguments("--headless");
			capability.setBrowserName("chrome");
			capability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			capability.setJavascriptEnabled(true);
			capability.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
			capability.setCapability("disable-popup-blocking", true);
			osName = capability.getPlatform().name();
//			capability = DesiredCapabilities.chrome();
			/*System.setProperty("webdriver.chrome.driver",
					"E:\\chromedriver.exe");*/
//			capability.setBrowserName("chrome");
//			capability.setJavascriptEnabled(true);
//			osName = capability.getPlatform().name();
			browserVersion = capability.getVersion();
//			driver= new ChromeDriver(capability);
			driver = new RemoteWebDriver(remote_grid, capability);
			
			
			
//			capability = DesiredCapabilities.chrome();
//			File driverpath = new File("Resource/chromedriver.exe");
//			String path1 = driverpath.getAbsolutePath();
//			System.setProperty("webdriver.chrome.driver",path1);
//			capability.setBrowserName("chrome");
////			capability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
////			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//			capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
//			capability.setCapability("nativeEvents", false);
//			capability.setJavascriptEnabled(true);
//			osName = capability.getPlatform().name();
//			driver= new ChromeDriver(capability);
//			driver = new RemoteWebDriver(remote_grid, capability);
			
		}else if (targetBrowser.contains("safari"))
		{
			capability = DesiredCapabilities.safari();
			capability.setJavascriptEnabled(true);
			capability.setBrowserName("safari");
			driver = new SafariDriver(capability);
		}
		
		suiteName = testContext.getSuite().getName();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
//		Dimension d = new Dimension(1382,744); 
//		driver.manage().window().setSize(d); 
		driver.get(testUrl);
		/*if(targetBrowser.contains("ie11")){
			driver.navigate().to(testUrl);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
		else{
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(testUrl);
		}*/
		
		suiteName = testContext.getSuite().getName();
	
		
		// Product
		productIndexPage = new ProductIndexPage(driver);
		productVerification =  new ProductVerification(driver);
		
		}catch(Exception e){
			e.printStackTrace();
			//test.log(Status.FAIL, testResult.getThrowable());
		}
	}
	/**
	 * After Method
	 * 
	 * @param testResult
	 */
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult testResult,ITestContext testContext)
	{
		String screenshotName="";
		testName = testResult.getName();
		try
		{
			String Result="";
			if (testResult.isSuccess())
			{
				Result="Pass";
			}
			else if(testResult.getStatus()==ITestResult.FAILURE)
			{ 
				Result="Fail";
	        }
			else
			{
//				Result="Skeep";
				Result="Skip";
			}
			//TestData.updatedBuildAnalysis("BuildAnalysis.xlsx", "BuildAnalysis",header ,testName, Result,col);
		}catch(Exception e)
		{
			System.out.println("Build Analysis Failure");
		}
		try 
		{
			//Version= driver.getCurrentUrl();
			//testName = testResult.getName();
			screenshotName = Common.getCurrentTimeStampString(); //+ testName;
			if (!testResult.isSuccess()) 
			{
				/* Print test result to Jenkins Console */
				System.out.println();
				System.out.println("TEST FAILED - " + testName);
				System.out.println();
				System.out.println("ERROR MESSAGE: " + testResult.getThrowable());
				System.out.println("\n");
				Reporter.setCurrentTestResult(testResult);
				/* Make a screenshot for test that failed */
				if(testResult.getStatus()==ITestResult.FAILURE)
				{ 
					System.out.println("1 message from tear down");
					  log("Please look to the screenshot :- "+ Common.makeScreenshot(driver, screenshotName));
					//  test.log(Status.FAIL, testResult.getThrowable());
		        }
				if(testResult.getStatus()==ITestResult.SKIP)
				{
					//  test.log(Status.SKIP, testResult.getThrowable());
					  /*driver.manage().deleteAllCookies();
						 driver.close();
						 driver.quit();*/
		        }
				
				
				try {
		             PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("target/teststream.txt", true)));
		             out.println("{\"metodo\":\""+testName.toString()+"\", \"status\":\""+testResult.getStatus()+"\", "
		        + "\"classe\":\""+testContext.getClass().getName()+"\", \"descricao\":\""+testContext.getCurrentXmlTest().getName().toString()+"\"}");
		             out.close();
		         } catch (IOException e) {
		          e.printStackTrace();
		         }
				
				
				/*driver.manage().deleteAllCookies();
				driver.close();
				driver.quit();*/
			}
			else 
			{
				/*driver.manage().deleteAllCookies();
				driver.close();
				driver.quit();*/
			}
		}catch (Throwable throwable)
		{
			try
			{
				if(testResult.getStatus()==ITestResult.SKIP)
				{
					//test.log(Status.SKIP, testResult.getThrowable());
				}else
				{
					//test.log(Status.FAIL, testResult.getThrowable());
				}
				/*driver.manage().deleteAllCookies();
				driver.close();
				driver.quit();*/
			}catch(Exception e)
			{
				System.out.println(e);
			}
		}
		driver.manage().deleteAllCookies();
		driver.close();
		driver.quit();
	}
	
	
	/**
	 * Log given message to Reporter output.
	 * 
	 * @param msg
	 *            Message/Log to be reported.
	 */
	/*@AfterSuite(alwaysRun = true)
	public void postConfigue()
	{
		//extent.setSystemInfo("Version",Version.split("/")[3]);
		extent.setSystemInfo("Version","v1");
		extent.flush();
		//TestData.createZipfileForOutPut("Exec_Report_"+Common.getCurrentTimeStampString());
	}*/
	public void log(String msg) 
	{
		Reporter.log(msg + "</br>");
		//test.log(Status.INFO,"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+" "+msg);
		System.out.println(msg);
	}
	public void logStep(int msg1, String msg2) 
	{
		Reporter.log("Step-"+msg1+" : "+msg2 + "</br>");
		//test.log(Status.INFO,"Step-"+msg1+" : "+msg2);
		System.out.println("Step-"+msg1+" : "+msg2);// for jenkins  
	}
	public void logCase(String msg)
	{
		//test=extent.startTest(msg);
		Reporter.log("Test Case : "+msg+"</br>");
		System.out.println("Test Case : "+msg);
		//test=extent.createTest(msg);
	}
	public static void slog(String msg)
	{
		//test=extent.startTest(msg);
		//Reporter.log("Test Case : "+msg+"</br>");
		Reporter.log(msg + "</br>");
		//test.log(Status.INFO,"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+" "+msg);
		System.out.println(msg);
	}
	/*public void assignAuthor_Module(String AuthorNm,String ModuleNm )
	{
		test.assignAuthor(AuthorNm);
		test.assignCategory(ModuleNm);
	}*/
	
	/*public void logStatus(final int test_status,String msg) 
	{
		switch (test_status) 
		{
			case ITestStatus.PASSED:
				test.log(Status.PASS,"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp "+msg+" ");
				break;
			case ITestStatus.FAILED:
				String screenshotName = Common.getCurrentTimeStampString();
				test.log(Status.FAIL,"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+msg+" Please look to the screenshot :- "+ Common.makeScreenshot(driver, screenshotName));
				MakeScreenshots();
				break;
			case ITestStatus.SKIPPED:
				test.log(Status.SKIP," "+msg);
				break;
			default:
				break;
		}
	}*/
	
	public static void logStatus(final int test_status, String msg) {

		switch (test_status) {

		case ITestStatus.PASSED:
			Reporter.log(msg+"<font color=238E23>--<img src=\"passed.png\"/></font>"+"</br>");
			break;

		case ITestStatus.FAILED:
			Reporter.log(msg+"<font color=#FF0000>--<img src=\"failed.png\"/></font>"+"</br>");
			break;

		case ITestStatus.SKIPPED:
			Reporter.log(msg+"<font color=#FFFF00>--Skipped</font>"+"</br>");
			break;

		default:
			break;
		}
	}

	public void MakeScreenshots()
	{
		String screenshotName = Common.getCurrentTimeStampString() + testName;
		Common.makeScreenshot2(driver, screenshotName);
	}
}