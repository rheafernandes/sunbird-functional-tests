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

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.MediaType;


public class ReviewContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3";

    @Test(dataProvider = "reviewContent")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType","valParams","mimeType", "doUpload"})
    @CitrusTest
    public void testReviewContent(
            String testName, String requestUrl, HttpStatus httpStatusCode, String userType, Map<String, Object> valParams, String mimeType, boolean doUpload) {
        getAuthToken(this, userType);
        String contentId = (String) ContentUtil.createResourceContent(this, null, mimeType, null).get("content_id");
        if(doUpload) ContentUtil.uploadResourceContent(this, contentId, mimeType, null);
        performPostTest(
                this,
                TEMPLATE_DIR,
                testName,
                requestUrl + contentId,
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                httpStatusCode,
                valParams,
                RESPONSE_JSON
        );
    }

    @DataProvider(name = "reviewContent")
    public Object[][] reviewContent() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_REVIEW_WITH_VALID_IDENTIFIER_WITH_NOT_UPLOAD_FILE, APIUrl.REVIEW_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR,
                        null, "application/pdf", false
                },
                new Object[]{
                        ContentV3Scenario.TEST_REVIEW_WITH_VALID_IDENTIFIER, APIUrl.REVIEW_CONTENT, HttpStatus.OK, Constant.REVIEWER,
                        null, "application/pdf", true
                },
                new Object[]{
                        ContentV3Scenario.TEST_REVIEW_WITH_INVALID_IDENTIFIER, APIUrl.REVIEW_CONTENT, HttpStatus.BAD_REQUEST, Constant.CREATOR,
                        null, "application/pdf", false
                }
        };
    }

}
