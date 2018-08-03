package org.sunbird.integration.test.user.notification.email;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SendEmailNotificationTest extends BaseCitrusTestRunner {

  public static final String TEST_NAME_SEND_EMAIL_NOTIFICATION_FAILURE_WITHOUT_SUBJECT =
      "testSendEmailNotificationFailureWithoutSubject";
  public static final String TEST_NAME_SEND_EMAIL_NOTIFICATION_FAILURE_WITHOUT_BODY =
	      "testSendEmailNotificationFailureWithoutBody";
  public static final String TEST_NAME_SEND_EMAIL_NOTIFICATION_FAILURE_WITHOUT_RECIPIENT =
	      "testSendEmailNotificationFailureWithoutRecipient";
  
  

  public static final String TEMPLATE_DIR = "templates/user/notification/email";

  private String getSendEmailUrl() {
    return getLmsApiUriPath("/api/course/v1/notification/email", "/v1/notification/email");
  }

  @DataProvider(name = "sendMailFailureDataProvider")
  public Object[][] sendMailFailureDataProvider() {

    return new Object[][] {
      new Object[] {TEST_NAME_SEND_EMAIL_NOTIFICATION_FAILURE_WITHOUT_SUBJECT,HttpStatus.BAD_REQUEST}, 
      new Object[] {TEST_NAME_SEND_EMAIL_NOTIFICATION_FAILURE_WITHOUT_BODY, HttpStatus.BAD_REQUEST}, 
      new Object[] {TEST_NAME_SEND_EMAIL_NOTIFICATION_FAILURE_WITHOUT_RECIPIENT, HttpStatus.BAD_REQUEST}, 
    };
  }

  @Test(dataProvider = "sendMailFailureDataProvider")
  @CitrusParameters({"testName", "httpStatusCode"})
  @CitrusTest
  public void testSendMailFailure(
      String testName, HttpStatus httpStatusCode) {
   
	  getTestCase().setName(testName);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getSendEmailUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        httpStatusCode,
        RESPONSE_JSON);
  }

}
