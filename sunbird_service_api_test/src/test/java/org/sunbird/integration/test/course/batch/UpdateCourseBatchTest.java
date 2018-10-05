package org.sunbird.integration.test.course.batch;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.ContentStoreUtil;
import org.sunbird.common.action.CourseBatchUtil;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.common.action.UserUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UpdateCourseBatchTest extends BaseCitrusTestRunner {

  private static final String TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITHOUT_AUTH_TOKEN =
      "testUpdateCourseBatchFailureWithoutAuthToken";
  private static final String TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_INVALID_BATCHID =
      "testUpdateCourseBatchFailureWithInvalidId";

  private static final String TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_INVALID_MENTOR =
      "testUpdateCourseBatchFailureWithInvalidMentor";

  private static final String TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_INVALID_PARTICIPANTS =
      "testUpdateCourseBatchFailureWithInvalidParticipants";

  private static final String TEST_NAME_UPDATE_COURSE_BATCH_SUCCESS_WITH_VALID_MENTORS =
      "testUpdateCourseBatchSuccessWithValidMentors";

  private static final String TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_VALID_PARTICIPANTS =
      "testUpdateCourseBatchSuccessWithValidParticipants";

  public static final String TEMPLATE_DIR = "templates/course/batch/update";
  public static final String TODAY_DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

  private String getUpdateCourseBatchUrl() {
    return getLmsApiUriPath("/api/course/v1/batch/update", "/v1/course/batch/update");
  }

  @DataProvider(name = "updateCourseBatchDataFailureProvider")
  public Object[][] updateCourseBatchDataFailureProvider() {
    return new Object[][] {
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITHOUT_AUTH_TOKEN, false, HttpStatus.UNAUTHORIZED
      },
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_INVALID_BATCHID, true, HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_INVALID_MENTOR, true, HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_INVALID_PARTICIPANTS,
        true,
        HttpStatus.BAD_REQUEST
      },
    };
  }

  @DataProvider(name = "updateCourseBatchDataSuccessProvider")
  public Object[][] updateCourseBatchDataSuccessProvider() {
    return new Object[][] {
      new Object[] {TEST_NAME_UPDATE_COURSE_BATCH_SUCCESS_WITH_VALID_MENTORS, true, HttpStatus.OK},
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_VALID_PARTICIPANTS, true, HttpStatus.OK
      },
    };
  }

  @Test(dataProvider = "updateCourseBatchDataFailureProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testUpdateCourseBatchFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest();
    variable("startDate", TODAY_DATE);
    performPatchTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUpdateCourseBatchUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "updateCourseBatchDataSuccessProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testUpdateCourseBatchSuccess(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest();
    variable("startDate", TODAY_DATE);
    performPatchTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUpdateCourseBatchUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  private void beforeTest() {
    UserUtil.createUserAndGetToken(this, testContext);
    variable("courseUnitId", ContentStoreUtil.getCourseUnitId());
    variable("resourceId", ContentStoreUtil.getResourceId());
    variable("startDate", TODAY_DATE);
    String courseId = ContentStoreUtil.getCourseId(this, testContext);
    ;
    variable("courseId", courseId);
    variable("rootOrgChannel", OrgUtil.getRootOrgChannel());
    OrgUtil.getRootOrgId(this, testContext);
    CourseBatchUtil.getInviteOnlyCourseBatchId(this, testContext);
    variable("batchId", testContext.getVariable("batchId"));
  }
}
