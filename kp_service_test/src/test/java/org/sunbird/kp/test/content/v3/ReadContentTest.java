package org.sunbird.kp.test.content.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.util.ContentUtil;
import org.sunbird.kp.test.util.WorkflowConstants;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Integration Test Cases for Content Read API
 * @author Kumar Gauraw
 */
public class ReadContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/read";

    @Test(dataProvider = "readResourceContentWithIdentifier")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType","valParams","mimeType"})
    @CitrusTest
    public void testReadResourceContentWithIdentifier(
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

    @Test(dataProvider = "readResourceContentWithMode")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "valParams", "workFlowStatus", "responseJson", "mode"})
    @CitrusTest
    public void testReadResourceContentWithMode(
            String testName, String requestUrl, HttpStatus httpStatusCode, Map<String, Object> valParams, String workFlowStatus, String responseJson, String mode) {
        String contentId = ContentUtil.prepareResourceContent(workFlowStatus, this, null, "application/vnd.ekstep.ecml-archive", null).get("content_id").toString();
            performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                requestUrl + contentId + "?mode=" + mode,
                null,
                httpStatusCode,
                valParams,
                responseJson
        );
    }

    @Test(dataProvider = "readResourceContentWithFields")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "valParams", "workFlowStatus", "responseJson", "fields"})
    @CitrusTest
    public void testReadResourceContentWithFields(
            String testName, String requestUrl, HttpStatus httpStatusCode, Map<String, Object> valParams, String workFlowStatus, String responseJson, String fields) {
        String contentId = ContentUtil.prepareResourceContent(workFlowStatus, this, null, "application/vnd.ekstep.ecml-archive", null).get("content_id").toString();
        performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                requestUrl + contentId + "?fields=" + fields,
                null,
                httpStatusCode,
                valParams,
                responseJson
        );
    }
    @Test(dataProvider = "readResourceContentWithModeAndFields")
    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "valParams", "workFlowStatus", "responseJson", "mode", "fields"})
    @CitrusTest
    public void testReadResourceContentWithModeAndFields(
            String testName, String requestUrl, HttpStatus httpStatusCode, Map<String, Object> valParams, String workFlowStatus, String responseJson, String mode, String fields) {
        String contentId = ContentUtil.prepareResourceContent(workFlowStatus, this, null, "application/vnd.ekstep.ecml-archive", null).get("content_id").toString();
        performGetTest(
                this,
                TEMPLATE_DIR,
                testName,
                requestUrl + contentId + "?mode=" + mode + "&fields=" + fields,
                null,
                httpStatusCode,
                valParams,
                responseJson
        );
    }

    @DataProvider(name = "readResourceContentWithIdentifier")
    public Object[][] readResourceContentWithIdentifier() {
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
    @DataProvider(name = "readResourceContentWithMode")
    public Object[][] readResourceContentWithMode() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_READ_RESOURCE_CONTENT_WITH_EDIT_MODE, APIUrl.READ_CONTENT, HttpStatus.OK, null, "contentInLiveImageDraft", RESPONSE_JSON, "edit"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_RESOURCE_CONTENT_WITH_INVALID_MODE, APIUrl.READ_CONTENT, HttpStatus.OK, null, "contentInLiveImageDraft", RESPONSE_JSON, "abc"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_RESOURCE_CONTENT_WITH_EMPTY_MODE, APIUrl.READ_CONTENT, HttpStatus.OK, null, "contentInLiveImageDraft", RESPONSE_JSON, ""
                }
        };
    }
    @DataProvider(name = "readResourceContentWithFields")
    public Object[][] readResourceContentWithFields() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_READ_RESOURCE_CONTENT_WITH_FIELDS, APIUrl.READ_CONTENT, HttpStatus.OK, null, "contentInLiveImageDraft", RESPONSE_JSON, "name,status"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_RESOURCE_CONTENT_WITH_INVALID_FIELDS, APIUrl.READ_CONTENT, HttpStatus.OK, null, "contentInLiveImageDraft", RESPONSE_JSON, "abc"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_RESOURCE_CONTENT_WITH_BODY_FIELDS, APIUrl.READ_CONTENT, HttpStatus.OK, null, "contentInLiveImageDraft", RESPONSE_JSON, "body"
                },
                new Object[]{
                        ContentV3Scenario.TEST_READ_RESOURCE_CONTENT_WITH_EMPTY_FIELDS, APIUrl.READ_CONTENT, HttpStatus.OK, null, "contentInLiveImageDraft", RESPONSE_JSON, ""
                }
        };
    }
    @DataProvider(name = "readResourceContentWithModeAndFields")
    public Object[][] readResourceContentWithModeAndFields() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_READ_RESOURCE_CONTENT_WITH_FIELDS_AND_MODE, APIUrl.READ_CONTENT, HttpStatus.OK, null, "contentInLiveImageDraft", RESPONSE_JSON, "edit", "status"
                }
        };
    }

    }

