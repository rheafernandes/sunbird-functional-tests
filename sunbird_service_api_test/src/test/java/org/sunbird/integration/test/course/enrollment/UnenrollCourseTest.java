package org.sunbird.integration.test.course.enrollment;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.ContentStoreUtil;
import org.sunbird.common.action.CourseBatchUtil;
import org.sunbird.common.action.CourseEnrollmentUtil;
import org.sunbird.common.action.UserUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UnenrollCourseTest extends BaseCitrusTestRunner {

  public static final String TEST_NAME_UNENROLL_COURSE_FAILURE_WITHOUT_COURSE_ID =
      "testUnenrollCourseFailureWithoutCourseId";
  public static final String TEST_NAME_UNENROLL_COURSE_FAILURE_WITHOUT_BATCH_ID =
      "testUnenrollCourseFailureWithoutBatchId";
  public static final String TEST_NAME_UNENROLL_COURSE_FAILURE_WITHOUT_USER_ID =
      "testUnenrollCourseFailureWithoutUserId";
  public static final String TEST_NAME_UNENROLL_COURSE_FAILURE_INVITE_ONLY_BATCH =
      "testUnenrollCourseFailureForInviteOnlyBatch";
  public static final String TEST_NAME_UNENROLL_COURSE_FAILURE_INVALID_COURSE_BATCH =
      "testUnenrollCourseFailureForInvalidCourseBatch";
  public static final String TEST_NAME_UNENROLL_COURSE_FAILURE_INVALID_USER_COURSE =
      "testUnenrollCourseFailureInvalidUserCourse";
  public static final String TEST_NAME_UNENROLL_COURSE_FAILURE_WITH_USER_NOT_ENROLLED =
      "testUnenrollCourseFailureWithUserNotEnrolled";
  public static final String TEST_NAME_UNENROLL_COURSE_FAILURE_WITH_USER_ALREADY_UNENROLLED =
      "testUnenrollCourseFailureWithUserAlreadyUnenrolled";
  public static final String TEST_NAME_ENROLL_COURSE_SUCCESS_FOR_USER_UNENROLLED =
      "testEnrollCourseSuccessForUserUnenrolled";
  public static final String TEST_NAME_UNENROLL_COURSE_SUCCESS = "testUnenrollCourseSuccess";

  public static final String TEMPLATE_DIR = "templates/course/batch/unenroll";
  private static String courseBatchId = UUID.randomUUID().toString();
  private static final String TODAY_DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

  private String getEnrollCourseBatchUrl() {
    return getLmsApiUriPath("/api/course/v1/unenroll", "/v1/course/enrol");
  }

  private String getUnenrollCourseBatchUrl() {
    return getLmsApiUriPath("/api/course/v1/enroll", "/v1/course/unenroll");
  }

  @DataProvider(name = "courseUnenrollmentDataProviderFailure")
  public Object[][] courseUnenrollmentDataProviderFailure() {
    return new Object[][] {
      new Object[] {
        TEST_NAME_UNENROLL_COURSE_FAILURE_WITHOUT_COURSE_ID, false, false, false, false, false
      },
      new Object[] {
        TEST_NAME_UNENROLL_COURSE_FAILURE_WITHOUT_BATCH_ID, false, false, false, false, false
      },
      new Object[] {
        TEST_NAME_UNENROLL_COURSE_FAILURE_WITHOUT_USER_ID, false, false, false, false, false
      },
      new Object[] {
        TEST_NAME_UNENROLL_COURSE_FAILURE_INVITE_ONLY_BATCH, true, true, false, false, false
      },
      new Object[] {
        TEST_NAME_UNENROLL_COURSE_FAILURE_INVALID_COURSE_BATCH, true, true, true, false, false
      },
      new Object[] {
        TEST_NAME_UNENROLL_COURSE_FAILURE_INVALID_USER_COURSE, true, true, true, false, false
      },
      new Object[] {
        TEST_NAME_UNENROLL_COURSE_FAILURE_WITH_USER_NOT_ENROLLED, true, true, true, false, false
      },
      new Object[] {
        TEST_NAME_UNENROLL_COURSE_FAILURE_WITH_USER_ALREADY_UNENROLLED, true, true, true, true, true
      },
    };
  }

  @Test(dataProvider = "courseUnenrollmentDataProviderFailure")
  @CitrusParameters({
    "testName",
    "canCreateUser",
    "canCreateCourseBatch",
    "isOpenBatch",
    "canEnroll",
    "canUnenroll"
  })
  @CitrusTest
  public void testUnenrollCourseFailure(
      String testName,
      boolean canCreateUser,
      boolean canCreateCourseBatch,
      boolean isOpenBatch,
      boolean canEnroll,
      boolean canUnenroll) {
    beforeTest(testName, canCreateUser, canCreateCourseBatch, isOpenBatch, canEnroll, canUnenroll);
    ;
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUnenrollCourseBatchUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        HttpStatus.BAD_REQUEST,
        RESPONSE_JSON);
  }

  @DataProvider(name = "courseUnenrollmentDataProviderSuccess")
  public Object[][] courseUnenrollmentDataProviderSuccess() {
    return new Object[][] {
      new Object[] {TEST_NAME_UNENROLL_COURSE_SUCCESS, false, getUnenrollCourseBatchUrl()},
      new Object[] {
        TEST_NAME_ENROLL_COURSE_SUCCESS_FOR_USER_UNENROLLED, true, getEnrollCourseBatchUrl()
      },
    };
  }

  @Test(dataProvider = "courseUnenrollmentDataProviderSuccess")
  @CitrusParameters({"testName", "canEnroll", "apiPath"})
  @CitrusTest
  public void testUnenrollCourseSuccess(String testName, boolean canUnenroll, String apiPath) {
    getTestCase().setName(testName);
    getAuthToken(this, true);
    beforeTest(testName, true, true, true, true, canUnenroll);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        apiPath,
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        HttpStatus.OK,
        RESPONSE_JSON);
  }

  private void beforeTest(
      String testName,
      boolean canCreateUser,
      boolean canCreateCourseBatch,
      boolean isOpenBatch,
      boolean canEnroll,
      boolean canUnenroll) {
    getTestCase().setName(testName);
    getAuthToken(this, true);
    if (canCreateUser) {
      UserUtil.createUserAndGetToken(this, testContext);
    }
    if (canCreateCourseBatch) {
      variable("courseUnitId", ContentStoreUtil.getCourseUnitId());
      variable("resourceId", ContentStoreUtil.getResourceId());
      variable("startDate", TODAY_DATE);
      String courseId = ContentStoreUtil.getCourseId(this, testContext);
      variable("courseId", courseId);
      if (isOpenBatch) {
        courseBatchId = CourseBatchUtil.getOpenCourseBatchId(this, testContext);
      } else {
        courseBatchId = CourseBatchUtil.getInviteOnlyCourseBatchId(this, testContext);
      }
      variable("batchId", courseBatchId);
    }
    if (canEnroll) {
      CourseEnrollmentUtil.enrollCourse(this, testContext, config);
    }

    if (canUnenroll) {
      CourseEnrollmentUtil.unenrollCourse(this, testContext, config);
    }
  }
}
