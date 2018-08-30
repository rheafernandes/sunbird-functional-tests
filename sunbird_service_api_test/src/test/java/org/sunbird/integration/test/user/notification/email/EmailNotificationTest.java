package org.sunbird.integration.test.user.notification.email;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EmailNotificationTest extends BaseCitrusTestRunner {

  public static final String TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITHOUT_SUBJECT =
      "testEmailNotificationFailureWithoutSubject";
  public static final String TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITHOUT_BODY =
      "testEmailNotificationFailureWithoutBody";
  public static final String TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITHOUT_RECIPIENT_EMAILS =
      "testEmailNotificationFailureWithoutRecipientEmails";
  public static final String TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITH_INCORRECT_EMAIL_TEMPLATE =
      "testEmailNotificationFailureWithIncorrectEmailTemplate";
  public static final String
      TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITH_EMPTY_RECIPIENT_SEARCH_QUERY =
          "testEmailNotificationFailureWithEmptyRecipientSearchQuery";
  public static final String
      TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITH_RECIPIENTS_EXCEEDS_THRESHOLD =
          "testEmailNotificationFailureWithRecipientsExceedsThreshold";
  public static final String TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITHOUT_RECIPIENT_DETAILS =
      "testEmailNotificationFailureWithoutRecipientDetails";

  public static final String TEST_NAME_EMAIL_NOTIFICATION_SUCCESS_WITH_VALID_EMAIL_TEMPLATE =
      "testEmailNotificationSuccessWithValidEmailTemplate";
  public static final String TEST_NAME_EMAIL_NOTIFICATION_SUCCESS_WITHOUT_EMAIL_TEMPLATE =
      "testEmailNotificationSuccessWithoutEmailTemplate";
  public static final String TEST_NAME_EMAIL_NOTIFICATION_SUCCESS_WITHOUT_NAME =
      "testEmailNotificationSuccessWithoutName";
  public static final String TEST_NAME_EMAIL_NOTIFICATION_SUCCESS_WITH_MULTIPLE_RECIPIENT_EMAILS =
      "testEmailNotificationSuccessWithMultipleRecipientEmails";

  public static final String TEMPLATE_DIR = "templates/user/notification/email";

  private String getSendEmailUrl() {
    return getLmsApiUriPath("/api/user/v1/notification/email", "/v1/notification/email");
  }

  @DataProvider(name = "emailNotificationFailureDataProvider")
  public Object[][] emailNotificationFailureDataProvider() {

    return new Object[][] {
      new Object[] {TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITHOUT_SUBJECT},
      new Object[] {TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITHOUT_BODY},
      new Object[] {TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITHOUT_RECIPIENT_EMAILS},
      new Object[] {TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITH_INCORRECT_EMAIL_TEMPLATE},
      new Object[] {TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITH_EMPTY_RECIPIENT_SEARCH_QUERY},
      new Object[] {TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITH_RECIPIENTS_EXCEEDS_THRESHOLD},
      new Object[] {TEST_NAME_EMAIL_NOTIFICATION_FAILURE_WITHOUT_RECIPIENT_DETAILS}
    };
  }

  @DataProvider(name = "emailNotificationSuccessDataProvider")
  public Object[][] emailNotificationSuccessDataProvider() {

    return new Object[][] {
      new Object[] {TEST_NAME_EMAIL_NOTIFICATION_SUCCESS_WITH_VALID_EMAIL_TEMPLATE},
      new Object[] {TEST_NAME_EMAIL_NOTIFICATION_SUCCESS_WITHOUT_EMAIL_TEMPLATE},
      new Object[] {TEST_NAME_EMAIL_NOTIFICATION_SUCCESS_WITHOUT_NAME},
      new Object[] {TEST_NAME_EMAIL_NOTIFICATION_SUCCESS_WITH_MULTIPLE_RECIPIENT_EMAILS}
    };
  }

  @Test(dataProvider = "emailNotificationFailureDataProvider")
  @CitrusParameters({"testName"})
  @CitrusTest
  public void testEmailNotificationFailure(String testName) {
    beforeTest(testName);

    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getSendEmailUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        HttpStatus.BAD_REQUEST,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "emailNotificationSuccessDataProvider")
  @CitrusParameters({"testName"})
  @CitrusTest
  public void testEmailNotificationSuccess(String testName) {
    beforeTest(testName);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getSendEmailUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        HttpStatus.OK,
        RESPONSE_JSON);
  }

  public void beforeTest(String testName) {
    getTestCase().setName(testName);
    getAuthToken(this, true);
  }
}
