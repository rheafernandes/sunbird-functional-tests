package org.sunbird.integration.test.course.batch;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UpdateCourseBatchTest extends BaseCitrusTestRunner {

  // failure
  private static final String TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITHOUT_AUTH_TOKEN =
      "testUpdateCourseBatchFailureWithoutAuthToken";
  private static final String TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_INVALID_ID =
      "testUpdateCourseBatchFailureWithInvalidId";
  private static final String
      TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_START_DATE_AND_COURSE_BATCH_STARTED =
          "testUpdateCourseBatchFailureWithStartDateAndCourseBatchStarted";
  private static final String
      TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_END_DATE_AND_COURSE_BATCH_STARTED =
          "testUpdateCourseBatchFailureWithEndDateAndCourseBatchStarted";
  private static final String
      TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_DIFFERENT_START_DATE_AND_END_DATE_AND_COURSE_BATCH_STARTED =
          "testUpdateCourseBatchFailureWithDifferentStartDateAndEndDateAndCourseBatchStarted";
  private static final String
      TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_FUTURE_END_DATE_AND_COURSE_BATCH_NOT_STARTED =
          "testUpdateCourseBatchFailureWithFutureEndDateAndCourseBatchNotStarted";
  private static final String
      TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_PAST_START_DATE_AND_COURSE_BATCH_NOT_STARTED =
          "testUpdateCourseBatchFailureWithPastStartDateAndCourseBatchNotStarted";
  private static final String
      TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_PAST_END_DATE_AND_COURSE_BATCH_NOT_STARTED =
          "testUpdateCourseBatchFailureWithPastEndDateAndCourseBatchNotStarted";
  private static final String
      TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_END_DATE_BEFORE_FUTURE_START_DATE_AND_COURSE_BATCH_NOT_STARTED =
          "testUpdateCourseBatchFailureWithEndDateBeforeFutureStartDateAndCourseBatchNotStarted";
  private static final String
      TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_START_DATE_AND_COURSE_BATCH_COMPLETED =
          "testUpdateCourseBatchFailureWithStartDateAndCourseBatchCompleted";
  private static final String
      TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_END_DATE_AND_COURSE_BATCH_COMPLETED =
          "testUpdateCourseBatchFailureWithEndDateAndCourseBatchCompleted";
  private static final String
      TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_START_DATE_AND_END_DATE_AND_COURSE_BATCH_COMPLETED =
          "testUpdateCourseBatchFailureWithStartDateAndEndDateAndCourseBatchCompleted";

  // success
  private static final String
      TEST_NAME_UPDATE_COURSE_BATCH_SUCCESS_WITH_SAME_START_DATE_AND_END_DATE_AND_COURSE_BATCH_STARTED =
          "testUpdateCourseBatchSuccessWithSameStartDateAndEndDateAndCourseBatchStarted";
  private static final String
      TEST_NAME_UPDATE_COURSE_BATCH_SUCCESS_WITH_FUTURE_START_DATE_AND_COURSE_BATCH_NOT_STARTED =
          "testUpdateCourseBatchSuccessWithFutureStartDateAndCourseBatchNotStarted";
  private static final String
      TEST_NAME_UPDATE_COURSE_BATCH_SUCCESS_WITH_FUTURE_START_DATE_AND_END_DATE_AND_COURSE_BATCH_NOT_STARTED =
          "testUpdateCourseBatchSuccessWithFutureStartDateAndEndDateAndCourseBatchNotStarted";

  public static final String SAME_START_DATE = "2018-08-30";
  public static final String SAME_END_DATE = "2018-09-09";
  public static final String PAST_START_DATE = "2018-08-31";
  public static final String PAST_END_DATE = "2018-09-02";
  public static final String FUTURE_START_DATE = "2018-09-15";
  public static final String FUTURE_END_DATE = "2018-09-25";
  public static final String FUTURE_BEFORE_END_DATE = "2018-09-12";

  public static final String TEMPLATE_DIR = "templates/course/batch/update";

  public static final String Id_COURSE_BATCH_NOT_STARTED = "0125727457222983685";
  public static final String NAME_COURSE_BATCH_NOT_STARTED = "kirtitest999";

  public static final String Id_COURSE_BATCH_STARTED = "0125728227995648004";
  public static final String NAME_COURSE_BATCH_STARTED = "kirtitest3333";

  public static final String Id_COURSE_BATCH_COMPLETED = "0125706929205493760";
  public static final String NAME_COURSE_BATCH_COMPLETED = "kirtitest222";

  private String getUpdateCourseBatchUrl() {
    return getLmsApiUriPath("/api/course/v1/batch/update", "/v1/course/batch/update");
  }

  @DataProvider(name = "updateCourseBatchDataFailureProvider")
  public Object[][] updateCourseBatchDataFailureProvider() {
    return new Object[][] {
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITHOUT_AUTH_TOKEN,
        false,
        HttpStatus.UNAUTHORIZED,
        "",
        "",
        "",
        ""
      },
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_INVALID_ID,
        true,
        HttpStatus.BAD_REQUEST,
        FUTURE_START_DATE,
        "",
        "",
        ""
      },
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_START_DATE_AND_COURSE_BATCH_STARTED,
        true,
        HttpStatus.BAD_REQUEST,
        PAST_START_DATE,
        "",
        Id_COURSE_BATCH_STARTED,
        NAME_COURSE_BATCH_STARTED
      },
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_END_DATE_AND_COURSE_BATCH_STARTED,
        true,
        HttpStatus.BAD_REQUEST,
        "",
        FUTURE_END_DATE,
        Id_COURSE_BATCH_STARTED,
        NAME_COURSE_BATCH_STARTED
      },
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_DIFFERENT_START_DATE_AND_END_DATE_AND_COURSE_BATCH_STARTED,
        true,
        HttpStatus.BAD_REQUEST,
        PAST_START_DATE,
        FUTURE_END_DATE,
        Id_COURSE_BATCH_STARTED,
        NAME_COURSE_BATCH_STARTED
      },
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_FUTURE_END_DATE_AND_COURSE_BATCH_NOT_STARTED,
        true,
        HttpStatus.BAD_REQUEST,
        "",
        FUTURE_END_DATE,
        Id_COURSE_BATCH_NOT_STARTED,
        NAME_COURSE_BATCH_NOT_STARTED
      },
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_PAST_START_DATE_AND_COURSE_BATCH_NOT_STARTED,
        true,
        HttpStatus.BAD_REQUEST,
        PAST_START_DATE,
        "",
        Id_COURSE_BATCH_NOT_STARTED,
        NAME_COURSE_BATCH_NOT_STARTED
      },
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_PAST_END_DATE_AND_COURSE_BATCH_NOT_STARTED,
        true,
        HttpStatus.BAD_REQUEST,
        "",
        PAST_END_DATE,
        Id_COURSE_BATCH_NOT_STARTED,
        NAME_COURSE_BATCH_NOT_STARTED
      },
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_END_DATE_BEFORE_FUTURE_START_DATE_AND_COURSE_BATCH_NOT_STARTED,
        true,
        HttpStatus.BAD_REQUEST,
        FUTURE_START_DATE,
        FUTURE_BEFORE_END_DATE,
        Id_COURSE_BATCH_NOT_STARTED,
        NAME_COURSE_BATCH_NOT_STARTED
      },
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_START_DATE_AND_COURSE_BATCH_COMPLETED,
        true,
        HttpStatus.BAD_REQUEST,
        FUTURE_START_DATE,
        "",
        Id_COURSE_BATCH_COMPLETED,
        NAME_COURSE_BATCH_COMPLETED
      },
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_END_DATE_AND_COURSE_BATCH_COMPLETED,
        true,
        HttpStatus.BAD_REQUEST,
        "",
        FUTURE_END_DATE,
        Id_COURSE_BATCH_COMPLETED,
        NAME_COURSE_BATCH_COMPLETED
      },
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_FAILURE_WITH_START_DATE_AND_END_DATE_AND_COURSE_BATCH_COMPLETED,
        true,
        HttpStatus.BAD_REQUEST,
        FUTURE_START_DATE,
        FUTURE_END_DATE,
        Id_COURSE_BATCH_COMPLETED,
        NAME_COURSE_BATCH_COMPLETED
      }
    };
  }

  @Test(dataProvider = "updateCourseBatchDataFailureProvider")
  @CitrusParameters({
    "testName",
    "isAuthRequired",
    "httpStatusCode",
    "startDate",
    "endDate",
    "id",
    "name"
  })
  @CitrusTest
  public void testUpdateCourseBatchFailure(
      String testName,
      boolean isAuthRequired,
      HttpStatus httpStatusCode,
      String startDate,
      String endDate,
      String id,
      String name) {
    getTestCase().setName(testName);
    getAuthToken(this, isAuthRequired);
    variable("id", id);
    variable("name", name);
    variable("startDate", startDate);
    variable("endDate", endDate);

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

  @DataProvider(name = "updateCourseBatchDataSuccessProvider")
  public Object[][] updateCourseBatchDataSuccessProvider() {
    return new Object[][] {
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_SUCCESS_WITH_FUTURE_START_DATE_AND_COURSE_BATCH_NOT_STARTED,
        true,
        HttpStatus.OK,
        FUTURE_START_DATE,
        "",
        Id_COURSE_BATCH_NOT_STARTED,
        NAME_COURSE_BATCH_NOT_STARTED
      },
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_SUCCESS_WITH_FUTURE_START_DATE_AND_END_DATE_AND_COURSE_BATCH_NOT_STARTED,
        true,
        HttpStatus.OK,
        FUTURE_START_DATE,
        FUTURE_END_DATE,
        Id_COURSE_BATCH_NOT_STARTED,
        NAME_COURSE_BATCH_NOT_STARTED
      },
      new Object[] {
        TEST_NAME_UPDATE_COURSE_BATCH_SUCCESS_WITH_SAME_START_DATE_AND_END_DATE_AND_COURSE_BATCH_STARTED,
        true,
        HttpStatus.OK,
        SAME_START_DATE,
        SAME_END_DATE,
        Id_COURSE_BATCH_STARTED,
        NAME_COURSE_BATCH_STARTED
      }
    };
  }

  @Test(dataProvider = "updateCourseBatchDataSuccessProvider")
  @CitrusParameters({
    "testName",
    "isAuthRequired",
    "httpStatusCode",
    "startDate",
    "endDate",
    "id",
    "name"
  })
  @CitrusTest
  public void testUpdateCourseBatchSuccess(
      String testName,
      boolean isAuthRequired,
      HttpStatus httpStatusCode,
      String startDate,
      String endDate,
      String id,
      String name) {
    getTestCase().setName(testName);
    getAuthToken(this, isAuthRequired);
    variable("id", id);
    variable("name", name);
    variable("startDate", startDate);
    variable("endDate", endDate);

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
}
