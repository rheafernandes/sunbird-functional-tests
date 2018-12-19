package org.sunbird.integration.test.otp;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OtpVerificationTest extends BaseCitrusTestRunner {

  public static final String TEST_VERIFY_OTP_WITHOUT_PHONE_KEY_FAILURE =
      "testVerifyOtpWithoutPhoneKeyFailure";

  public static final String TEST_VERIFY_OTP_WITH_INVALID_PHONE_FAILURE =
      "testVerifyOtpWithInvalidPhoneFailure";

  public static final String TEST_VERIFY_OTP_WITHOUT_EMAIL_KEY_FAILURE =
      "testVerifyOtpWithoutEmailKeyFailure";

  public static final String TEST_VERIFY_OTP_WITH_INVALID_EMAIL_FAILURE =
      "testVerifyOtpWithInvalidEmailFailure";

  public static final String TEST_VERIFY_OTP_WITHOUT_OTP_FAILURE = "testVerifyOtpWithoutOtpFailure";

  public static final String TEST_VERIFY_OTP_WITH_INVALID_TYPE_FAILURE =
      "testVerifyOtpWithInvalidTypeFailure";

  public static final String TEST_VERIFY_OTP_WITHOUT_TYPE_FAILURE =
      "testVerifyOtpWithoutTypeFailure";
  public static final String TEMPLATE_DIR = "templates/otp/verify";

  private String getOtpVerifyUrl() {
    return getLmsApiUriPath("/api/otp/v1/verify", "v1/otp/verify");
  }

  @DataProvider(name = "otpVerifyFailureDataProvider")
  public Object[][] otpVerifyFailureDataProvider() {
    return new Object[][] {
      new Object[] {TEST_VERIFY_OTP_WITHOUT_PHONE_KEY_FAILURE},
      new Object[] {TEST_VERIFY_OTP_WITH_INVALID_PHONE_FAILURE},
      new Object[] {TEST_VERIFY_OTP_WITHOUT_EMAIL_KEY_FAILURE},
      new Object[] {TEST_VERIFY_OTP_WITH_INVALID_EMAIL_FAILURE},
      new Object[] {TEST_VERIFY_OTP_WITHOUT_OTP_FAILURE},
      new Object[] {TEST_VERIFY_OTP_WITH_INVALID_TYPE_FAILURE},
      new Object[] {TEST_VERIFY_OTP_WITHOUT_TYPE_FAILURE},
    };
  }

  @Test(dataProvider = "otpVerifyFailureDataProvider")
  @CitrusParameters({"testName"})
  @CitrusTest
  public void testAddUserToOrgFailure(String testName) {
    getAuthToken(this, true);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getOtpVerifyUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        HttpStatus.BAD_REQUEST,
        RESPONSE_JSON);
  }
}
