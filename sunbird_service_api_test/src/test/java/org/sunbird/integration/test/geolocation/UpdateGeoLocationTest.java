package org.sunbird.integration.test.geolocation;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.GeoLocationUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UpdateGeoLocationTest extends BaseCitrusTestRunner {
  public static final String TEST_UPDATE_GEOLOCATION_FAILURE_WITHOUT_ACCESS_TOKEN =
      "testUpdateGeolocationFailureWithoutAccessToken";
  public static final String TEST_UPDATE_GEOLOCATION_FAILURE_WITH_INVALID_LOCATIONID =
      "testUpdateGeolocationFailureWithInvalidLocationId";
  public static final String TEST_UPDATE_GEOLOCATION_SUCCESS = "testUpdateGeolocationSuccess";
  public static final String TEMPLATE_DIR = "templates/geolocation/update";

  private String getUpdateGeoLocationUrl(String pathParam) {
    return getLmsApiUriPath(
        "/api/org/v1/location/update", "/v1/notification/location/update", pathParam);
  }

  @DataProvider(name = "updateGeoLocationFailureDataProvider")
  public Object[][] updateGeoLocationFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_UPDATE_GEOLOCATION_FAILURE_WITHOUT_ACCESS_TOKEN, false, HttpStatus.UNAUTHORIZED
      },
      new Object[] {
        TEST_UPDATE_GEOLOCATION_FAILURE_WITH_INVALID_LOCATIONID, true, HttpStatus.BAD_REQUEST
      }
    };
  }

  @DataProvider(name = "updateGeoLocationSuccessDataProvider")
  public Object[][] updateGeoLocationSuccessDataProvider() {

    return new Object[][] {new Object[] {TEST_UPDATE_GEOLOCATION_SUCCESS, true, HttpStatus.OK}};
  }

  @Test(dataProvider = "updateGeoLocationFailureDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testUpdateGeolocationFailure(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest();
    performPatchTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUpdateGeoLocationUrl("InvalidLocationId"),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @Test(dataProvider = "updateGeoLocationSuccessDataProvider")
  @CitrusParameters({"testName", "isAuthRequired", "httpStatusCode"})
  @CitrusTest
  public void testUpdateGeolocationSuccess(
      String testName, boolean isAuthRequired, HttpStatus httpStatusCode) {
    getTestCase().setName(testName);
    beforeTest();
    performPatchTest(
        this,
        TEMPLATE_DIR,
        testName,
        getUpdateGeoLocationUrl(testContext.getVariable("geolocationId")),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        isAuthRequired,
        httpStatusCode,
        RESPONSE_JSON);
  }

  void beforeTest() {
    GeoLocationUtil.getGeoLocationId(this, testContext);
  }
}
