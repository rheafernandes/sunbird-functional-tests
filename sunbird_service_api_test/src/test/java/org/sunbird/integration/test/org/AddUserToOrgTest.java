package org.sunbird.integration.test.org;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.common.action.UserUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddUserToOrgTest extends BaseCitrusTestRunner {

  public static final String TEST_ADD_USER_TO_ORG_SUCCESS_WITH_EMPTY_ROLE_ARRAY =
      "testAddUserToOrgSuccessWithEmptyRoleArray";

  public static final String TEST_NAME_ADD_USER_TO_ORG_FAILURE_WITH_INVALID_USER_ID =
      "testAddUserToOrgFailureWithInvalidUserId";
  public static final String TEST_NAME_ADD_USER_TO_ORG_FAILURE_WITH_INVALID_ORG_ID =
      "testAddUserToOrgFailureWithInvalidOrgId";

  public static final String TEMPLATE_DIR = "templates/organisation/user/add";

  private String getAddUserToOrgUrl() {
    return getLmsApiUriPath("/api/org/v1/member/add", "v1/org/member/add");
  }

  @DataProvider(name = "addUserToOrgFailureDataProvider")
  public Object[][] addUserToOrgFailureDataProvider() {

    return new Object[][] {
      new Object[] {TEST_NAME_ADD_USER_TO_ORG_FAILURE_WITH_INVALID_USER_ID},
      new Object[] {TEST_NAME_ADD_USER_TO_ORG_FAILURE_WITH_INVALID_ORG_ID},
    };
  }

  @Test(dataProvider = "addUserToOrgFailureDataProvider")
  @CitrusParameters({"testName"})
  @CitrusTest
  public void testAddUserToOrgFailure(String testName) {
    getAuthToken(this, true);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getAddUserToOrgUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        HttpStatus.BAD_REQUEST,
        RESPONSE_JSON);
  }

  @Test()
  @CitrusTest
  public void testAddUserToOrgSuccessWithEmptyRoleArray() {
    getTestCase().setName(TEST_ADD_USER_TO_ORG_SUCCESS_WITH_EMPTY_ROLE_ARRAY);
    getAuthToken(this, true);
    createUser();
    createOrg();
    performPostTest(
        this,
        TEMPLATE_DIR,
        TEST_ADD_USER_TO_ORG_SUCCESS_WITH_EMPTY_ROLE_ARRAY,
        getAddUserToOrgUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        HttpStatus.OK,
        RESPONSE_JSON);
  }

  private void createUser() {
    UserUtil.getUserId(this, testContext);
    variable("userId", testContext.getVariable("userId"));
  }

  private void createOrg() {
    variable("rootOrgChannel", OrgUtil.getRootOrgChannel());
    OrgUtil.getRootOrgId(this, testContext);
    variable("organisationId", testContext.getVariable("organisationId"));
  }
}
