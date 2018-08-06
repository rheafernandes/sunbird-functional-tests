package org.sunbird.integration.test.dashboard.org;

import org.springframework.http.HttpStatus;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;

public class OrgCreationDashboardTest extends BaseCitrusTestRunner {

	public static final String TEST_NAME_ORG_CREATION_DASHBOARD_FAILURE_WITHOUT_ACCESS_TOKEN =
			"testOrgCreationDashboardFailureWithoutAccessToken";
	public static final String TEST_NAME_ORG_CREATION_DASHBOARD_FAILURE_WITH_INVALID_ORG_ID =
			"testOrgCreationDashboardFailureWithInvalidOrgId";
	public static final String TEST_NAME_ORG_CREATION_DASHBOARD_FAILURE_WITH_INVALID_TIME_PERIOD =
			"testOrgCreationDashboardFailureWithInvalidTimePeriod";
	
	public static final String TEST_NAME_ORG_CREATION_DASHBOARD_SUCCESS_WITH_VALID_ORG_ID =
			"testOrgCreationDashboardSuccessWithValidOrgId";
	
	public static String ROOT_ORG_ID = "1";


	public static final String TEMPLATE_DIR = "templates/dashboard/org/creation";


	private String getOrgCreationUrl(String pathParam) {
		return getLmsApiUriPath("/api/dashboard/v1/creation/org", "/v1/dashboard/creation/org", pathParam);
	}

	@DataProvider(name = "orgCreationFailureDataProvider")
	public Object[][] orgCreationFailureDataProvider() {

		return new Object[][] {
			new Object[] {
					TEST_NAME_ORG_CREATION_DASHBOARD_FAILURE_WITHOUT_ACCESS_TOKEN, false, HttpStatus.UNAUTHORIZED,false
			},
			new Object[] {
					TEST_NAME_ORG_CREATION_DASHBOARD_FAILURE_WITH_INVALID_ORG_ID, true, HttpStatus.BAD_REQUEST,false
			},
			new Object[] {
					TEST_NAME_ORG_CREATION_DASHBOARD_FAILURE_WITH_INVALID_TIME_PERIOD, true, HttpStatus.BAD_REQUEST,true
			}

		};
	}

	@Test(dataProvider = "orgCreationFailureDataProvider")
	@CitrusParameters({"testName", "isAuthRequired", "httpStatusCode","canCreateOrg"})
	@CitrusTest
	public void testOrgCreationFailure(
			String testName, boolean isAuthRequired, HttpStatus httpStatusCode,boolean canCreateOrg) {
		getAuthToken(this, isAuthRequired);
		getTestCase().setName(testName);
		
		variable("rootOrgChannel", OrgUtil.getRootOrgChannel());
		variable("rootOrgExternalId", OrgUtil.getRootOrgExternalId());
		beforeTest(canCreateOrg);
		
		String url = null;
		
		if(canCreateOrg) {
			if(testName.equalsIgnoreCase(TEST_NAME_ORG_CREATION_DASHBOARD_FAILURE_WITH_INVALID_TIME_PERIOD)) {
				url = getOrgCreationUrl(ROOT_ORG_ID+"?period=1");
			}
			
		}else {
			url = getOrgCreationUrl(ROOT_ORG_ID);
		}
		
		performGetTest(
				this, TEMPLATE_DIR, testName, url, isAuthRequired, httpStatusCode, RESPONSE_JSON);
	}
	
	@DataProvider(name = "orgCreationSuccessDataProvider")
	public Object[][] orgCreationSuccessDataProvider() {

		return new Object[][] {			
			new Object[] {
					TEST_NAME_ORG_CREATION_DASHBOARD_SUCCESS_WITH_VALID_ORG_ID, true, HttpStatus.OK,true
			},

		};
	}

	@Test(dataProvider = "orgCreationSuccessDataProvider")
	@CitrusParameters({"testName", "isAuthRequired", "httpStatusCode","canCreateOrg"})
	@CitrusTest
	public void testOrgCreationSuccess(
			String testName, boolean isAuthRequired, HttpStatus httpStatusCode,boolean canCreateOrg) {
		getAuthToken(this, isAuthRequired);
		getTestCase().setName(testName);
		
		variable("rootOrgChannel", OrgUtil.getRootOrgChannel());
		variable("rootOrgExternalId", OrgUtil.getRootOrgExternalId());
		beforeTest(canCreateOrg);
		
		String url = null;
		
		if(canCreateOrg) {
			if(testName.equalsIgnoreCase(TEST_NAME_ORG_CREATION_DASHBOARD_SUCCESS_WITH_VALID_ORG_ID)){
				url = getOrgCreationUrl(ROOT_ORG_ID+"?period=7d");
			}
			
		}else {
			url = getOrgCreationUrl(ROOT_ORG_ID);
		}
		
		performGetTest(
				this, TEMPLATE_DIR, testName, url, isAuthRequired, httpStatusCode, RESPONSE_JSON);
	}
	private void beforeTest(boolean canCreateOrg) {
		if(canCreateOrg)
			ROOT_ORG_ID = OrgUtil.getRootOrgId(this, testContext);
	}

}

