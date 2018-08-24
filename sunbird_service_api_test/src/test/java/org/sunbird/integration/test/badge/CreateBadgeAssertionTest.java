package org.sunbird.integration.test.badge;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.BadgeClassUtil;
import org.sunbird.common.action.IssuerUtil;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.common.action.UserUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateBadgeAssertionTest extends BaseCitrusTestRunner {

  private static final String BT_TEST_NAME_CREATE_ISSUER_SUCCESS = "testCreateIssuerSuccess";
  private static final String BT_CREATE_ISSUER_TEMPLATE_DIR = "templates/badge/issuer/create";

  private static final String BT_TEST_NAME_CREATE_BADGE_CLASS_SUCCESS =
      "testCreateBadgeClassSuccessWithTypeUser";
  private static final String BT_CREATE_BADGE_CLASS_TEMPLATE_DIR = "templates/badge/class/create";

  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_USER_WITHOUT_EVIDENCE =
      "testCreateBadgeAssertionSuccessUserWithoutEvidence";
  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_USER_WITH_EVIDENCE =
      "testCreateBadgeAssertionSuccessUserWithEvidence";
  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_TEXTBOOK_WITHOUT_EVIDENCE =
      "testCreateBadgeAssertionSuccessTextbookWithoutEvidence";
  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_TEXTBOOK_WITH_EVIDENCE =
      "testCreateBadgeAssertionSuccessTextbookWithEvidence";
  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_COURSE_WITHOUT_EVIDENCE =
      "testCreateBadgeAssertionSuccessCourseWithoutEvidence";
  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_COUURSE_WITH_EVIDENCE =
      "testCreateBadgeAssertionSuccessCourseWithEvidence";

  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITHOUT_ISSUER_ID =
      "testCreateBadgeAssertionFailureWithoutIssuerId";
  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITHOUT_BADGE_ID =
      "testCreateBadgeAssertionFailureWithoutBadgeId";
  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITHOUT_RECIPIENT_ID =
      "testCreateBadgeAssertionFailureWithoutRecipientId";
  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITHOUT_RECIPIENT_TYPE =
      "testCreateBadgeAssertionFailureWithoutRecipientType";
  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_RECIPIENT_TYPE =
      "testCreateBadgeAssertionFailureWithInvalidRecipientType";
  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_ISSUER_ID =
      "testCreateBadgeAssertionFailureWithInvalidIssuerId";
  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_BADGE_ID =
      "testCreateBadgeAssertionFailureWithInvalidBadgeId";
  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_USER_ID =
      "testCreateBadgeAssertionFailureWithInvalidUserId";
  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_CONTENT_ID =
      "testCreateBadgeAssertionFailureWithInvalidContentId";

  private static final String TEMPLATE_DIR = "templates/badge/assertion/create";

  private String getCreateBadgeAssertionUrl() {
    return getLmsApiUriPath(
        "/api/badging/v1/issuer/badge/assertion/create", "/v1/issuer/badge/assertion/create");
  }

  @DataProvider(name = "createBadgeAssertionDataProviderSuccess")
  public Object[][] createBadgeAssertionDataProviderSuccess() {
    return new Object[][] {
      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_USER_WITHOUT_EVIDENCE},
      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_USER_WITH_EVIDENCE},
      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_TEXTBOOK_WITHOUT_EVIDENCE},
      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_TEXTBOOK_WITH_EVIDENCE},
      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_COURSE_WITHOUT_EVIDENCE},
      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_COUURSE_WITH_EVIDENCE}
    };
  }

  @DataProvider(name = "createBadgeAssertionDataProviderFailure")
  public Object[][] createBadgeAssertionDataProviderFailure() {
    return new Object[][] {
      new Object[] {
        TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITHOUT_ISSUER_ID,
        HttpStatus.BAD_REQUEST,
        false,
        false,
        false
      },
      new Object[] {
        TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITHOUT_BADGE_ID,
        HttpStatus.BAD_REQUEST,
        false,
        false,
        false
      },
      new Object[] {
        TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITHOUT_RECIPIENT_ID,
        HttpStatus.BAD_REQUEST,
        false,
        false,
        false
      },
      new Object[] {
        TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITHOUT_RECIPIENT_TYPE,
        HttpStatus.BAD_REQUEST,
        false,
        false,
        false
      },
      new Object[] {
        TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_RECIPIENT_TYPE,
        HttpStatus.BAD_REQUEST,
        false,
        false,
        false
      },
      new Object[] {
        TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_USER_ID,
        HttpStatus.NOT_FOUND,
        false,
        false,
        false
      },
      new Object[] {
        TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_ISSUER_ID,
        HttpStatus.NOT_FOUND,
        true,
        false,
        false
      },
      new Object[] {
        TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_BADGE_ID,
        HttpStatus.NOT_FOUND,
        true,
        true,
        false
      },
      new Object[] {
        TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_CONTENT_ID,
        HttpStatus.NOT_FOUND,
        false,
        false,
        false
      }
    };
  }

  @Test(dataProvider = "createBadgeAssertionDataProviderSuccess")
  @CitrusParameters({"testName"})
  @CitrusTest
  public void testCreateBadgeAssertionSuccess(String testName) {
    beforeTest(testName, true, true, true);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getCreateBadgeAssertionUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        HttpStatus.OK,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "createBadgeAssertionDataProviderFailure")
  @CitrusParameters({
    "testName",
    "httpStatus",
    "canCreateUser",
    "canCreateIssuer",
    "canCreateBadgeClass"
  })
  @CitrusTest
  public void testCreateBadgeAssertionFailure(
      String testName,
      HttpStatus httpStatus,
      boolean canCreateUser,
      boolean canCreateIssuer,
      boolean canCreateBadgeClass) {
    beforeTest(testName, canCreateUser, canCreateIssuer, canCreateBadgeClass);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getCreateBadgeAssertionUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        false,
        httpStatus,
        RESPONSE_JSON);
  }

  private void beforeTest(
      String testName, Boolean canCreateUser, Boolean canCreateIssuer, Boolean canCreateBadge) {

    getTestCase().setName(testName);
    if (canCreateUser) {
      getAuthToken(this, true);
      UserUtil.getUserId(this, testContext);
    }

    if (canCreateIssuer) {
      IssuerUtil.createIssuer(
          this,
          testContext,
          config,
          BT_CREATE_ISSUER_TEMPLATE_DIR,
          BT_TEST_NAME_CREATE_ISSUER_SUCCESS,
          HttpStatus.OK);
    }

    if (canCreateBadge) {
      String orgId =
          OrgUtil.getSearchOrgId(this, testContext, System.getenv("sunbird_default_channel"));
      variable("organisationId", orgId);
      BadgeClassUtil.createBadgeClass(
          this,
          testContext,
          config,
          BT_CREATE_BADGE_CLASS_TEMPLATE_DIR,
          BT_TEST_NAME_CREATE_BADGE_CLASS_SUCCESS,
          HttpStatus.OK);
    }
  }
}
