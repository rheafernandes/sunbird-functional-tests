package org.sunbird.integration.test.badge;

import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;

public class CreateBadgeAssertionTest extends BaseCitrusTestRunner{

	  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_USER_WITHOUT_EVIDENCE =
	      "testCreateBadgeAssertionClassSuccessUserWithoutEvidence";
	  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_USER_WITH_EVIDENCE =
		      "testCreateBadgeAssertionClassSuccessUserWithEvidence";
	  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_TEXTBOOK_WITHOUT_EVIDENCE =
		      "testCreateBadgeAssertionClassSuccessTextbookWithoutEvidence";
	  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_TEXTBOOK_WITH_EVIDENCE =
			      "testCreateBadgeAssertionClassSuccessTextbookWithEvidence";
	  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_COURSE_WITHOUT_EVIDENCE =
		      "testCreateBadgeAssertionClassSuccessCourseWithoutEvidence";
	  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_COUURSE_WITH_EVIDENCE =
			      "testCreateBadgeAssertionClassSuccessCourseWithEvidence";

	  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITHOUT_ISSUER_ID =
	      "testCreateBadgeAssertionClassFailureWithoutIssuerId";
	  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITHOUT_BADGE_ID =
	      "testCreateBadgeAssertionClassFailureWithoutBadgeId";
	  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITHOUT_RECIPIENT_ID =
	      "testCreateBadgeAssertionClassFailureWithoutRecipientId";
	  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITHOUT_RECIPIENT_TYPE =
	      "testCreateBadgeAssertionClassFailureWithoutRecipientType";
	  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_RECIPIENT_TYPE =
	      "testCreateBadgeAssertionClassFailureWithInvalidRecipientType";
	  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_ISSUER_ID =
	      "testCreateBadgeAssertionClassFailureWithInvalidIssuerId";
	  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_BADGE_ID =
	      "testCreateBadgeAssertionClassFailureWithInvalidBadgeId";
	  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_USER_ID =
		      "testCreateBadgeAssertionClassFailureWithInvalidUserId";
	  private static final String TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_CONTENT_ID =
		      "testCreateBadgeAssertionClassFailureWithInvalidContentId";

	  private static final String TEMPLATE_DIR = "templates/badge/assertion/create";
	  
	  private String getCreateBadgeAssertionUrl() {
		    return getLmsApiUriPath("/api/badging/v1/issuer/badge/assertion/create", "/v1/issuer/badge/assertion/create");
		  }

	  @DataProvider(name = "createBadgeAssertionDataProviderSuccess")
	  public Object[][] createBadgeAssertionDataProviderSuccess() {
	    return new Object[][] {
	      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_USER_WITHOUT_EVIDENCE},
	      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_USER_WITH_EVIDENCE},
	      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_TEXTBOOK_WITHOUT_EVIDENCE},
	      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_TEXTBOOK_WITH_EVIDENCE},
	      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_COURSE_WITHOUT_EVIDENCE},
	      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_SUCCESS_COUURSE_WITH_EVIDENCE }
	    };
	  }
	  
	  
	  @DataProvider(name = "createBadgeAssertionDataProviderFailure")
	  public Object[][] createBadgeAssertionDataProviderFailure() {
	    return new Object[][] {
	      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITHOUT_ISSUER_ID, false},
	      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITHOUT_BADGE_ID, false},
	      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITHOUT_RECIPIENT_ID, false},
	      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITHOUT_RECIPIENT_TYPE, false},
	      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_RECIPIENT_TYPE, false},
	      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_ISSUER_ID, false},
	      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_BADGE_ID, false},
	      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_USER_ID, false},
	      new Object[] {TEST_NAME_CREATE_BADGE_ASSERTION_FAILURE_WITH_INVALID_CONTENT_ID, false}
	    };
	  }
	  
	  @Test(dataProvider = "createBadgeAssertionDataProviderSuccess")
	  @CitrusParameters({"testName"})
	  @CitrusTest
	  public void testCreateBadgeAssertionSuccess(String testName) {
	    performMultipartTest(
	        this,
	        TEMPLATE_DIR,
	        testName,
	        getCreateBadgeAssertionUrl(),
	        REQUEST_FORM_DATA,
	        null,
	        false,
	        HttpStatus.OK,
	        RESPONSE_JSON);
	    
	  }
	  
	  @Test(dataProvider = "createBadgeAssertionDataProviderFailure")
	  @CitrusParameters({"testName"})
	  @CitrusTest
	  public void testCreateBadgeAssertionFailure(String testName) {
	    performMultipartTest(
	        this,
	        TEMPLATE_DIR,
	        testName,
	        getCreateBadgeAssertionUrl(),
	        REQUEST_FORM_DATA,
	        null,
	        false,
	        HttpStatus.BAD_REQUEST,
	        RESPONSE_JSON);
	  }
	  
	  
}
