package org.sunbird.integration.test.course.batch;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import java.util.UUID;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.ContentStoreUtil;
import org.sunbird.common.action.CourseBatchUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddUserToCourseBatch extends BaseCitrusTestRunner {

  public static final String TEST_NAME_ADD_USER_TO_BATCH_FAILURE_WITH_INVALID_BATCHID =
      "testAddUserToBatchFailureWithInvalidBatchId";
  public static final String TEST_NAME_ADD_USER_TO_BATCH_FAILURE_WITHOUT_AUTH_TOKEN =
      "testAddUserToBatchFailureWithoutAuthtoken";
  public static final String TEST_NAME_ADD_USER_TO_BATCH_FAILURE_WITHOUT_USER_IDS =
      "testAddUserToBatchFailureWithoutUserIds";
  public static final String TEST_NAME_ADD_USER_TO_BATCH_FAILURE_FOR_OPEN_BATCH =
      "testAddUserToBatchFailureForOpenBatch";
  public static final String TEST_NAME_ADD_USER_TO_BATCH_FAILURE_FOR_Invitee_Only_WITHOUT_ORG_ID =
      "testAddUserToBatchFailureForInviteeOnlyBatchWithoutOrg";

  public static final String TEMPLATE_DIR = "templates/course/batch/addUser";
  private static String courseBatchId = UUID.randomUUID().toString();

  private String getAddUserToBatchUrl() {
    return getLmsApiUriPath("/api/course/v1/batch/user/add", "/v1/course/batch/users/add/");
  }

  @DataProvider(name = "addUserToBatchDataFailureProvider")
  public Object[][] addUserToBatchDataFailureProvider() {
    return new Object[][] {
      new Object[] {
        TEST_NAME_ADD_USER_TO_BATCH_FAILURE_WITHOUT_AUTH_TOKEN,
        false,
        false,
        false,
        HttpStatus.UNAUTHORIZED
      },
      new Object[] {
        TEST_NAME_ADD_USER_TO_BATCH_FAILURE_WITH_INVALID_BATCHID,
        true,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_ADD_USER_TO_BATCH_FAILURE_WITHOUT_USER_IDS,
        true,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_ADD_USER_TO_BATCH_FAILURE_FOR_OPEN_BATCH, true, true, true, HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_NAME_ADD_USER_TO_BATCH_FAILURE_FOR_Invitee_Only_WITHOUT_ORG_ID,
        true,
        true,
        false,
        HttpStatus.NOT_FOUND
      },
    };
  }

  @Test(dataProvider = "addUserToBatchDataFailureProvider")
  @CitrusParameters({
    "testName",
    "isAuthRequired",
    "courseBatchCreate",
    "isOpenBatch",
    "httpStatusCode"
  })
  @CitrusTest
  public void testAddUserToBatchFailure(
      String testName,
      boolean isAuthRequired,
      boolean courseBatchCreate,
      boolean isOpenBatch,
      HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    getAuthToken(this, isAuthRequired);
    before(courseBatchCreate, isOpenBatch);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getAddUserToBatchUrl() + courseBatchId,
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  private void before(boolean courseBatchCreate, boolean isOpenBatch) {
    if (courseBatchCreate) {
      variable("courseUnitId", ContentStoreUtil.getCourseUnitId());
      variable("resourceId", ContentStoreUtil.getResourceId());
      variable("startDate", CreateCourseBatchTest.TODAY_DATE);
      String courseId = ContentStoreUtil.getCourseId(this, testContext);
      variable("courseId", courseId);
      if (isOpenBatch) {
        courseBatchId = CourseBatchUtil.getOpenCourseBatchId(this, testContext);
      } else {
        courseBatchId = CourseBatchUtil.getInviteOnlyCourseBatchId(this, testContext);
      }
    }
  }
}
