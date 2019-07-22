package org.sunbird.kp.test.content.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.util.ContentUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Functional test cases for Upload API
 *
 * @author pritha
 */
public class UploadContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/upload";
    private static final String MATCHED_EXTENSION = ".pdf";
    private static final String IMG_EXTENSION = ".img";

    @Test(dataProvider = "uploadResourceContentInLiveWithFile")
    @CitrusParameters({"userType", "mimeType", "extension"})
    @CitrusTest
    public void testUploadResourceContentInLiveWithFile(String userType, String mimeType, String extension) {
        getAuthToken(this, userType);
        String contentId = (String) ContentUtil.prepareResourceContent("contentInLive", this, null, mimeType, null).get("content_id");
        System.out.println("contentId for live content == " + contentId);
        Map<String, Object> result = ContentUtil.uploadResourceContent(this, contentId, mimeType, null);
        Assert.assertTrue(result.containsKey("content_url"));
        String url = (String) result.get("content_url");
        Assert.assertTrue(url.endsWith(extension));

        Map<String, Object> resourceMap = (Map<String, Object>) ContentUtil.readContent(this, contentId, "edit", null).get("content");
        String identifier = (String)resourceMap.get("identifier");
        Assert.assertTrue(identifier.endsWith(IMG_EXTENSION));

    }


    @Test(dataProvider = "uploadResourceContentWithFileMimeTypes")
    @CitrusParameters({"userType", "mimeType", "extension"})
    @CitrusTest
    public void testUploadResourceContentWithFileMimeTypes(String userType, String mimeType, String extension) {
        getAuthToken(this, userType);
        String contentId = (String) ContentUtil.createResourceContent(this, null, mimeType, null).get("content_id");
        Map<String, Object> result = ContentUtil.uploadResourceContent(this, contentId, mimeType, null);
        Assert.assertTrue(result.containsKey("content_url"));
        String url = (String) result.get("content_url");
        Assert.assertTrue(url.endsWith(extension));
    }

    @Test(dataProvider = "uploadAssetContentWithFileMimeTypes")
    @CitrusParameters({"userType", "mimeType", "extension"})
    @CitrusTest
    public void testUploadAssetContentWithFileMimeTypes(String userType, String mimeType, String extension) {
        getAuthToken(this, userType);
        String contentId = (String) ContentUtil.createAssetContent(this, null, mimeType, null).get("content_id");
        Map<String, Object> result = ContentUtil.uploadAssetContent(this, contentId, mimeType, null);
        Assert.assertTrue(result.containsKey("content_url"));
        String url = (String) result.get("content_url");
        Assert.assertTrue(url.endsWith(extension));
    }

    @Test(dataProvider = "uploadResourceContentWithFileUrl")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType", "valParams", "mimeType", "extension"})
    @CitrusTest
    public void testUploadResourceContentWithFileUrl(
            String testName, String requestUrl, HttpStatus httpStatusCode, String userType, Map<String, Object> valParams, String mimeType, String extension) {
        getAuthToken(this, userType);
        String contentId = (String) ContentUtil.createResourceContent(this, null, mimeType, null).get("content_id");
        System.out.println("created contentId = " + contentId);
        setContext(this, contentId, mimeType, extension, null);
        performMultipartTest(
                this,
                TEMPLATE_DIR,
                testName,
                requestUrl + contentId,
                null,
                Constant.REQUEST_FORM_DATA,
                httpStatusCode,
                valParams,
                null
        );
    }

    @Test(dataProvider = "uploadResourceContentWithFile")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType", "valParams", "mimeType", "extension", "fileName"})
    @CitrusTest
    public void testUploadResourceContentWithFile(
            String testName, String requestUrl, HttpStatus httpStatusCode, String userType, Map<String, Object> valParams, String mimeType, String extension, String fileName) {
        getAuthToken(this, userType);
        String contentId = (String) ContentUtil.createResourceContent(this, null, mimeType, null).get("content_id");
        setContext(this, contentId, mimeType, extension, fileName);
        performMultipartTest(
                this,
                TEMPLATE_DIR,
                testName,
                requestUrl + contentId,
                null,
                Constant.REQUEST_FORM_DATA,
                httpStatusCode,
                valParams,
                null
        );
    }

    @DataProvider(name = "uploadResourceContentWithFileMimeTypes")
    public Object[][] uploadResourceContentWithFileMimeTypes() {
        return new Object[][]{
                new Object[]{Constant.CREATOR, "application/pdf", ".pdf"},
                new Object[]{Constant.CREATOR, "application/vnd.ekstep.ecml-archive", ".zip"},
                new Object[]{Constant.CREATOR, "application/vnd.ekstep.html-archive", ".zip"},

        };
    }

    @DataProvider(name = "uploadAssetContentWithFileMimeTypes")
    public Object[][] uploadAssetContentWithFileMimeTypss() {
        return new Object[][]{
                new Object[]{Constant.CREATOR, "image/png", ".png"},
                new Object[]{Constant.CREATOR, "image/jpg", ".jpg"},
                new Object[]{Constant.CREATOR, "video/mp4", ".mp4"},
                new Object[]{Constant.CREATOR, "video/webm", ".webm"},
                new Object[]{Constant.CREATOR, "audio/mp3", ".mp3"},
                new Object[]{Constant.CREATOR, "video/mpeg", ".mpeg"},

        };
    }

    @DataProvider(name = "uploadResourceContentWithFileUrl")
    public Object[][] uploadResourceContentWithFileUrl() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_UPLOAD_RESOURCE_PDF_WITH_FILE_URL, APIUrl.UPLOAD_CONTENT, HttpStatus.OK, Constant.CREATOR,
                        null, "application/pdf", ".pdf"
                },

        };
    }

    @DataProvider(name = "uploadResourceContentWithFile")
    public Object[][] uploadResourceContentWithFile() {
        return new Object[][]{
                //specific negetive test scenarios
                new Object[]{
                        ContentV3Scenario.TEST_UPLOAD_RESOURCE_PDF_WITH_FILE_MISMATCHED_MIME, APIUrl.UPLOAD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR,
                        null, "application/pdf", ".jpg", null
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPLOAD_RESOURCE_PDF_WITH_FILE_INVALID_IDENTIFIER, APIUrl.UPLOAD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR,
                        null, null, null, ".pdf"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPLOAD_RESOURCE_WITH_EMPTY_ZIP, APIUrl.UPLOAD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR,
                        null, "application/vnd.ekstep.ecml-archive", null, "empty.zip"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPLOAD_RESOURCE_ZIP_WITHOUT_INDEX, APIUrl.UPLOAD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR,
                        null, "application/vnd.ekstep.ecml-archive", null, "UploadWithoutIndex.zip"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPLOAD_RESOURCE_ZIP_WITHOUT_ASSET, APIUrl.UPLOAD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR,
                        null, "application/vnd.ekstep.ecml-archive", null, "sample_withoutAssets.zip"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPLOAD_RESOURCE_WITH_VALID_ECML_INVALID_JSON, APIUrl.UPLOAD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR,
                        null, "application/vnd.ekstep.ecml-archive", null, "ecmlCorruptedJSON.zip"
                },

                new Object[]{
                        ContentV3Scenario.TEST_UPLOAD_RESOURCE_WITH_GREATER_THAN_50MB_ZIP, APIUrl.UPLOAD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR,
                        null, "application/vnd.ekstep.ecml-archive", null, "contentAbove50MB.zip"
                },
                // specific positive test cases
                new Object[]{
                        ContentV3Scenario.TEST_UPLOAD_RESOURCE_WITH_VALID_ECML_VALID_JSON, APIUrl.UPLOAD_CONTENT, HttpStatus.OK, Constant.CREATOR,
                        null, "application/vnd.ekstep.ecml-archive", null, "ecml_with_json.zip"
                },

                new Object[]{
                        ContentV3Scenario.TEST_UPLOAD_RESOURCE_ECML_WITH_TWIN_ANIMATION_AUDIO_SPRITES_IMG_SPRITES, APIUrl.UPLOAD_CONTENT, HttpStatus.OK, Constant.CREATOR,
                        null, "application/vnd.ekstep.ecml-archive", null, "tweenAndaudioSprite.zip"
                },
        };
    }

    @DataProvider(name = "uploadResourceContentInLiveWithFile")
    public Object[][] uploadResourceContentInLiveWithFile() {
        return new Object[][]{
                new Object[]{Constant.CREATOR, "application/pdf", ".pdf"},

        };
    }


    private void setContext(BaseCitrusTestRunner runner, String contentId, String mimeType, String ext, String fileName) {
        String extension = ext != null ? ext : MATCHED_EXTENSION;
        String fileNameValue = fileName != null ? fileName : "sample" + extension;
        if (StringUtils.isNotBlank(mimeType) && StringUtils.isNotBlank(contentId)) {
            switch (mimeType.toLowerCase()) {
                case "application/pdf":
                case "application/vnd.ekstep.ecml-archive":
                case "application/vnd.ekstep.html-archive":
                case "application/vnd.ekstep.h5p-archive":
                case "application/vnd.ekstep.plugin-archive":
                case "video/x-youtube":
                case "image/png":
                case "image/jpg":
                case "video/mp4":
                case "video/webm":
                case "video/mpeg":
                case "audio/mp3":
                    runner.testContext.setVariable("fileNameValue", fileNameValue);
                    runner.testContext.setVariable("fileUrlValue", "sample_url" + extension);
                    break;
            }
        } else { //handle for mimeType == null (invalid id)
            runner.testContext.setVariable("fileNameValue", "sample" + extension);
            runner.testContext.setVariable("fileUrlValue", "url_" + extension);
        }
    }

}
