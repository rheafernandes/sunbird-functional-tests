package org.sunbird.common.action;

import com.consol.citrus.context.TestContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;

public class GeoLocationUtil {

  public static final String TEMPLATE_DIR = "templates/geolocation/create";
  public static final String TEST_CREATE_GEOLOCATION_SUCCESS = "testCreateGeolocationSuccess";

  private static String getCreateGeolocationUrl(BaseCitrusTestRunner runner) {
    return runner.getLmsApiUriPath(
        "/api/org/v1/location/create", "/v1/notification/location/create");
  }

  public static void createGeolocaiton(
      BaseCitrusTestRunner runner, TestContext testContext, String templateDir, String testName) {
    runner.http(
        builder ->
            TestActionUtil.getPostRequestTestAction(
                builder,
                Constant.LMS_ENDPOINT,
                templateDir,
                testName,
                getCreateGeolocationUrl(runner),
                Constant.REQUEST_JSON,
                MediaType.APPLICATION_JSON.toString(),
                TestActionUtil.getHeaders(true)));

    runner.http(
        builder ->
            TestActionUtil.getExtractFromResponseTestAction(
                testContext,
                builder,
                Constant.LMS_ENDPOINT,
                HttpStatus.OK,
                "$.result.response[0].id",
                "geolocationId"));
    runner.sleep(Constant.ES_SYNC_WAIT_TIME);
  }

  public static void getGeoLocationId(BaseCitrusTestRunner runner, TestContext testContext) {
    runner.getAuthToken(runner, true);
    runner.variable("rootOrgChannel", OrgUtil.getRootOrgChannel());
    OrgUtil.getRootOrgId(runner, testContext);
    runner.variable("organisationId", testContext.getVariable("organisationId"));
    createGeolocaiton(runner, testContext, TEMPLATE_DIR, TEST_CREATE_GEOLOCATION_SUCCESS);
  }
}
