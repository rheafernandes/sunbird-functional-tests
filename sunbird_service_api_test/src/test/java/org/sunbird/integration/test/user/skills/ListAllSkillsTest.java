package org.sunbird.integration.test.user.skills;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ListAllSkillsTest extends BaseCitrusTestRunner {

  public static final String TEST_NAME_LIST_ALL_SKILLS_FAILURE_WITHOUT_ACCESS_TOKEN =
      "testListAllSkillsFailureWithoutAccessToken";
  public static final String TEST_NAME_LIST_ALL_SKILLS_SUCCESS = "testListAllSkillsSuccess";

  public static final String TEMPLATE_DIR = "templates/user/skill/listAll";

  private String getListAllSkillUrl() {
    return getLmsApiUriPath("/api/data/v1/skills", "/v1/skills");
  }

  @DataProvider(name = "listAllSkillsFailureDataProvider")
  public Object[][] listAllSkillsFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_NAME_LIST_ALL_SKILLS_FAILURE_WITHOUT_ACCESS_TOKEN, false, HttpStatus.UNAUTHORIZED
      }
    };
  }

  @Test(dataProvider = "listAllSkillsFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void listAllSkillsFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    getAuthToken(this, isAuthRequired);
    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getListAllSkillUrl(),
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @DataProvider(name = "listAllSkillsSuccessDataProvider")
  public Object[][] listAllSkillsSuccessDataProvider() {

    return new Object[][] {new Object[] {TEST_NAME_LIST_ALL_SKILLS_SUCCESS, true, HttpStatus.OK}};
  }

  @Test(dataProvider = "listAllSkillsSuccessDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void listAllSkillsSuccess(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    getAuthToken(this, isAuthRequired);
    performGetTest(
        this,
        TEMPLATE_DIR,
        testName,
        getListAllSkillUrl(),
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }
}
