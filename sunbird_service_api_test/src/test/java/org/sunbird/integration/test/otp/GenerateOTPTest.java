/** */
package org.sunbird.integration.test.otp;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/** @author Rahul Kumar */
public class GenerateOTPTest extends BaseCitrusTestRunner {

  public static final String GENERATE_OTP_FOR_INVALID_KEY_FAILURE =
      "generateOTPForInvalidKeyFailure";
  public static final String GENERATE_OTP_FOR_MISSING_TYPE_FAILURE =
      "generateOTPForMissingTypeFailure";
  public static final String GENERATE_OTP_FOR_INVALID_TYPE_FAILURE =
      "generateOTPForInvalidTypeFailure";
  public static final String GENERATE_OTP_FOR_INVALID_EMAIL_FAILURE =
      "generateOTPForInvalidEmailFailure";
  public static final String GENERATE_OTP_FOR_INVALID_PHONE_FAILURE =
      "generateOTPForInvalidPhoneFailure";
  public static final String GENERATE_OTP_FOR_EMAIL_SUCCESS = "generateOTPForEmailSuccess";
  public static final String GENERATE_OTP_FOR_PHONE_SUCCESS = "generateOTPForPhoneSuccess";

  public static final String TEMPLATE_DIR = "templates/otp/generate";

  private String getOTPGenerateUrl() {
    return getLmsApiUriPath("/api/v1/otp/generate", "v1/otp/generate");
  }

  @DataProvider(name = "generateOTPFailureDataProvider")
  public Object[][] generateOTPFailureDataProvider() {
    return new Object[][] {
      new Object[] {GENERATE_OTP_FOR_INVALID_KEY_FAILURE, HttpStatus.BAD_REQUEST},
      new Object[] {GENERATE_OTP_FOR_MISSING_TYPE_FAILURE, HttpStatus.BAD_REQUEST},
      new Object[] {GENERATE_OTP_FOR_INVALID_TYPE_FAILURE, HttpStatus.BAD_REQUEST},
      new Object[] {GENERATE_OTP_FOR_INVALID_EMAIL_FAILURE, HttpStatus.BAD_REQUEST},
      new Object[] {GENERATE_OTP_FOR_INVALID_PHONE_FAILURE, HttpStatus.BAD_REQUEST}
    };
  }

  @Test(dataProvider = "generateOTPFailureDataProvider")
  @CitrusParameters({"testName", "httpStatusCode"})
  @CitrusTest
  public void testGenerateOTPFailure(String testName, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getOTPGenerateUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @DataProvider(name = "generateOTPSuccessDataProvider")
  public Object[][] generateOTPSuccessDataProvider() {
    return new Object[][] {
      new Object[] {GENERATE_OTP_FOR_EMAIL_SUCCESS, HttpStatus.OK},
      new Object[] {GENERATE_OTP_FOR_PHONE_SUCCESS, HttpStatus.OK}
    };
  }

  @Test(dataProvider = "generateOTPSuccessDataProvider")
  @CitrusParameters({"testName", "httpStatusCode"})
  @CitrusTest
  public void testGenerateOTPSuccess(String testName, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getOTPGenerateUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        httpStatusCode,
        RESPONSE_JSON);
  }
}
