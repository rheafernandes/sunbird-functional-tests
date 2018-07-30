package org.sunbird.integration.test.user.notes;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.UserNoteUtil;
import org.sunbird.common.action.UserUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReadUserNoteTest extends BaseCitrusTestRunner {

  private static final String TEST_READ_USER_NOTE_FAILURE_WITH_INVALID_USERID =
      "testReadUserNoteFailureWithInvalidUserId";

  private static final String TEST_READ_USER_NOTE_FAILURE_WITH_INVALID_NOTEID =
      "testReadUserNoteFailureWithInvalidNoteId";
  private static final String TEST_READ_USER_NOTE_SUCCESS = "testReadUserSuccess";
  public static final String BT_CREATE_NOTE_TEMPLATE_DIR = "templates/user/note/create";
  public static final String TEMPLATE_DIR = "templates/user/note/read";
  private static final String TEST_CREATE_USER_NOTE_SUCCESS_WITH_BOTH_COURSEID_AND_CONTENTID =
      "testCreateUserNoteSuccessWithCourseIdAndContentId";

  private String getReadNoteUrl(String pathParam) {
    return getLmsApiUriPath("/api/note/v1/read", "/v1/note/read", pathParam);
  }

  @DataProvider(name = "readUserNoteFailureDataProvider")
  public Object[][] readUserNoteFailureDataProvider() {

    return new Object[][] {
      new Object[] {TEST_READ_USER_NOTE_FAILURE_WITH_INVALID_USERID, true, HttpStatus.UNAUTHORIZED}
    };
  }

  @DataProvider(name = "readUserNoteSuccessDataProvider")
  public Object[][] readUserNoteSuccessDataProvider() {
    return new Object[][] {new Object[] {TEST_READ_USER_NOTE_SUCCESS, true, HttpStatus.OK}};
  }

  @Test(dataProvider = "readUserNoteFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testReadUserNoteFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    beforeTest(isAuthRequired);
    getAuthToken(this, isAuthRequired);
    performGetTest(
        this,
        testName,
        getReadNoteUrl(testContext.getVariable("noteId")),
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "readUserNoteSuccessDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testReadUserNoteSuccess(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest(isAuthRequired);
    performGetTest(
        this,
        testName,
        getReadNoteUrl(testContext.getVariable("noteId")),
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @Test()
  @CitrusTest
  public void testReadUserNoteFailureWithInvalidNoteId() {
    getTestCase().setName(TEST_READ_USER_NOTE_FAILURE_WITH_INVALID_NOTEID);
    beforeTest(true);
    performGetTest(
        this,
        TEST_READ_USER_NOTE_FAILURE_WITH_INVALID_NOTEID,
        getReadNoteUrl("InvalidNoteId"),
        true,
        HttpStatus.UNAUTHORIZED,
        RESPONSE_JSON);
  }

  void beforeTest(boolean isAuthRequired) {
    UserUtil.getUserId(this, testContext);
    String channelName = System.getenv("sunbird_default_channel");
    getAuthToken(this, isAuthRequired);
    getAuthToken(
        this,
        testContext.getVariable("userName") + "@" + channelName,
        "password",
        testContext.getVariable("userId"),
        true);
    variable("userId", testContext.getVariable("userId"));
    UserNoteUtil.createUserNote(
        this,
        testContext,
        BT_CREATE_NOTE_TEMPLATE_DIR,
        TEST_CREATE_USER_NOTE_SUCCESS_WITH_BOTH_COURSEID_AND_CONTENTID);
  }
}
