package org.sunbird.integration.test.dashboard.org;

import org.springframework.http.HttpStatus;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;

public class OrgCreationTest extends BaseCitrusTestRunner {

	public static final String TEST_NAME_ORG_CREATION_FAILURE_WITHOUT_ACCESS_TOKEN =
			"testOrgCreationFailureWithoutAccessToken";
	public static final String TEST_NAME_ORG_CREATION_FAILURE_WITH_INVALID_ORG_ID =
			"testOrgCreationFailureWithInvalidOrgId";
	public static final String TEST_NAME_ORG_CREATION_FAILURE_WITH_INVALID_TIME_PERIOD =
			"testOrgCreationFailureWithInvalidTimePeriod";
	
	public static final String TEST_NAME_ORG_CREATION_SUCCESS_WITH_VALID_ORG_ID =
			"testOrgCreationSuccessWithValidOrgId";
	
	public static String ROOT_ORG_ID = "1";


	public static final String TEMPLATE_DIR = "templates/dashboard/org/creation";


	private String getOrgCreationUrl(String pathParam) {
		return getLmsApiUriPath("/api/dashboard/v1/creation/org", "/v1/dashboard/creation/org", pathParam);
	}

	@DataProvider(name = "orgCreationDataProvider")
	public Object[][] orgCreationDataProvider() {

		return new Object[][] {
			new Object[] {
					TEST_NAME_ORG_CREATION_FAILURE_WITHOUT_ACCESS_TOKEN, false, HttpStatus.UNAUTHORIZED,false
			},
			new Object[] {
					TEST_NAME_ORG_CREATION_FAILURE_WITH_INVALID_ORG_ID, true, HttpStatus.BAD_REQUEST,false
			},
			new Object[] {
					TEST_NAME_ORG_CREATION_FAILURE_WITH_INVALID_TIME_PERIOD, true, HttpStatus.BAD_REQUEST,true
			},
			new Object[] {
					TEST_NAME_ORG_CREATION_SUCCESS_WITH_VALID_ORG_ID, true, HttpStatus.OK,true
			},

		};
	}

	@Test(dataProvider = "orgCreationDataProvider")
	@CitrusParameters({"testName", "isAuthRequired", "httpStatusCode","canCreateOrg"})
	@CitrusTest
	public void testOrgCreation(
			String testName, boolean isAuthRequired, HttpStatus httpStatusCode,boolean canCreateOrg) {
		getAuthToken(this, isAuthRequired);
		getTestCase().setName(testName);
		
		variable("rootOrgChannel", OrgUtil.getRootOrgChannel());
		variable("rootOrgExternalId", OrgUtil.getRootOrgExternalId());
		beforeTest(canCreateOrg);
		
		String url = null;
		
		if(canCreateOrg) {
			if(testName.equalsIgnoreCase(TEST_NAME_ORG_CREATION_FAILURE_WITH_INVALID_TIME_PERIOD)) {
				url = getOrgCreationUrl(ROOT_ORG_ID+"?period=1");
			}else if(testName.equalsIgnoreCase(TEST_NAME_ORG_CREATION_SUCCESS_WITH_VALID_ORG_ID)){
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

