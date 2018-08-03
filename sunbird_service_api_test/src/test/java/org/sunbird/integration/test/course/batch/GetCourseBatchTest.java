package org.sunbird.integration.test.course.batch;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.ContentStoreUtil;
import org.sunbird.common.action.CourseBatchUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GetCourseBatchTest extends BaseCitrusTestRunner {

  public static final String TEST_NAME_GET_COURSE_BATCH_SUCCESS_WITH_OPEN_VALID_ID =
      "testGetCouseBatchSuccessWithOpenValidId";
  public static final String TEST_NAME_GET_COURSE_BATCH_SUCCESS_WITH_INVITEE_ONLY_VALID_ID =
      "testGetCouseBatchSuccessWithInviteeOnlyValidId";
  public static final String TEST_NAME_GET_COURSE_BATCH_FAILURE_WITH_INVALID_ID =
      "testGetCouseBatchFailureWithInValidId";
  public static final String TEST_NAME_GET_COURSE_BATCH_FAILURE_WITHOUT_AUTH_TOKEN =
      "testGetCouseBatchFailureWithoutAuthToken";
  private static String courseBatchId = null;
  public static final String TEMPLATE_DIR = "templates/course/batch/read";

  private String getGetCourseBatchUrl() {
    return getLmsApiUriPath("/api/course/v1/batch/read/", "/v1/course/batch/read/");
  }

  @DataProvider(name = "readCourseBatchDataFailureProvider")
  public Object[][] readCourseBatchDataFailureProvider() {
    return new Object[][] {
      new Object[] {TEST_NAME_GET_COURSE_BATCH_FAILURE_WITH_INVALID_ID, true, HttpStatus.OK},
      new Object[] {
        TEST_NAME_GET_COURSE_BATCH_FAILURE_WITHOUT_AUTH_TOKEN, false, HttpStatus.UNAUTHORIZED
      }
    };
  }

  @Test(dataProvider = "readCourseBatchDataFailureProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testReadCourseBatchFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getAuthToken(this, isAuthRequired);
    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getGetCourseBatchUrl() + UUID.randomUUID().toString(),
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @DataProvider(name = "readCourseBatchSuccessDataProvider")
  public Object[][] readCourseBatchSuccessDataProvider() {
    return new Object[][] {
      new Object[] {TEST_NAME_GET_COURSE_BATCH_SUCCESS_WITH_OPEN_VALID_ID, true},
      new Object[] {TEST_NAME_GET_COURSE_BATCH_SUCCESS_WITH_INVITEE_ONLY_VALID_ID, false}
    };
  }

  @Test(dataProvider = "readCourseBatchSuccessDataProvider")
  @CitrusParameters({"testName", "isOpen"})
  @CitrusTest
  public void testReadCourseBatchSuccess(String testName, boolean isOpen) {
    getAuthToken(this, true);
    before(isOpen);
    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getGetCourseBatchUrl() + courseBatchId,
        true,
        HttpStatus.OK,
        RESPONSE_JSON);
  }

  private void before(boolean isOpen) {
    variable("courseUnitId", ContentStoreUtil.getCourseUnitId());
    variable("resourceId", ContentStoreUtil.getResourceId());
    variable("startDate", CreateCourseBatchTest.TODAY_DATE);
    String courseId = ContentStoreUtil.getCourseId(this, testContext);
    variable("courseId", courseId);
    if (isOpen) {
      courseBatchId = CourseBatchUtil.getOpenCourseBatchId(this, testContext);
    } else {
      courseBatchId = CourseBatchUtil.getInviteOnlyCourseBatchId(this, testContext);
    }
  }
}
