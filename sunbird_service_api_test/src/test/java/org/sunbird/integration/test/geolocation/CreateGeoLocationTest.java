package org.sunbird.integration.test.geolocation;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateGeoLocationTest extends BaseCitrusTestRunner {

  public static final String TEST_CREATE_GEOLOCATION_FAILURE_WITHOUT_ACCESS_TOKEN =
      "testCreateGeolocationFailureWithoutAccessToken";
  public static final String TEST_CREATE_GEOLOCATION_FAILURE_WITH_INVALID_ROOT_ORGID =
      "testCreateGeolocationFailureWithInvalidRootOrgId";
  public static final String TEST_CREATE_GEOLOCATION_FAILURE_WITHOUT_DATA_OBJECT =
      "testCreateGeolocationFailureWithoutDataObject";
  public static final String TEST_CREATE_GEOLOCATION_FAILURE_WITH_EMPTY_DATA_ARRAY =
      "testCreateGeolocationFailureWithEmptyDataArray";
  public static final String TEST_CREATE_GEOLOCATION_SUCCESS_WITH_EMPTY_OBJECT_IN_DATA_ARRAY =
      "testCreateGeolocationSuccessWithEmptyObjectInDataArray";
  public static final String TEST_CREATE_GEOLOCATION_SUCCESS = "testCreateGeolocationSuccess";
  public static final String TEMPLATE_DIR = "templates/geolocation/create";

  private String getCreateGeoLocationUrl() {
    return getLmsApiUriPath("/api/org/v1/location/create", "/v1/notification/location/create");
  }

  @DataProvider(name = "createGeoLocationFailureDataProvider")
  public Object[][] createGeoLocationFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_CREATE_GEOLOCATION_FAILURE_WITHOUT_ACCESS_TOKEN, false, HttpStatus.UNAUTHORIZED
      },
      new Object[] {
        TEST_CREATE_GEOLOCATION_FAILURE_WITH_INVALID_ROOT_ORGID, true, HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_CREATE_GEOLOCATION_FAILURE_WITHOUT_DATA_OBJECT, true, HttpStatus.INTERNAL_SERVER_ERROR
      },
      new Object[] {
        TEST_CREATE_GEOLOCATION_FAILURE_WITH_EMPTY_DATA_ARRAY, true, HttpStatus.BAD_REQUEST
      }
    };
  }

  @DataProvider(name = "createGeoLocationSuccessDataProvider")
  public Object[][] createGeoLocationSuccessDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_CREATE_GEOLOCATION_SUCCESS_WITH_EMPTY_OBJECT_IN_DATA_ARRAY, true, HttpStatus.OK
      },
      new Object[] {TEST_CREATE_GEOLOCATION_SUCCESS, true, HttpStatus.OK},
    };
  }

  @Test(dataProvider = "createGeoLocationFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testCreateGeolocationFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest();
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getCreateGeoLocationUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "createGeoLocationSuccessDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testCreateGeolocationSuccess(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest();
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getCreateGeoLocationUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  void beforeTest() {
    getAuthToken(this, true);
    variable("rootOrgChannel", OrgUtil.getRootOrgChannel());
    OrgUtil.getRootOrgId(this, testContext);
    variable("organisationId", testContext.getVariable("organisationId"));
  }
}
