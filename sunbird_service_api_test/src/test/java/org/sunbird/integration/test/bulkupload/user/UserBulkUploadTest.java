package org.sunbird.integration.test.bulkupload.user;

import org.springframework.http.HttpStatus;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;

public class UserBulkUploadTest extends BaseCitrusTestRunner {

	public static final String TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITHOUT_ACCESS_TOKEN =
			"testUserBulkUploadFailureWithoutAccessToken";
	public static final String TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITHOUT_ORG =
			"testUserBulkUploadFailureWithoutOrgDetails";
	public static final String TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITH_INVALID_ORG_ID =
			"testUserBulkUploadFailureWithInvalidOrgId";
	public static final String TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITHOUT_CSV_FILE =
			"testUserBulkUploadFailureWithoutCsvFile";
	public static final String TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITH_EMPTY_CSV_FILE =
			"testUserBulkUploadFailureWithEmptyCsvFile";
	public static final String TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITHOUT_COLUMN_HEADER_IN_CSV_FILE =
			"testUserBulkUploadFailureWithoutColumnHeaderInCsvFile";
	public static final String TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITH_INVALID_COLUMN =
			"testUserBulkUploadFailureWithInvalidColumn";
	
	
	private static String ROOT_ORG_ID = null;
	public static final String TEMPLATE_DIR = "templates/bulkupload/user";
	public static final String BT_ORG_CREATE_TEMPLATE_DIR = "templates/org/create";

	private String getUserBulkUploadUrl() {
		return getLmsApiUriPath("/api/user/v1/upload", "/v1/bulk/user/upload");
	}

	@DataProvider(name = "userBulkUploadFailureDataProvider")
	public Object[][] userBulkUploadFailureDataProvider() {

		return new Object[][] {
			new Object[] {TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITHOUT_ACCESS_TOKEN, HttpStatus.UNAUTHORIZED, false, false},
			new Object[] {TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITHOUT_ORG, HttpStatus.BAD_REQUEST, true, false},
			new Object[] {TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITH_INVALID_ORG_ID, HttpStatus.BAD_REQUEST, true, false},
			new Object[] {TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITHOUT_CSV_FILE, HttpStatus.BAD_REQUEST, true, false},
			new Object[] {TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITH_EMPTY_CSV_FILE, HttpStatus.BAD_REQUEST, true,true},
			new Object[] {TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITHOUT_COLUMN_HEADER_IN_CSV_FILE, HttpStatus.BAD_REQUEST, true,true},
			new Object[] {TEST_NAME_USER_BULK_UPLOAD_FAILURE_WITH_INVALID_COLUMN, HttpStatus.BAD_REQUEST, true,true},
			


		};
	}

	@Test(dataProvider = "userBulkUploadFailureDataProvider")
	@CitrusParameters({"testName", "httpStatusCode", "isAuthRequired", "canCreateOrg"})
	@CitrusTest
	public void testUserBulkUploadFailure(
			String testName, HttpStatus httpStatusCode, boolean isAuthRequired,boolean canCreateOrg) {
		getTestCase().setName(testName);
		getAuthToken(this, true);
		beforeTest(canCreateOrg);
		performMultipartTest(
				this,
				TEMPLATE_DIR,
				testName,
				getUserBulkUploadUrl(),
				REQUEST_FORM_DATA,
				null,
				isAuthRequired,
				httpStatusCode,
				RESPONSE_JSON);
	}
	private void beforeTest(boolean canCreateOrg) {
		if(ROOT_ORG_ID == null) {
			variable("rootOrgChannel", OrgUtil.getRootOrgChannel());
			ROOT_ORG_ID = OrgUtil.getRootOrgId(this, testContext);
		}else {				
			testContext.setVariable("organisationId", ROOT_ORG_ID);
		}
	}
}
