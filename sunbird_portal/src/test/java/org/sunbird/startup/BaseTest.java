package org.sunbird.startup;

import java.io.IOException;
import java.sql.DriverManager;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;


public abstract class BaseTest implements IAutoConst {

	public static WebDriver driver;
	static 
	{
		System.setProperty(CHROME_KEY,CHROME_VAL);

	}

	@BeforeMethod
	public void openApplication() throws IOException, InterruptedException 
	{
		System.out.println("Test Execution Started : Opening the browser");
		driver = new ChromeDriver(); 
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(APP_URL);
		System.out.println("Scripts are executing on "+driver.getCurrentUrl());
		
	}

	@AfterMethod
	public void closeApplication() throws InterruptedException
	{
		driver.manage().deleteAllCookies();
		driver.quit();
		System.out.println("Test Execution Completed : Closing the browser");
		
	}

	public WebDriver getDriver() {
		// TODO Auto-generated method stub
		return driver;
	}

}

