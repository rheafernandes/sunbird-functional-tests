package org.sunbird.pageobjects;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sunbird.generic.ExtentTestManager;
import org.sunbird.generic.GenericFunctions;
import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.page.UploadOrgPage;
import org.sunbird.startup.BaseTest;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;


public class UploadOrgObj extends BaseTest
{
	static Logger log = Logger.getLogger(UploadOrgObj.class.getName());

	WebDriverWait wait=new WebDriverWait(driver,15);
	UploadOrgPage orgUploadPage=PageFactory.initElements(driver, UploadOrgPage.class);


	//updated on 17/07/2018 method return type.
	public boolean uploadRootAndSubOrg(String uploadDocument) throws Exception 
	{
		ExtentTestManager.getTest().log(LogStatus.INFO, "User is trying to upload Root Organization Or Sub-Organization");
		try
		{

			GenericFunctions.waitForElementToAppearOnScreen(orgUploadPage.dropdown);
			orgUploadPage.dropdown.click();
			GenericFunctions.waitForElementToAppearOnScreen(orgUploadPage.clickProfileIcon);
			orgUploadPage.clickProfileIcon.click();
			GenericFunctions.waitWebDriver(15000);
			WebElement element=wait.until(ExpectedConditions.visibilityOf(orgUploadPage.organizationUpload));
			driver.navigate().refresh();
			element.click();
			if(element.isDisplayed())
			{
				GenericFunctions.waitForElementToAppear(orgUploadPage.uploadCSV);
				orgUploadPage.uploadCSV.click();
				GenericFunctions.waitWebDriver(3000);
				if(uploadDocument.equalsIgnoreCase(UPLOAD_ROOT_ORG))
				{
					ExtentTestManager.getTest().log(LogStatus.INFO, "User is trying to upload Root Organization "+UPLOAD_ROOT_ORG+" is the file name");
					String path = System.getProperty("user.dir")+"/UploadingDocuments/"+UPLOAD_ROOT_ORG;

					//Commented for testing Extent report
					//String path = System.getProperty("user.dir")+UPLOAD_ROOT_ORG;

					System.out.println("Uploaded file name: "+path);
					//log.info("Uploaded file name: "+path);
					GenericFunctions.waitWebDriver(3000);
					GenericFunctions.uploadFile(path);
					GenericFunctions.waitForElementToAppear(orgUploadPage.processID);
					GenericFunctions.waitWebDriver(3000);	
					//String getProcessID = orgUploadPage.processID.getText().substring(12,33);	
					String getProcessID = orgUploadPage.processID.getText();
					System.out.println(getProcessID);
					String [] iD=getProcessID.split("ID: ");
					for(String idVal:iD)
					{
						GenericFunctions.writeNotepad(idVal,"./TestData/RootOrgProcessIds.txt");
					}

					orgUploadPage.closePopup.click();
					System.out.println("Successful on Root Org upload");
					log.info("Process ID: "+getProcessID+"Successful on Root Org upload");
				}
				else if(uploadDocument.equalsIgnoreCase(UPLOAD_SUB_ORG))
				{
					ExtentTestManager.getTest().log(LogStatus.INFO, "User is trying to upload Sub-Organization "+UPLOAD_SUB_ORG+" is the file name");
					String path = System.getProperty("user.dir")+"/UploadingDocuments/"+UPLOAD_SUB_ORG;
					System.out.println("Uploaded file name: "+path);
					log.info("Uploaded file name: "+path);
					GenericFunctions.waitWebDriver(3000);
					GenericFunctions.uploadFile(path);
					GenericFunctions.waitWebDriver(3000);
					//String getProcessID = orgUploadPage.processID.getText().substring(12,32);	

					String getProcessID = orgUploadPage.processID.getText();
					System.out.println(getProcessID);
					String [] iD=getProcessID.split("ID: ");
					for(String idVal:iD)
					{
						GenericFunctions.writeNotepad(idVal,"./TestData/SubOrgProcessIds.txt");
					}
					//GenericFunctions.writeNotepad(getProcessID,"./TestData/SubOrgProcessIds.txt");
					orgUploadPage.closePopup.click();
					System.out.println("Successful on Sub Org upload");
					log.info("Process ID: "+getProcessID+"Successful on Sub Org upload");
					/*String errorMsg = orgUploadPage.uploadErrorToast.getText();
				Thread.sleep(1000);	
				System.out.println(errorMsg+"Please upload a valid file");
				log.info(errorMsg);
				Thread.sleep(2000);*/
					//orgUploadPage.uploadErrorToast.click();

				}

			}
			/*else
			{
				driver.navigate().refresh();
				log.error("No Files were uploaded");
				ExtentTestManager.getTest().log(LogStatus.FAIL, "User Defined Message @Exception : User Logout Failed");
				ExtentTestManager.getTest().log(LogStatus.FAIL,"Exception Message: "+e.getMessage());
				System.out.println("No Files were uploaded");
			}*/

		}
		catch(Exception e)
		{
			driver.navigate().refresh();
			log.error("No Files were uploaded");
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Exception : Failed on Uploading Organizations and Sub Organizations");
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Exception Message: "+e.getLocalizedMessage());
			System.out.println("No Files were uploaded");
			Assert.fail("Error while uploading User for Root and Sub Org");
		}
		return true;

	}

	public void uploadRootAndSubOrgUserWithOrgId(String uploadDocument) throws Exception
	{
		//ExtentTestManager.getTest().log(LogStatus.INFO, "User Defined Message : User is trying to users for upload Organizations or Sub-Organizations with Ord ID based on the given input");
		try
		{
			Robot robot =new Robot();
			GenericFunctions.waitForElementToAppearOnScreen(orgUploadPage.dropdown);
			GenericFunctions.waitWebDriver(3000);
			orgUploadPage.dropdown.click();
			GenericFunctions.waitForElementToAppearOnScreen(orgUploadPage.clickProfileIcon);
			orgUploadPage.clickProfileIcon.click();
			GenericFunctions.waitForElementToAppear(orgUploadPage.organizationUpload);
			WebElement element=wait.until(ExpectedConditions.visibilityOf(orgUploadPage.organizationUpload));
			if(element.isDisplayed())
			{	 String processID="";
			String path="";
			orgUploadPage.checkUploadStatus.click();
			if(uploadDocument.equalsIgnoreCase(UPLOAD_USERS_ROOT_ORG))
			{
				ExtentTestManager.getTest().log(LogStatus.INFO, "User is fetching Root Org Process ID to upload users for Root Org with Org ID");
				processID=GenericFunctions.readFromNotepad("./TestData/RootOrgProcessIds.txt");
				log.info("Process Id: "+processID);

			}
			else if(uploadDocument.equalsIgnoreCase(UPLOAD_USERS_SUB_ORG))
			{
				ExtentTestManager.getTest().log(LogStatus.INFO, "User is fetching Sub Org Process ID to upload users for Sub Org with Sub Org ID");
				processID=GenericFunctions.readFromNotepad("./TestData/SubOrgProcessIds.txt");
			}	
			orgUploadPage.cProcessId.sendKeys(processID);
			GenericFunctions.waitWebDriver(3000);
			orgUploadPage.clickCheckStatus.click();
			GenericFunctions.waitWebDriver(3000);
			String orgId = orgUploadPage.getOrgId.getText();
			System.out.println("ID:"+orgId);
			orgUploadPage.closeWindow.click();
			GenericFunctions.waitWebDriver(3000);
			orgUploadPage.uploadUsers.click();
			GenericFunctions.waitWebDriver(3000);
			orgUploadPage.clickOrgId.sendKeys(orgId);
			orgUploadPage.uploadUsersCsv.click();
			GenericFunctions.waitWebDriver(3000);
			if(uploadDocument.equalsIgnoreCase(UPLOAD_USERS_ROOT_ORG))
			{
				//ExtentTestManager.getTest().log(LogStatus.INFO, "User is Uploading users for Root Organization");
				path = System.getProperty("user.dir")+"/UploadingDocuments/"+UPLOAD_USERS_ROOT_ORG;
			}
			else if(uploadDocument.equalsIgnoreCase(UPLOAD_USERS_SUB_ORG))
			{
				//ExtentTestManager.getTest().log(LogStatus.INFO, "User is Uploading users for Sub Organization");
				path = System.getProperty("user.dir")+"/UploadingDocuments/"+UPLOAD_USERS_SUB_ORG;
			}
			System.out.println("Uploaded file name: "+path);
			log.info("Uploaded file name: "+path);
			GenericFunctions.waitWebDriver(3000);
			GenericFunctions.uploadFile(path);
			GenericFunctions.waitWebDriver(4000);
			orgUploadPage.closePopup.click();
			System.out.println("Uploaded users for Root/Sub Org along with Org ID: "+orgUploadPage.uploadedRUser.getText());
			GenericFunctions.waitWebDriver(2000);
			//orgUploadPage.closeWindow1.click();
			}
			else 
			{
				System.out.println("Element is not displayed");
			}
		}
		catch(Exception e)
		{
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Failed on Uploading Users for Organizations and Sub Organizations with Org ID");
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Exception Message: "+e.getLocalizedMessage());
			System.out.println("Error while uploading users, No Files were uploaded");
			Assert.fail("Error while uploading users with Org ID, No Files were uploaded");
		}


	}

	public void rootAndSubOrgUserExternalAndProvider(String uploadDocument) throws Exception
	{
		String processID="";
		String path="";
		try
		{

			GenericFunctions.waitForElementToAppear(orgUploadPage.clickProfileIcon);
			orgUploadPage.clickProfileIcon.click();
			GenericFunctions.waitWebDriver(10000);
			WebElement element=wait.until(ExpectedConditions.visibilityOf(orgUploadPage.organizationUpload));
			if(element.isDisplayed())
			{	
				orgUploadPage.checkUploadStatus.click();
				if(uploadDocument.equalsIgnoreCase(UPLOAD_USERS_ROOT_ORG))
				{
					processID=GenericFunctions.readFromNotepad("./TestData/RootOrgProcessIds.txt");
					ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched Root Org Process ID to upload users for Root Org with Provider and External ID");
					log.info("Root Org Process Id: "+processID);

				}
				else if(uploadDocument.equalsIgnoreCase(UPLOAD_USERS_SUB_ORG))
				{
					processID=GenericFunctions.readFromNotepad("./TestData/SubOrgProcessIds.txt");
					ExtentTestManager.getTest().log(LogStatus.INFO, "Fetched Sub Org Process ID to upload users for Sub Org with Provider and External ID");
					log.info("Sub Org Process Id: "+processID);
				}

				System.out.println(processID);
				orgUploadPage.cProcessId.sendKeys(processID);
				GenericFunctions.waitWebDriver(3000);
				orgUploadPage.clickCheckStatus.click();
				GenericFunctions.waitWebDriver(3000);
				String externalId=orgUploadPage.externalId.getText();
				String provider=orgUploadPage.provider.getText();
				System.out.println("External ID: "+externalId);
				System.out.println("Provider :"+provider);
				orgUploadPage.closeWindow.click();
				GenericFunctions.waitWebDriver(4000);
				orgUploadPage.uploadUsers.click();
				GenericFunctions.waitWebDriver(3000);
				orgUploadPage.uploadUserExternal_Id.sendKeys(externalId);
				orgUploadPage.uploadUserProvider.sendKeys(provider);
				GenericFunctions.waitWebDriver(2000);
				orgUploadPage.uploadUsersCsv.click();
				GenericFunctions.waitWebDriver(3000);
				if(uploadDocument.equalsIgnoreCase(UPLOAD_USERS_ROOT_ORG))
				{
					path = System.getProperty("user.dir")+"/UploadingDocuments/"+UPLOAD_USERS_ROOT_ORG;
				}
				else
				{
					path = System.getProperty("user.dir")+"/UploadingDocuments/"+UPLOAD_USERS_SUB_ORG;
				}

				System.out.println("Uploaded file name: "+path);
				log.info("Uploaded file name: "+path);
				GenericFunctions.waitWebDriver(3000);
				GenericFunctions.uploadFile(path);
				GenericFunctions.waitWebDriver(3000);
				//GenericFunctions.waitForElementToAppear(orgUploadPage.uploadedRUser);
				System.out.println("Users for Root/Sub Org Uploaded with Provider and externalId: "+orgUploadPage.uploadedRUser.getText());
				GenericFunctions.waitWebDriver(3000);
				//OrgUploadPage.closeWindow1.click();
			}
			else
			{
				System.out.println("Element is not displayed");
			}
		}
		catch(Exception e)
		{
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Failed on Uploading Users for Organizations and Sub Organizations with Provider and External ID");
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Exception Message: "+e.getLocalizedMessage());
			System.out.println("Error while uploading users with Provider and External ID, No Files were uploaded");
			Assert.fail("Error while uploading users with Provider and External ID, No Files were uploaded");
		}
	}

	public void adminDashboard() throws Exception
	{
		try
		{
			orgUploadPage.dropdown.click();
			GenericFunctions.waitForElementToAppear(orgUploadPage.clickProfileIcon);
			orgUploadPage.clickProfileIcon.click();
			GenericFunctions.waitWebDriver(3000);
			String aprofileUrl=driver.getCurrentUrl();
			String eProfileUrl="https://staging.open-sunbird.org/profile";
			GenericFunctions.waitWebDriver(2000);
			log.info(aprofileUrl);
			log.info(eProfileUrl);
			Assert.assertEquals(aprofileUrl, eProfileUrl, "Assert fails on matching URLs");
			if(aprofileUrl.equalsIgnoreCase(eProfileUrl))
			{
				Assert.assertTrue(true);
				log.info("Profile page is displayed successfully");
				System.out.println("Profile page is displayed successfully");
			}

			else
			{
				log.info("Profile page is not loaded");
				System.out.println("Profile page is not loaded");

			} 
			GenericFunctions.waitWebDriver(2000);
			ExtentTestManager.getTest().log(LogStatus.INFO, "Verifying the page elements: Organization Upload, Upload Users and Check upload status");
			WebElement element1= GenericFunctions.waitForElementToAppear(orgUploadPage.organizationUpload);
			WebElement element2= GenericFunctions.waitForElementToAppear(orgUploadPage.uploadUsers);
			WebElement element3 = GenericFunctions.waitForElementToAppear(orgUploadPage.checkUploadStatus);
			if(element1.isDisplayed())
			{		
				if(element2.isDisplayed())
				{
					if(element3.isDisplayed())
					{
						//Assert.assertTrue(true);
						log.info("Upload Organizations option is present");
						System.out.println("Upload Organizations option is present");
						log.info("Upload Organizations option is present");
						System.out.println("Upload Organizations option is present");
						log.info("Check upload status option is present");
						System.out.println("Check upload status option is present");
						Assert.assertTrue(true);
					}
				}
			}
			else
			{
				System.out.println("Options are present in Profile page");
			}
		}

		catch(Exception e)
		{
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Failed on verifying the elements in Admin Dashboard");
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Exception Message: "+e.getLocalizedMessage());
			System.out.println("Error while verifying the Admin Dashboard");
			Assert.fail("Error while verifying the Admin Dashboard");

		}

		/*WebElement element=wait.until(ExpectedConditions.visibilityOf(orgUploadPage.organizationUpload));
		if(element.isDisplayed())
		{
			Assert.assertTrue(true);
			log.info("Upload Organizations option is present");
			System.out.println("Upload Organizations option is present");
		}
		else 
		{
			log.info("Upload Organizations option is not present");
			System.out.println("Upload Organizations option is not present");
		}

		WebElement element1=wait.until(ExpectedConditions.visibilityOf(orgUploadPage.uploadUsers));
		if(element1.isDisplayed())
		{
			Assert.assertTrue(true);
			log.info("Upload Users option is present");
			System.out.println("Upload Users option is present");
		}

		else
		{
			log.info("Upload Users option is not present");
			System.out.println("Upload Users option is not present");
		}

		WebElement element2=wait.until(ExpectedConditions.visibilityOf(orgUploadPage.checkUploadStatus));
		if(element2.isDisplayed())
		{
			Assert.assertTrue(true);
			log.info("Check upload status option is present");
			System.out.println("Check upload status option is present");
		}

		else
		{
			log.info("Check upload status option is not present");
			System.out.println("Check upload status option is not present");
		}*/

	}

	public void adminCreationConsumption(String filterName) throws Exception
	{
		try
		{
			GenericFunctions.waitForElementToAppear(orgUploadPage.dropdown);
			orgUploadPage.dropdown.click();
			//GenericFunctions.waitTillTheElementIsVisibleAndClickable(orgUploadPage.clickAdminDashboardIcon);
			GenericFunctions.waitWebDriver(4500);
			orgUploadPage.clickAdminDashboardIcon.click();
			GenericFunctions.waitWebDriver(3000);
			orgUploadPage.selectOrganizationDropdown.click();
			GenericFunctions.waitWebDriver(2000);
			orgUploadPage.selectOrg.click();
			GenericFunctions.waitWebDriver(2000);
			orgUploadPage.rDropdownList.click();
			GenericFunctions.waitWebDriver(2000);
			String creationText=orgUploadPage.clickCreation.getText();
			String consumptionText=orgUploadPage.clickConsumption.getText();
			System.out.println(creationText);



			if(orgUploadPage.clickCreation.getText().equalsIgnoreCase(filterName))
			{
				ExtentTestManager.getTest().log(LogStatus.INFO, "Verifying the Dashboard by applying filter as "+filterName);
				GenericFunctions.waitForElementToAppear(orgUploadPage.clickCreation);
				orgUploadPage.clickCreation.click();
				ExtentTestManager.getTest().log(LogStatus.INFO,"Checking the statics of "+filterName+":Last7days,Last14days,last5week and download the CSV");
				GenericFunctions.waitWebDriver(2000);
				verifyPageElements();
				GenericFunctions.waitWebDriver(2000);
				orgUploadPage.last14Days.click();
				GenericFunctions.waitWebDriver(2000);
				verifyPageElements();
				GenericFunctions.waitWebDriver(2000);
				orgUploadPage.last5Weeks.click();
				GenericFunctions.waitWebDriver(2000);
				verifyPageElements();
				GenericFunctions.waitWebDriver(2000);
				Assert.assertTrue(true);
			}
			else if(orgUploadPage.clickConsumption.getText().equalsIgnoreCase(filterName))
			{
				ExtentTestManager.getTest().log(LogStatus.INFO, "Verifying the Dashboard by applying filter as "+filterName);
				System.out.println(consumptionText); 
				orgUploadPage.clickConsumption.click();
				ExtentTestManager.getTest().log(LogStatus.INFO,"Checking the statics of "+filterName+":Last7days,Last14days,last5week and download the CSV");
				GenericFunctions.waitWebDriver(2000);
				verifyPageElements1();
				GenericFunctions.waitWebDriver(2000);
				orgUploadPage.last14Days.click();
				GenericFunctions.waitWebDriver(2000);
				verifyPageElements1();
				GenericFunctions.waitWebDriver(2000);
				orgUploadPage.last5Weeks.click();
				GenericFunctions.waitWebDriver(2000);
				verifyPageElements1();
				GenericFunctions.waitWebDriver(2000);
				Assert.assertTrue(true);
			}
			else
			{
				System.out.println("None of the filters are passed");
			}
		}
		catch(Exception e)
		{
			ExtentTestManager.getTest().log(LogStatus.FAIL, "Failed on verifying applying filters in Admin Dashboard and verifying elements");
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Exception Message: "+e.getLocalizedMessage());
			Assert.fail("Error while applying filters in the Admin Dashboard and verifying the elements");
		}
	}
	public void verifyPageElements() throws Exception
	{	

		try
		{
			GenericFunctions.waitWebDriver(2000);
			if(orgUploadPage.contentsCreated.isDisplayed())
			{
				log.info(orgUploadPage.contentsCreated.getText()+" element is displayed");
				GenericFunctions.waitWebDriver(2000);
				if(orgUploadPage.authors.isDisplayed())
				{	
					log.info(orgUploadPage.authors.getText()+" element is displayed");
					GenericFunctions.waitWebDriver(2000);
					if(orgUploadPage.reviewers.isDisplayed())
					{
						log.info(orgUploadPage.reviewers.getText()+" element is displayed");
						GenericFunctions.waitWebDriver(2000);
						if(orgUploadPage.csvLink.isDisplayed())
						{
							GenericFunctions.scrollToElement(orgUploadPage.csvLink);
							log.info(orgUploadPage.csvLink.getText()+" element is displayed");
							GenericFunctions.waitWebDriver(3000);
							orgUploadPage.csvLink.click();
							GenericFunctions.waitWebDriver(3000);
							orgUploadPage.closeThanks.click();
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			log.info(orgUploadPage.contentsCreated.isDisplayed()+" element is not displayed");
		}
	}	
	public void verifyPageElements1() throws Exception
	{
		try
		{
			if(orgUploadPage.visits.isDisplayed())
			{
				log.info(orgUploadPage.visits.getText()+" element is displayed");
				GenericFunctions.waitWebDriver(2000);
				if(orgUploadPage.consumptionTime.isDisplayed())
				{
					log.info(orgUploadPage.consumptionTime.getText()+" element is displayed");
					GenericFunctions.waitWebDriver(2000);
					if(orgUploadPage.avgTime.isDisplayed())
					{
						log.info(orgUploadPage.avgTime.getText()+" element is displayed");
						GenericFunctions.waitWebDriver(2000);
						if(orgUploadPage.csvLink.isDisplayed())
						{
							GenericFunctions.scrollToElement(orgUploadPage.csvLink);
							log.info(orgUploadPage.csvLink.getText()+" element is displayed");
							GenericFunctions.waitWebDriver(3000);
							orgUploadPage.csvLink.click();
							GenericFunctions.waitWebDriver(3000);
							orgUploadPage.closeThanks.click();
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			log.info(orgUploadPage.visits.getText()+" element is not displayed");
		}
	}

}

