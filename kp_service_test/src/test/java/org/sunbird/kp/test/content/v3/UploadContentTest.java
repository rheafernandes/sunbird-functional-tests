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


public class UploadContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/upload";
    private static final String MATCHED_EXTENSION = ".pdf";

    @Test(dataProvider = "uploadResourceContentWithFile")
    @CitrusParameters({"userType", "mimeType", "extension"})
    @CitrusTest
    public void testUploadResourceContentWithFile(String userType, String mimeType, String extension) {
        getAuthToken(this, userType);
        String contentId = (String) ContentUtil.createResourceContent(this, null, mimeType, null).get("content_id");
        Map<String, Object> result = ContentUtil.uploadResourceContent(this, contentId, mimeType, null);
        Assert.assertTrue(result.containsKey("content_url"));
        String url = (String) result.get("content_url");
        Assert.assertTrue(url.endsWith(extension));
    }

    @Test(dataProvider = "uploadAssetContentWithFile")
    @CitrusParameters({"userType", "mimeType", "extension"})
    @CitrusTest
    public void testUploadAssetContentWithFile(String userType, String mimeType, String extension) {
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
        setContext(this, contentId, mimeType, extension);
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

    @Test(dataProvider = "uploadResourceContentWithFileNegativeCase")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType", "valParams", "mimeType", "extension"})
    @CitrusTest
    public void testUploadResourceContentWithFileNegativeCase(
            String testName, String requestUrl, HttpStatus httpStatusCode, String userType, Map<String, Object> valParams, String mimeType, String extension) {
        getAuthToken(this, userType);
        String contentId = (String) ContentUtil.createResourceContent(this, null, mimeType, null).get("content_id");
        setContext(this, contentId, mimeType, extension);
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

    @DataProvider(name = "uploadResourceContentWithFile")
    public Object[][] uploadResourceContentWithFile() {
        return new Object[][]{
                new Object[]{Constant.CREATOR, "application/pdf", ".pdf"},
                new Object[]{Constant.CREATOR, "application/vnd.ekstep.ecml-archive", ".zip"},
                new Object[]{Constant.CREATOR, "application/vnd.ekstep.html-archive", ".zip"},

        };
    }

    @DataProvider(name = "uploadAssetContentWithFile")
    public Object[][] uploadAssetContentWithFile() {
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

    @DataProvider(name = "uploadResourceContentWithFileNegativeCase")
    public Object[][] uploadResourceContentWithFileNegativeCase() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_UPLOAD_RESOURCE_PDF_WITH_FILE_MISMATCHED_MIME, APIUrl.UPLOAD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR,
                        null, "application/pdf", ".jpg"
                },
                new Object[]{
                        ContentV3Scenario.TEST_UPLOAD_RESOURCE_PDF_WITH_FILE_INVALID_IDENTIFIER, APIUrl.UPLOAD_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR,
                        null, null, ".pdf"
                },

        };
    }

    private void setContext(BaseCitrusTestRunner runner, String contentId, String mimeType, String ext) {
        String extension = ext != null ? ext : MATCHED_EXTENSION;
        if (StringUtils.isNotBlank(mimeType) && StringUtils.isNotBlank(contentId)) {
            switch (mimeType.toLowerCase()) {
                case "application/pdf":
                case "application/vnd.ekstep.ecml-archive":
                case "application/vnd.ekstep.html-archive":
                case "application/vnd.ekstep.h5p-archive":
                case "video/x-youtube":
                case "image/png":
                case "image/jpg":
                case "video/mp4":
                case "video/webm":
                case "video/mpeg":
                case "audio/mp3":
                    runner.testContext.setVariable("fileNameValue", "sample" + extension);
                    runner.testContext.setVariable("fileUrlValue", "sample_url" + extension);
                    break;
            }
        } else { //handle for mimeType == null (invalid id)
            runner.testContext.setVariable("fileNameValue", "sample" + extension);
            runner.testContext.setVariable("fileUrlValue", "sample_url" + extension);
        }
    }

}
