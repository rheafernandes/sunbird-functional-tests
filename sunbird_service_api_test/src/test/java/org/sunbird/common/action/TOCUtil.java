package org.sunbird.common.action;

import com.consol.citrus.context.TestContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;

import java.util.Map;

/**
 * This Util Class holds methods to Create Content for TOC API's Test Cases.
 * @author Gauraw
 */
public class TOCUtil {

    private static final String CONTENT_CREATE_URL = "/content/v1/create";
    private static final String TOC_TEMPLATE_DIR = "templates/textbook/toc";
    private static final String CREATE_TEST_TEXTBOOK = "createTestTextbookSuccess";
    private static final String CREATE_TEST_TEXTBOOK_WITH_CHILDREN = "createTestTextbookWithChildrenSuccess";
    private static final String CREATE_TEST_RESOURCE_CONTENT = "createTestResourceContentSuccess";

    public static String createTextbook(BaseCitrusTestRunner runner, TestContext testContext) {
        return createContent(runner, testContext, CREATE_TEST_TEXTBOOK);
    }

    public static String createTextbookWithChildren(BaseCitrusTestRunner runner, TestContext testContext) {
        //TODO: Need to Enhance this method to call update hierarchy.
        return createContent(runner, testContext, CREATE_TEST_TEXTBOOK_WITH_CHILDREN);
    }

    public static String createResourceContent(BaseCitrusTestRunner runner, TestContext testContext) {
        return createContent(runner, testContext, CREATE_TEST_RESOURCE_CONTENT);
    }

    private static String createContent(BaseCitrusTestRunner runner, TestContext testContext, String testName) {
        String contentId = "";
        runner.http(
                builder ->
                        TestActionUtil.getPostRequestTestAction(
                                builder,
                                Constant.CONTENT_STORE_ENDPOINT,
                                TOC_TEMPLATE_DIR,
                                testName,
                                CONTENT_CREATE_URL,
                                Constant.REQUEST_JSON,
                                MediaType.APPLICATION_JSON.toString(),
                                getHeaders()));
        runner.http(
                builder ->
                        TestActionUtil.getExtractFromResponseTestAction(
                                testContext,
                                builder,
                                Constant.CONTENT_STORE_ENDPOINT,
                                HttpStatus.OK,
                                "$.result.node_id",
                                "contentId"));
        contentId = testContext.getVariable("contentId");
        runner.sleep(Constant.ES_SYNC_WAIT_TIME);
        return contentId;
    }

    private static Map<String, Object> getHeaders() {
        Map<String, Object> headers = TestActionUtil.getHeaders(true);
        headers.put(Constant.AUTHORIZATION, Constant.BEARER + System.getenv("content_store_api_key"));
        return headers;
    }

}
