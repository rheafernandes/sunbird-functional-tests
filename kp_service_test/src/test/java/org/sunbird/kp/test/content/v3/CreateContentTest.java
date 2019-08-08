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

/**
 * Integration Test Cases for Content Create API
 *
 * @author Kumar Gauraw
 */
public class CreateContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/create";

    @Test(dataProvider = "createResourceContentWithValidRequest")
    @CitrusParameters("testName")
    @CitrusTest
    public void testCreateResourceContentWithValidRequest(String testName) {
        getAuthToken(this, Constant.CREATOR);
        performPostTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.CREATE_CONTENT,
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                HttpStatus.OK,
                null,
                RESPONSE_JSON
        );
    }

    @Test(dataProvider = "createAssetContentWithValidRequest")
    @CitrusParameters("testName")
    @CitrusTest
    public void testCreateAssetContentWithValidRequest(String testName) {
        getAuthToken(this, Constant.CREATOR);
        performPostTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.CREATE_CONTENT,
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                HttpStatus.OK,
                null,
                RESPONSE_JSON
        );
    }

    @Test(dataProvider = "createResourceContentWithInvalidRequest")
    @CitrusParameters("testName")
    @CitrusTest
    public void createResourceContentWithInvalidRequest(String testName) {
        getAuthToken(this, Constant.CREATOR);
        performPostTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.CREATE_CONTENT,
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                HttpStatus.BAD_REQUEST,
                null,
                RESPONSE_JSON
        );
    }

    @Test
    @CitrusTest
    public void testCreateResourcePdfContentWithDuplicateIdentifier() {
        getAuthToken(this, Constant.CREATOR);
        String contentId = (String) ContentUtil.createResourceContent(this,null,"application/pdf",null).get("content_id");
        this.variable("identifierVal",contentId);
        performPostTest(
                this,
                TEMPLATE_DIR,
                "testCreateResourcePdfContentWithDuplicateIdentifier",
                APIUrl.CREATE_CONTENT,
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                HttpStatus.BAD_REQUEST,
                null,
                RESPONSE_JSON
        );
    }


    @DataProvider(name = "createResourceContentWithValidRequest")
    public Object[][] createResourceContentWithValidRequest() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_ECML_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_HTML_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_H5P_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_YOUTUBE_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_VIDEO_MP4_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_VIDEO_MPEG_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_PLUGIN_CONTENT_WITH_VALID_REQUEST
                }
        };
    }

    @DataProvider(name = "createAssetContentWithValidRequest")
    public Object[][] createAssetContentWithValidRequest() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_ASSET_VIDEO_MP4_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_ASSET_VIDEO_MPEG_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_ASSET_VIDEO_WEBM_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_ASSET_IMAGE_JPEG_CONTENT_WITH_VALID_REQUEST
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_ASSET_IMAGE_PNG_CONTENT_WITH_VALID_REQUEST
                }
        };
    }

    @DataProvider(name = "createResourceContentWithInvalidRequest")
    public Object[][] createResourceContentWithInvalidRequest() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_REQUEST_WITHOUT_NAME
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_REQUEST_WITHOUT_CODE
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_REQUEST_WITHOUT_MIMETYPE
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_REQUEST_WITHOUT_CONTENT_TYPE
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_REQUEST_WITH_SYSTEM_PROPS
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_MIMETYPE
                },
                new Object[]{
                        ContentV3Scenario.TEST_CREATE_RESOURCE_PDF_CONTENT_WITH_INVALID_CONTENT_TYPE
                }
        };
    }
}
