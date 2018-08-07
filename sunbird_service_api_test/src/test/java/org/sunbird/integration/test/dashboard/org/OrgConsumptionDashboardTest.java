package org.sunbird.integration.test.dashboard.org;

import org.springframework.http.HttpStatus;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;

public class OrgConsumptionDashboardTest extends BaseCitrusTestRunner {

	public static final String TEST_NAME_ORG_CONSUMPTION_DASHBOARD_FAILURE_WITHOUT_ACCESS_TOKEN =
			"testOrgConsumptionDashboardFailureWithoutAccessToken";
	public static final String TEST_NAME_ORG_CONSUMPTION_DASHBOARD_FAILURE_WITH_INVALID_ORG_ID =
			"testOrgConsumptionDashboardFailureWithInvalidOrgId";
	public static final String TEST_NAME_ORG_CONSUMPTION_DASHBOARD_FAILURE_WITH_INVALID_TIME_PERIOD =
			"testOrgConsumptionDashboardFailureWithInvalidTimePeriod";

	public static final String TEST_NAME_ORG_CONSUMPTION_DASHBOARD_SUCCESS_WITH_VALID_ORG_ID =
			"testOrgConsumptionDashboardSuccessWithValidOrgId";

	public static String INVALID_ROOT_ORG_ID = "invaldRootOrg1";
	public static String ROOT_ORG_ID = null;

	public static final String TEMPLATE_DIR = "templates/dashboard/org/consumption";


	private String getOrgConsumptionUrl(String pathParam) {
		return getLmsApiUriPath("/api/dashboard/v1/consumption/org", "/v1/dashboard/consumption/org", pathParam);
	}

	@DataProvider(name = "orgConsumptionDashboardFailureDataProvider")
	public Object[][] orgConsumptionDashboardFailureDataProvider() {

		return new Object[][] {
			new Object[] {
					TEST_NAME_ORG_CONSUMPTION_DASHBOARD_FAILURE_WITHOUT_ACCESS_TOKEN, false, HttpStatus.UNAUTHORIZED,false,""
			},
			new Object[] {
					TEST_NAME_ORG_CONSUMPTION_DASHBOARD_FAILURE_WITH_INVALID_ORG_ID, true, HttpStatus.BAD_REQUEST,false,"?period=1d"
			},
			new Object[] {
					TEST_NAME_ORG_CONSUMPTION_DASHBOARD_FAILURE_WITH_INVALID_TIME_PERIOD, true, HttpStatus.BAD_REQUEST,true,"?period=1"
			}

		};
	}

	@Test(dataProvider = "orgConsumptionDashboardFailureDataProvider")
	@CitrusParameters({"testName", "isAuthRequired", "httpStatusCode","canCreateOrg","pathParam"})
	@CitrusTest
	public void testOrgConsumptionFailure(
			String testName, boolean isAuthRequired, HttpStatus httpStatusCode,boolean canCreateOrg, String pathParam) {
		getAuthToken(this, isAuthRequired);
		getTestCase().setName(testName);

		variable("rootOrgChannel", OrgUtil.getRootOrgChannel());
		variable("rootOrgExternalId", OrgUtil.getRootOrgExternalId());
		beforeTest(canCreateOrg);

		String url = null;

		if(canCreateOrg) {
			url = getOrgConsumptionUrl(ROOT_ORG_ID + pathParam);
			}else {
			url = getOrgConsumptionUrl(INVALID_ROOT_ORG_ID);
		}

		performGetTest(
				this, TEMPLATE_DIR, testName, url, isAuthRequired, httpStatusCode, RESPONSE_JSON);
	}

	@DataProvider(name = "orgConsumptionDashboardSuccessDataProvider")
	public Object[][] orgConsumptionDashboardSuccessDataProvider() {

		return new Object[][] {

			new Object[] {
					TEST_NAME_ORG_CONSUMPTION_DASHBOARD_SUCCESS_WITH_VALID_ORG_ID, true, HttpStatus.OK,true,"?period=7d"
			},

		};
	}

	@Test(dataProvider = "orgConsumptionDashboardSuccessDataProvider")
	@CitrusParameters({"testName", "isAuthRequired", "httpStatusCode","canCreateOrg","pathParam"})
	@CitrusTest
	public void testOrgConsumptionSuccess(
			String testName, boolean isAuthRequired, HttpStatus httpStatusCode,boolean canCreateOrg, String pathParam) {
		getAuthToken(this, isAuthRequired);
		getTestCase().setName(testName);

		variable("rootOrgChannel", OrgUtil.getRootOrgChannel());
		variable("rootOrgExternalId", OrgUtil.getRootOrgExternalId());
		

		String url = null;

		if(canCreateOrg) {
			beforeTest(canCreateOrg);
			url = getOrgConsumptionUrl(ROOT_ORG_ID + pathParam);
		}

		performGetTest(
				this, TEMPLATE_DIR, testName, url, isAuthRequired, httpStatusCode, RESPONSE_JSON);
	}

	private void beforeTest(boolean canCreateOrg) {
		if(canCreateOrg) {
			if(ROOT_ORG_ID == null) {
				ROOT_ORG_ID = OrgUtil.getRootOrgId(this, testContext);
			}else {				
				testContext.setVariable("organisationId", ROOT_ORG_ID);
			}
		}
		
	}

}

