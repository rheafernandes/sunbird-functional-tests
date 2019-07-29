package org.sunbird.kp.test.content.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.util.ContentUtil;
import org.sunbird.kp.test.util.WorkflowConstants;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Flag API test scenarios
 *
 * @author pritha
 */
public class FlagTest extends BaseCitrusTestRunner {


    private static final String TEMPLATE_DIR = "templates/content/v3/flag";

    private String flagContent(String testName, String mimeType, HttpStatus httpStatusCode, Map<String, Object> valParams, String workFlowStatus) {
        Map<String, Object> resourceMap = ContentUtil.prepareResourceContent(workFlowStatus, this, null, mimeType, null);

        String contentId = (String) resourceMap.get("content_id");
        Map<String, Object> contentMap = (Map<String, Object>) ContentUtil.readContent(this, contentId, "edit", null).get("content");

        this.variable("versionKeyVal", contentMap.get("versionKey").toString());
        performPostTest(
                this,
                TEMPLATE_DIR,
                testName,
                APIUrl.FLAG_CONTENT + contentId,
                null,
                REQUEST_JSON,
                MediaType.APPLICATION_JSON,
                httpStatusCode,
                valParams,
                null
        );
        return contentId;

    }

    @Test(dataProvider = "flagContent")
    @CitrusParameters({"testName", "userType", "mimeType", "httpStatusCode", "valParams", "workFlowStatus"})
    @CitrusTest
    public void testFlagContent(String testName, String userType, String mimeType, HttpStatus httpStatusCode, Map<String, Object> valParams, String workFlowStatus) {

        getAuthToken(this, userType);
        flagContent(testName, mimeType, httpStatusCode, valParams, workFlowStatus);

    }

    @DataProvider(name = "flagContent")
    public Object[][] flagContent() {
        return new Object[][]{

                //flag with valid scenarios
                new Object[]{ContentV3Scenario.TEST_FLAG_VALID_REQUEST_VALID_ID, Constant.CREATOR, "application/pdf", HttpStatus.OK, null, WorkflowConstants.CONTENT_IN_LIVE_STATE},
                new Object[]{ContentV3Scenario.TEST_FLAG_VALID_REQUEST_WITHOUT_FLAG_ARRAY, Constant.CREATOR, "application/pdf", HttpStatus.OK, null, WorkflowConstants.CONTENT_IN_LIVE_STATE},
                new Object[]{ContentV3Scenario.TEST_FLAG_VALID_REQUEST_WITHOUT_FLAGGEDREASON, Constant.CREATOR, "application/pdf", HttpStatus.OK, null, WorkflowConstants.CONTENT_IN_LIVE_STATE},
               // new Object[]{ContentV3Scenario.TEST_FLAG_WITH_PUBLISHED_CONTENT_WITH_EXISTING_IMG_WITH_VALID_REQUEST, Constant.CREATOR, "application/pdf", HttpStatus.OK, null, WorkflowConstants.CONTENT_IN_LIVE_IMAGE_DRAFT_STATE},
               // new Object[]{ContentV3Scenario.TEST_FLAG_WITH_FLAGGED_STATE_WITH_VALID_REQUEST, Constant.CREATOR, "application/pdf", HttpStatus.OK, null, WorkflowConstants.CONTENT_IN_FLAG_STATE},

                //flagging with content in other status
                new Object[]{ContentV3Scenario.TEST_FLAG_WITH_FLAGDRAFT_STATE_WITH_VALID_REQUEST, Constant.CREATOR, "application/pdf", HttpStatus.BAD_REQUEST, null, WorkflowConstants.CONTENT_IN_FLAG_DRAFT},
                new Object[]{ContentV3Scenario.TEST_FLAG_WITH_FLAGREVIEW_STATE_WITH_VALID_REQUEST, Constant.CREATOR, "application/pdf", HttpStatus.BAD_REQUEST, null, WorkflowConstants.CONTENT_IN_FLAG_REVIEW},
                new Object[]{ContentV3Scenario.TEST_FLAG_WITH_REVIEW_STATE_WITH_VALID_REQUEST, Constant.CREATOR, "application/pdf", HttpStatus.BAD_REQUEST, null, WorkflowConstants.CONTENT_IN_REVIEW_STATE},
                new Object[]{ContentV3Scenario.TEST_FLAG_WITH_RETIRE_STATE_WITH_VALID_REQUEST, Constant.CREATOR, "application/pdf", HttpStatus.BAD_REQUEST, null, WorkflowConstants.CONTENT_IN_RETIRED_STATE},


                //flagging with invalid requests
                new Object[]{ContentV3Scenario.TEST_FLAG_VALID_REQUEST_WITHOUT_VERSIONKEY, Constant.CREATOR, "application/pdf", HttpStatus.BAD_REQUEST, null, WorkflowConstants.CONTENT_IN_LIVE_STATE},
                new Object[]{ContentV3Scenario.TEST_FLAG_VALID_REQUEST_WITH_INVALID_VERSIONKEY, Constant.CREATOR, "application/pdf", HttpStatus.BAD_REQUEST, null, WorkflowConstants.CONTENT_IN_LIVE_STATE},
               // new Object[]{ContentV3Scenario.TEST_FLAG_VALID_REQUEST_WITHOUT_FLAGGEDBY, Constant.CREATOR, "application/pdf", HttpStatus.BAD_REQUEST, null, WorkflowConstants.CONTENT_IN_LIVE_STATE},
        };
    }
}
