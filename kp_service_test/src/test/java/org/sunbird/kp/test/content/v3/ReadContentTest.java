package org.sunbird.kp.test.content.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.util.ContentUtil;
import org.sunbird.kp.test.util.TestSetupUtil;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Integration Test Cases for Content Read API
 *
 * @author Kumar Gauraw
 */
public class ReadContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/read";
    private static Map<String, String> dirIdMap = new HashMap<>();


    @AfterClass
    public static void populateAssertionData() {
//        TODO: This code can be commented out after the directories are created and populated.
//        TestSetupUtil.createDirectoriesForTestCases(dirIdMap, "response.json", TEMPLATE_DIR);
    }

    @Test(dataProvider = "readResourceContentWithWorkflow")
    @CitrusParameters({"testName", "workflow"})
    @CitrusTest
    public void testReadResourceContentWithWorkflows(String testName, String workflow) {
        getAuthToken(this, Constant.CREATOR);
        Map<String, Object> result = ContentUtil.prepareResourceContent(workflow, this, null, "application/pdf", null);
        String contentId = (String) result.getOrDefault("content_id", "KP_TEST_000009999");
        this.variable("contentIdVal", contentId);
        this.variable("versionKeyVal", (String) result.getOrDefault("versionKey", "00000000000"));
        dirIdMap.put(testName, contentId);
        performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.READ_CONTENT + contentId,
                null,
                HttpStatus.OK,
                null,
                RESPONSE_JSON
        );
    }

    @Test(dataProvider = "readResourceContentDifferentMimeTypes")
    @CitrusParameters({"testName", "workflow"})
    @CitrusTest
    public void testReadResourceContentDifferentMimeTypes(String testName, String mimeType) {
        getAuthToken(this, Constant.CREATOR);
        Map<String, Object> result = ContentUtil.prepareResourceContent("contentInDraft", this, null, mimeType, null);
        String contentId = (String) result.getOrDefault("content_id", "KP_TEST_000009999");
        this.variable("contentIdVal", contentId);
        this.variable("versionKeyVal", (String) result.getOrDefault("versionKey", "00000000000"));
        dirIdMap.put(testName, contentId);
        performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.READ_CONTENT + contentId,
                null,
                HttpStatus.OK,
                null,
                RESPONSE_JSON
        );
    }

    @Test(dataProvider = "readResourceContentDifferentAssets")
    @CitrusParameters({"testName", "workflow"})
    @CitrusTest
    public void testReadResourceContentDifferentAssets(String testName, String mimeType) {
        getAuthToken(this, Constant.CREATOR);
        Map<String, Object> result = ContentUtil.prepareAssetContent("contentInDraft", this, mimeType, null);
        String contentId = (String) result.getOrDefault("content_id", "KP_TEST_000009999");
        this.variable("contentIdVal", contentId);
        this.variable("versionKeyVal", (String) result.getOrDefault("versionKey", "00000000000"));
        dirIdMap.put(testName, contentId);
        performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.READ_CONTENT + contentId,
                null,
                HttpStatus.OK,
                null,
                RESPONSE_JSON
        );
    }

    @Test(dataProvider = "readResourceContent")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType", "valParams", "mimeType"})
    @CitrusTest
    public void testReadResourceContent(
            String testName, String requestUrl, HttpStatus httpStatusCode, String userType, Map<String, Object> valParams, String mimeType) {
        getAuthToken(this, userType);
        String contentId = (String) ContentUtil.createResourceContent(this, null, mimeType, null).get("content_id");
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


    @DataProvider(name = "readResourceContentWithWorkflow")
    public Object[][] readResourceContentWithWorkflow() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_READ_PDF_CONTENT_AFTER_UPLOAD, "contentUpload"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_PDF_CONTENT_AFTER_REVIEW, "contentInReview"
                },
//                new Object[]{
//                        ContentV3Scenario.TEST_READ_PDF_CONTENT_AFTER_DISCARD, "contentDiscarded"
//                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_PDF_CONTENT_AFTER_FLAG, "contentInFlagged"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_PDF_CONTENT_AFTER_FLAG_ACCEPT, "contentInFlagDraft"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_PDF_CONTENT_AFTER_PUBLISH, "contentInLive"
                },
//                new Object[]{
//                        ContentV3Scenario.TEST_READ_PDF_CONTENT_AFTER_REJECT, ""
//                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_PDF_CONTENT_AFTER_UNLISTED_PUBLISH, "contentInUnlisted"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_PDF_CONTENT_AFTER_UPDATE, "contentDraftUpdated"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_PDF_CONTENT_AFTER_RETIRE, "contentRetired"
                },
        };
    }

    @DataProvider(name = "readResourceContentDifferentMimeTypes")
    public Object[][] readResourceContentDifferentMimeTypes() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_READ_ECML_CONTENT_IN_DRAFT, "application/vnd.ekstep.ecml-archive"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_HTML_CONTENT_IN_DRAFT, "application/vnd.ekstep.html-archive"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_H5P_CONTENT_IN_DRAFT, "application/vnd.ekstep.h5p-archive"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_PLUGIN_IN_DRAFT, "application/vnd.ekstep.plugin-archive"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_YOUTUBE_CONTENT_IN_DRAFT, "video/x-youtube"
                },
        };
    }

    @DataProvider(name = "readResourceContentDifferentAssets")
    public Object[][] readResourceContentDifferentAssets() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_READ_VIDEO_MP4_CONTENT_IN_DRAFT, "video/mp4"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_VIDEO_MPEG_CONTENT_IN_DRAFT, "video/mpeg"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_IMAGE_JPEG_IN_DRAFT, "image/jpeg"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_IMAGE_PNG_IN_DRAFT, "image/png"
                }
        };
    }
}
