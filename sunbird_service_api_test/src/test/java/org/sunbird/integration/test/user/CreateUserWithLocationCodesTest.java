package org.sunbird.integration.test.user;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.LocationUtil;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class CreateUserWithLocationCodesTest extends BaseCitrusTestRunner {
  public static final String TEST_NAME_CREATE_USER_FAILURE_WITH_INVALID_LOCATION_CODES =
      "testCreateUserFailureWithInvalidLocationCodes";
  public static final String TEST_NAME_CREATE_USER_SUCCES_WITH_LOCATION_CODES =
      "testCreateUserSuccessWithLocationCodes";

  public static final String TEMPLATE_DIR = "templates/user/create";

  private String getCreateUserUrl() {
    return getLmsApiUriPath("/api/user/v1/create", "/v1/user/create");
  }

  private String getCreateLocationUrl() {
    return getLmsApiUriPath("/api/data/v1/location/create", "/v1/location/create");
  }

  @DataProvider(name = "createUserFailureDataProvider")
  public Object[][] createUserFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_NAME_CREATE_USER_FAILURE_WITH_INVALID_LOCATION_CODES, false, HttpStatus.BAD_REQUEST
      }
    };
  }

  @DataProvider(name = "createUserSuccessDataProvider")
  public Object[][] createUserSuccessDataProvider() {
    return new Object[][] {
      new Object[] {TEST_NAME_CREATE_USER_SUCCES_WITH_LOCATION_CODES, false, HttpStatus.OK},
    };
  }

  @Test(dataProvider = "createUserFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testCreateUserFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getCreateUserUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "createUserSuccessDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testCreateUserSuccess(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest();
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getCreateUserUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  private void beforeTest() {
    if (StringUtils.isBlank((String) testContext.getVariables().get(Constant.DISTRICT_ID))) {
      getAuthToken(this, true);
      LocationUtil.createDistrict(
          this,
          testContext,
          "templates/location/district/create/",
          "testCreateDistrictLocationSuccess",
          getCreateLocationUrl(),
          REQUEST_JSON);
    }
    variable("locationCode", LocationUtil.DISTRICT_CODE);
  }
}
