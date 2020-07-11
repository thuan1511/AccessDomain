package testing.API;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.utils.FileUtil;
import com.google.common.io.Files;

public class GetRequestAutomation {

	public static WebDriver driver;
	public static Properties prop;
	public ExtentReports extent;
	public static ExtentHtmlReporter htmlReporter;
	public ExtentTest test;
	
	
	@BeforeTest
	public void setExtent() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/test-output/ExtentReport.html");
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		
		extent.setSystemInfo("Hostname", "localhost");
		extent.setSystemInfo("OS", "Mac OS");
		extent.setSystemInfo("Tester Name", "jimbi");
		extent.setSystemInfo("Browser", "Chrome");
		
	}
	
	@AfterTest
	public void endReport() {
		extent.flush();
		
	}
	@BeforeMethod
	 public void setup() {
	  System.setProperty("webdriver.chrome.driver", "resource/chromedriver");
	  driver = new ChromeDriver();
	  driver.manage().window().maximize();
	 }

	@AfterMethod
	 public void tearDown(ITestResult result) throws IOException {
	  if (result.getStatus() == ITestResult.FAILURE) {
	   test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName()); // to add name in extent report
	   test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // to add error/exception in extent report
	   String screenshotPath = GetRequestAutomation.getScreenshot(driver, result.getName());
	   test.addScreenCaptureFromPath(screenshotPath);// adding screen shot
	  } else if (result.getStatus() == ITestResult.SKIP) {
	   test.log(Status.SKIP, "Test Case SKIPPED IS " + result.getName());
	  }
	  else if (result.getStatus() == ITestResult.SUCCESS) {
	   test.log(Status.PASS, "Test Case PASSED IS " + result.getName());
	  }
	  driver.quit();
	 }
	
	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
		  String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		  TakesScreenshot ts = (TakesScreenshot) driver;
		  File source = ts.getScreenshotAs(OutputType.FILE);
		  
		  // after execution, you could see a folder "FailedTestsScreenshots" under src folder
		  String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
		  File finalDestination = new File(destination);
//		  FileUtils.copyFile(source, finalDestination);
		  Files.copy(source, finalDestination);
		  return destination;
		 }
	

	@Test
	public void ApiTesting() throws IOException {

		prop = new Properties();
		FileInputStream fis = new FileInputStream("src/main/java//configs/URL.properties");
		prop.load(fis);

		for (int i = 1; i <= prop.size(); i++) {
			
			String url = prop.getProperty("url" + i);
			driver.get(url);

			test = extent.createTest(url);
			HttpClient client = HttpClientBuilder.create().build();
			HttpResponse response = client.execute(new HttpGet(url));
			int statusCode = response.getStatusLine().getStatusCode();
		    System.out.println(driver.getCurrentUrl() + "-----" + "status Code: " + statusCode);
			if (statusCode == 200) {
				test.log(Status.PASS, "Access OK");
				System.out.println("OK");
			} else {
				test.log(Status.FAIL, "Access failed");
				System.out.println("Failed");
			}

		}
		driver.quit();

	}
	

}

//}
