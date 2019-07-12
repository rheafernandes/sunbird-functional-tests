package org.sunbird.kp.test.content.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.util.ContentUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Content V3 API Tests
 *
 * @author Kumar Gauraw
 */
public class ContentV3ApiTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3";

    @Test(dataProvider = "createResourceContentWithValidRequest")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType","valParams"})
    @CitrusTest
    public void testCreateResourceContentWithValidRequest(
            String testName, String requestUrl, HttpStatus httpStatusCode, String userType, Map<String, Object> valParams) {
        getAuthToken(this, userType);
        performPostTest(
                this,
                TEMPLATE_DIR,
                testName,
                requestUrl,
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                httpStatusCode,
                valParams,
                RESPONSE_JSON
        );
    }

    @Test(dataProvider = "readResourceContent")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType","valParams","mimeType"})
    @CitrusTest
    public void testReadResourceContent(
            String testName, String requestUrl, HttpStatus httpStatusCode, String userType, Map<String, Object> valParams, String mimeType) {
        getAuthToken(this, userType);
        String contentId = (String)ContentUtil.createResourceContent(this, null, mimeType, null).get("content_id");
        performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                requestUrl + contentId,
                null,
                httpStatusCode,
                valParams,
                RESPONSE_JSON
        );
    }


    @DataProvider(name = "createResourceContentWithValidRequest")
    public Object[][] createResourceContentWithValidRequest() {
        return new Object[][]{
                // Sample Request for Dynamic Validation.
                // If validationParams Map Passed, Static Validation based on Response File Will be disabled.
                /*new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_VALID_REQUEST, APIUrl.CREATE_CONTENT, HttpStatus.OK, "Creator",
                        new HashMap<String, Object>(){{put("node_id",null);put("versionKey",null);put("mimeType","application/pdf");}}
                },*/
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_VALID_REQUEST, APIUrl.CREATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_ECML_CONTENT_WITH_VALID_REQUEST, APIUrl.CREATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_HTML_CONTENT_WITH_VALID_REQUEST, APIUrl.CREATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_H5P_CONTENT_WITH_VALID_REQUEST, APIUrl.CREATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_YOUTUBE_CONTENT_WITH_VALID_REQUEST, APIUrl.CREATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_VIDEO_MP4_CONTENT_WITH_VALID_REQUEST, APIUrl.CREATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_VIDEO_MPEG_CONTENT_WITH_VALID_REQUEST, APIUrl.CREATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_PLUGIN_CONTENT_WITH_VALID_REQUEST, APIUrl.CREATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_ASSET_VIDEO_MP4_CONTENT_WITH_VALID_REQUEST, APIUrl.CREATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_ASSET_VIDEO_MPEG_CONTENT_WITH_VALID_REQUEST, APIUrl.CREATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_ASSET_VIDEO_WEBM_CONTENT_WITH_VALID_REQUEST, APIUrl.CREATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_ASSET_IMAGE_JPEG_CONTENT_WITH_VALID_REQUEST, APIUrl.CREATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_ASSET_IMAGE_PNG_CONTENT_WITH_VALID_REQUEST, APIUrl.CREATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null
                }
        };
    }

    @DataProvider(name = "readResourceContent")
    public Object[][] readResourceContent() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_READ_RESOURCE_CONTENT_WITH_VALID_IDENTIFIER, APIUrl.READ_CONTENT, HttpStatus.OK, Constant.CREATOR,
                        new HashMap(){{put("identifier",null);put("versionKey",null);put("mimeType","application/pdf");put("status","Draft");}}, "application/pdf"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_RESOURCE_CONTENT_WITH_VALID_IDENTIFIER, APIUrl.READ_CONTENT, HttpStatus.OK, Constant.CREATOR,
                        new HashMap(){{put("identifier",null);put("versionKey",null);put("mimeType","video/x-youtube");put("status","Draft");}}, "video/x-youtube"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_RESOURCE_CONTENT_WITH_INVALID_IDENTIFIER, APIUrl.READ_CONTENT, HttpStatus.NOT_FOUND, Constant.CREATOR,
                        null, null
                }
        };
    }
}
