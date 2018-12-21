package org.sunbird.integration.test.user;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.UserUtil;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/** Created by rajatgupta on 21/12/18. */
public class GetUserByKey extends BaseCitrusTestRunner {
  private static final String TEST_GET_USER_BY_KEY_FAILURE_WITHOUT_KEY =
      "testGetUserByKeyFailureWithoutKey";
  private static final String TEST_GET_USER_BY_KEY_FAILURE_WITHOUT_VALUE =
      "testGetUserByKeyFailureWithoutValue";
  private static final String TEST_GET_USER_BY_KEY_FAILURE_WITH_INVALID_EMAIL =
      "testGetUserByKeyFailureWithInvalidEmail";
  private static final String TEST_GET_USER_BY_KEY_FAILURE_WITH_INVALID_PHONE =
      "testGetUserByKeyFailureWithInvalidPhone";

  private static final String TEST_GET_USER_BY_KEY_SUCCESS_WITHOUT_TOKEN =
      "testGetUserByKeySuccessWithoutToken";
  private static final String TEST_GET_USER_BY_KEY_SUCCESS_WITH_PHONE =
      "testGetUserByKeySuccessWithPhone";
  private static final String TEST_GET_USER_BY_KEY_SUCCESS_WITH_EMAIL =
      "testGetUserByKeySuccessWithEmail";
  private static final String TEST_GET_USER_BY_KEY_SUCCESS_WITH_LOGIN_ID =
      "testGetUserByKeySuccessWithLoginId";

  public static final String TEMPLATE_DIR = "templates/user/getuserbykey";

  private String getUserByKeyUrl(String idType, String id) {
    return getLmsApiUriPath("/api/user/v1/getByKey", "/v1/user/check", idType, id);
  }

  @DataProvider(name = "getUserByKeyFailureDataProvider")
  public Object[][] getUserByKeyFailureDataProvider() {

    return new Object[][] {
      new Object[] {TEST_GET_USER_BY_KEY_FAILURE_WITHOUT_KEY, true, HttpStatus.BAD_REQUEST},
      new Object[] {TEST_GET_USER_BY_KEY_FAILURE_WITHOUT_VALUE, true, HttpStatus.BAD_REQUEST},
      new Object[] {TEST_GET_USER_BY_KEY_FAILURE_WITH_INVALID_EMAIL, true, HttpStatus.BAD_REQUEST},
      new Object[] {TEST_GET_USER_BY_KEY_FAILURE_WITH_INVALID_PHONE, true, HttpStatus.BAD_REQUEST}
    };
  }

  @DataProvider(name = "getUserByKeySuccessDataProvider")
  public Object[][] getUserByKeySuccessDataProvider() {

    return new Object[][] {
      new Object[] {TEST_GET_USER_BY_KEY_SUCCESS_WITHOUT_TOKEN, false, HttpStatus.OK},
      new Object[] {TEST_GET_USER_BY_KEY_SUCCESS_WITH_PHONE, true, HttpStatus.OK},
      new Object[] {TEST_GET_USER_BY_KEY_SUCCESS_WITH_EMAIL, true, HttpStatus.OK},
      new Object[] {TEST_GET_USER_BY_KEY_SUCCESS_WITH_LOGIN_ID, true, HttpStatus.OK}
    };
  }

  @Test(dataProvider = "getUserByKeyFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testGetUserByKeyFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    getAuthToken(this, isAuthRequired);
    beforeTest();
    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUserByKeyUrl("email", "id"),
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "getUserByKeySuccessDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testGetUserByKeySuccess(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    getAuthToken(this, isAuthRequired);
    beforeTest();
    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUserByKeyUrl("email", "id"),
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  void beforeTest() {
    if (StringUtils.isBlank((String) testContext.getVariables().get(Constant.USER_ID))) {
      UserUtil.getUserWithEmailAndPhone(this, testContext, Constant.USER_ID);
    } else {
      variable("phone", testContext.getVariable("phone"));
      variable("email", testContext.getVariable("email"));
      variable("userName", testContext.getVariable("userName"));
      variable("channel", testContext.getVariable("channel"));
    }
  }
}
