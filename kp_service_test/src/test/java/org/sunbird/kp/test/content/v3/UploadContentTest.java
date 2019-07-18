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

import javax.ws.rs.core.MediaType;
import java.util.Map;


public class UploadContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3";
    private static final String MATCHED_EXTENSION = ".pdf";


/*    @Test(dataProvider = "uploadResourceContentForFile")
    @CitrusParameters({"userType", "mimeType", "extension"})
    @CitrusTest
    public void testUploadResourceContentForFile(String userType, String mimeType, String extension) {
        getAuthToken(this, userType);
        String contentId = (String) ContentUtil.createResourceContent(this, null, mimeType, null).get("content_id");
        Map<String, Object> result = ContentUtil.uploadResourceContent(this, contentId, mimeType, null);
        Assert.assertTrue(result.containsKey("content_url"));
        String url = (String) result.get("content_url");
        Assert.assertTrue(url.endsWith(extension));
    }

    @DataProvider(name = "uploadResourceContentForFile")
    public Object[][] uploadResourceContentForFile() {
        return new Object[][]{
                new Object[]{Constant.CREATOR, "application/pdf", ".pdf"},
                new Object[]{Constant.CREATOR, "application/vnd.ekstep.ecml-archive", ".zip"},
                new Object[]{Constant.CREATOR, "application/vnd.ekstep.html-archive", ".zip"}


        };
    }

    @Test(dataProvider = "uploadAssetContentForFile")
    @CitrusParameters({"userType", "mimeType", "extension"})
    @CitrusTest
    public void testUploadAssetContentForFile(String userType, String mimeType, String extension) {
        getAuthToken(this, userType);
        String contentId = (String) ContentUtil.createAssetContent(this, null, mimeType, null).get("content_id");
        Map<String, Object> result = ContentUtil.uploadAssetContent(this, contentId, mimeType, null);
        Assert.assertTrue(result.containsKey("content_url"));
        String url = (String) result.get("content_url");
        Assert.assertTrue(url.endsWith(extension));
    }

    @DataProvider(name = "uploadAssetContentForFile")
    public Object[][] uploadAssetContentForFile() {
        return new Object[][]{
                new Object[]{Constant.CREATOR, "image/png", ".png"},
                new Object[]{Constant.CREATOR, "image/jpg", ".jpg"},
                new Object[]{Constant.CREATOR, "video/mp4", ".mp4"},
                new Object[]{Constant.CREATOR, "video/webm", ".webm"},
                new Object[]{Constant.CREATOR, "audio/mp3", ".mp3"},
                new Object[]{Constant.CREATOR, "video/mpeg", ".mpeg"},

        };
    }*/

   @Test(dataProvider = "uploadResourceContentForFileUrl")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType","valParams","mimeType", "extension"})
    @CitrusTest
    public void testUploadResourceContentForFileUrl(
            String testName, String requestUrl, HttpStatus httpStatusCode, String userType, Map<String, Object> valParams, String mimeType, String extension) {
        getAuthToken(this, userType);
        String contentId = (String) ContentUtil.createResourceContent(this, null, mimeType, null).get("content_id");
        System.out.println("created contentId = "+contentId);
        setContextFor(this, contentId, mimeType, extension);
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

    @DataProvider(name = "uploadResourceContentForFileUrl")
    public Object[][] uploadResourceContentForFileUrl() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_UPLOAD_RESOURCE_PDF_WITH_FILE_URL, APIUrl.UPLOAD_CONTENT, HttpStatus.OK, Constant.CREATOR,
                        null, "application/pdf", ".pdf"
                }

        };
    }

    private void  setContextFor(BaseCitrusTestRunner runner, String contentId, String mimeType, String ext) {
        String extension = ext!=null ? ext : MATCHED_EXTENSION ;
        if (StringUtils.isNotBlank(mimeType) && StringUtils.isNotBlank(contentId)) {
            switch (mimeType.toLowerCase()) {
                case "application/pdf":
                case "application/vnd.ekstep.ecml-archive":
                case "application/vnd.ekstep.html-archive":
                case "application/vnd.ekstep.h5p-archive":
                case "video/x-youtube":
                case "image/png" :
                case "image/jpg" :
                case "video/mp4":
                case "video/webm":
                case "video/mpeg":
                case "audio/mp3" :
                    runner.testContext.setVariable("fileUrlValue", "sample_url"+ extension);
                    break;
            }
        } else { //handle for mimeType == null (invalid id)
            runner.testContext.setVariable("fileUrlValue", "sample_url"+ extension);
        }
    }


}
