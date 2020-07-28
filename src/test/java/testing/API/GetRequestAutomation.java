package testing.API;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import SwitchNetwork.switchnetwork;
import telegrambots.notifyBot;

public class GetRequestAutomation {

	public static WebDriver driver;
	public static Properties prop;
	public ExtentReports extent;
	public static ExtentHtmlReporter htmlReporter;
	public ExtentTest test;

	notifyBot bot = new notifyBot();
	switchnetwork wifi = new switchnetwork();

	@BeforeTest
	public void setExtent() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/ExtentReport.html");
		htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("Hostname", "localhost");
		extent.setSystemInfo("OS", "Mac OS");
		extent.setSystemInfo("Tester Name", "QC_Jimbi");
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

	@Test
	public void ApiTesting() throws IOException {
		String network[] = { "fpt", "vnpt", "viettel" };
		for (String x : network) {
			if(x.equals("fpt")) {
				System.out.println(x);
				wifi.switchToSpecificNetwork("INFINIY_503", "i28unit#503");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(x.equals("vnpt")) {
				System.out.println(x);
				wifi.switchToSpecificNetwork("INFINIY28-603", "i28unit#603");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				System.out.println(x);
			}
			
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
					test.log(Status.PASS, url + "----- Access OK");
					System.out.println("OK");
					bot.sendMsg(driver.getCurrentUrl() + "    ------ Access Failed");

				} else {
					test.log(Status.FAIL, url + "------ Access failed");
//					bot.sendMsg(driver.getCurrentUrl() + "    ------ Access Failed");
				}

			}
		}
		driver.quit();

	}

}
