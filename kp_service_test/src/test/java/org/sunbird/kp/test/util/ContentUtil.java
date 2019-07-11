package org.sunbird.kp.test.util;

import com.consol.citrus.context.TestContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.AppConfig;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.common.TestActionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility Class for Content API Test
 * @author Kumar Gauraw
 */
public class ContentUtil {

    private static final String API_KEY = AppConfig.config.getString("kp_api_key");
    private static final Boolean IS_USER_AUTH_REQUIRED = AppConfig.config.getBoolean("user_auth_enable");
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final String CONTENT_PAYLOAD_DIR = "templates/payload/content";

    //static payload
    private static final String CREATE_CONTENT_EXPECT_200 = "createContentExpect200";
    private static final String UPLOAD_CONTENT_EXPECT_200 = "uploadContentExpect200";
    private static final String CREATE_ASSET_EXPECT_200 = "createAssetExpect200";
    private static final String UPLOAD_ASSET_EXPECT_200 = "uploadAssetExpect200";
    private static final String CREATE_TEXTBOOK_EXPECT_200 = "createTextbookExpect200";


    public static Map<String, Object> createContent(BaseCitrusTestRunner runner, String payload, String mimeType, Map<String, Object> headers) {
        String contentId = "KP_FT_12345";
        String versionKey = "12345";
        Map<String, Object> data = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(payload)) {
            return createContent(runner, runner.testContext, payload, null, headers);
        } else if (StringUtils.isNotBlank(mimeType)) {
            switch (mimeType.toLowerCase()) {
                case "application/pdf": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.resource.pdf");
                    runner.variable("mimeTypeVal", mimeType);
                    runner.variable("contentTypeVal", Constant.CONTENT_TYPE_RESOURCE);
                    runner.variable("mediaTypeVal", Constant.MEDIA_TYPE_CONTENT);
                    break;
                }
                case "application/vnd.ekstep.ecml-archive": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.resource.ecml");
                    runner.variable("mimeTypeVal", mimeType);
                    runner.variable("contentTypeVal", Constant.CONTENT_TYPE_RESOURCE);
                    runner.variable("mediaTypeVal", Constant.MEDIA_TYPE_CONTENT);
                    break;
                }
                case "application/vnd.ekstep.html-archive": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.resource.html");
                    runner.variable("mimeTypeVal", mimeType);
                    runner.variable("contentTypeVal", Constant.CONTENT_TYPE_RESOURCE);
                    runner.variable("mediaTypeVal", Constant.MEDIA_TYPE_CONTENT);
                    break;
                }
                case "application/vnd.ekstep.h5p-archive": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.resource.h5p");
                    runner.variable("mimeTypeVal", mimeType);
                    runner.variable("contentTypeVal", Constant.CONTENT_TYPE_RESOURCE);
                    runner.variable("mediaTypeVal", Constant.MEDIA_TYPE_CONTENT);
                    break;
                }
                case "application/vnd.ekstep.plugin-archive": {
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    runner.variable("idVal", timestamp);
                    runner.variable("codeVal", "KP_FT_" + timestamp);
                    runner.variable("mimeTypeVal", mimeType);
                    runner.variable("contentTypeVal", Constant.CONTENT_TYPE_PLUGIN);
                    runner.variable("mediaTypeVal", Constant.MEDIA_TYPE_CONTENT);
                    break;
                }
                case "video/x-youtube": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.resource.youtube");
                    runner.variable("mimeTypeVal", mimeType);
                    runner.variable("contentTypeVal", Constant.CONTENT_TYPE_RESOURCE);
                    runner.variable("mediaTypeVal", Constant.MEDIA_TYPE_CONTENT);
                    break;
                }
                case "application/vnd.ekstep.content-collection": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.collection.collection");
                    runner.variable("mimeTypeVal", mimeType);
                    runner.variable("contentTypeVal", Constant.CONTENT_TYPE_COLLECTION);
                    runner.variable("mediaTypeVal", Constant.MEDIA_TYPE_CONTENT);
                    break;
                }

                default: {

                }
            }
            return createContent(runner, runner.testContext, null, CREATE_CONTENT_EXPECT_200, headers);
        }
        data.put("content_id", contentId);
        data.put("versionKey", versionKey);
        return data;
    }


    private static Map<String, Object> createContent(BaseCitrusTestRunner runner, TestContext testContext, String payload, String testName, Map<String, Object> headers) {
        Map<String, Object> data = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(payload)) {
            System.out.println("Dynamic Payload...");
            runner.http(
                    builder ->
                            TestActionUtil.getPostRequestTestAction(
                                    builder,
                                    Constant.KP_ENDPOINT,
                                    APIUrl.CREATE_CONTENT,
                                    MediaType.APPLICATION_JSON.toString(),
                                    payload,
                                    getHeaders(headers)));

        } else if (StringUtils.isNotBlank(testName)) {
            System.out.println("Static DynamicPayload Used With Dynamic Values...");
            runner.getTestCase().setName(testName);
            runner.http(
                    builder ->
                            TestActionUtil.processPostRequest(
                                    builder,
                                    Constant.KP_ENDPOINT,
                                    CONTENT_PAYLOAD_DIR,
                                    testName,
                                    APIUrl.CREATE_CONTENT,
                                    Constant.REQUEST_JSON,
                                    Constant.CONTENT_TYPE_APPLICATION_JSON,
                                    getHeaders(headers)));
        }

        runner.http(
                builder ->
                        TestActionUtil.getExtractFromResponseTestAction(
                                testContext,
                                builder,
                                Constant.KP_ENDPOINT,
                                HttpStatus.OK,
                                "$.result",
                                "result"));
        Map<String, Object> result = null;
        try {
            result = objectMapper.readValue(testContext.getVariable("result"), new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            System.out.println("Exception Occurred While parsing context variable : " + e);
        }

        if (MapUtils.isNotEmpty(result)) {
            data.put("content_id", (String) result.get("node_id"));
            data.put("versionKey", (String) result.get("versionKey"));
        }

        return data;
    }

    public static void publishContent(BaseCitrusTestRunner runner,  TestContext testContext, String payload, String testName, String publishType, String contentId, Map<String, Object> headers) {
        final String url = StringUtils.equalsIgnoreCase("unlisted",publishType.toLowerCase())?(APIUrl.UNLISTED_PUBLISH_CONTENT + contentId) : (APIUrl.PUBLIC_PUBLISH_CONTENT + contentId);
        if (StringUtils.isNotBlank(payload)) {
            runner.http(
                    builder ->
                            TestActionUtil.getPostRequestTestAction(
                                    builder,
                                    Constant.KP_ENDPOINT,
                                    url,
                                    MediaType.APPLICATION_JSON.toString(),
                                    payload,
                                    getHeaders(headers)));
            runner.http(
                    builder ->TestActionUtil.getResponse(
                    builder, Constant.KP_ENDPOINT, "", "publishContent", HttpStatus.OK, "", null));
        }
    }

    /**
     * This Method provides all necessary header elements
     * @param additionalHeaders
     * @return
     */
    private static Map<String, Object> getHeaders(Map<String, Object> additionalHeaders) {
        Map<String, Object> headers = new HashMap<String, Object>();
        if (MapUtils.isNotEmpty(additionalHeaders))
            headers.putAll(additionalHeaders);

        if (!headers.containsKey(Constant.X_CHANNEL_ID))
            headers.put(Constant.X_CHANNEL_ID, AppConfig.config.getString("kp_test_default_channel"));

        headers.put(Constant.AUTHORIZATION, Constant.BEARER + API_KEY);

        if (IS_USER_AUTH_REQUIRED)
            headers.put(Constant.X_AUTHENTICATED_USER_TOKEN, "${accessToken}");
        return headers;
    }

}
