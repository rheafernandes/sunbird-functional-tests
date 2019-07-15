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
    private static final String CREATE_RESOURCE_CONTENT_EXPECT_200 = "createResourceContentExpect200";
    private static final String UPLOAD_RESOURCE_CONTENT_EXPECT_200 = "uploadResourceContentExpect200";
    private static final String CREATE_ASSET_CONTENT_EXPECT_200 = "createAssetContentExpect200";
    private static final String UPLOAD_ASSET_CONTENT_EXPECT_200 = "uploadAssetContentExpect200";
    private static final String CREATE_COLLECTION_CONTENT_EXPECT_200 = "createCollectionContentExpect200";


    public static Map<String, Object> createResourceContent(BaseCitrusTestRunner runner, String payload, String mimeType, Map<String, Object> headers) {
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
                    break;
                }
                case "application/vnd.ekstep.ecml-archive": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.resource.ecml");
                    runner.variable("mimeTypeVal", mimeType);
                    break;
                }
                case "application/vnd.ekstep.html-archive": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.resource.html");
                    runner.variable("mimeTypeVal", mimeType);
                    break;
                }
                case "application/vnd.ekstep.h5p-archive": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.resource.h5p");
                    runner.variable("mimeTypeVal", mimeType);
                    break;
                }
                case "application/vnd.ekstep.plugin-archive": {
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    runner.variable("idVal", timestamp);
                    runner.variable("codeVal", "KP_FT_" + timestamp);
                    runner.variable("mimeTypeVal", mimeType);
                    break;
                }
                case "video/x-youtube": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.resource.youtube");
                    runner.variable("mimeTypeVal", mimeType);
                    break;
                }

            }
            return createContent(runner, runner.testContext, null, CREATE_RESOURCE_CONTENT_EXPECT_200, headers);
        }
        data.put("content_id", contentId);
        data.put("versionKey", versionKey);
        return data;
    }

    public static Map<String, Object> createAssetContent(BaseCitrusTestRunner runner, String payload, String mimeType, Map<String, Object> headers) {
        String contentId = "KP_FT_12345";
        String versionKey = "12345";
        Map<String, Object> data = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(payload)) {
            return createContent(runner, runner.testContext, payload, null, headers);
        } else if (StringUtils.isNotBlank(mimeType)) {
            switch (mimeType.toLowerCase()) {
                case "image/jpeg": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.asset.jpeg");
                    runner.variable("mimeTypeVal", mimeType);
                    runner.variable("mediaTypeVal", "image");
                    break;
                }
                case "image/png": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.asset.png");
                    runner.variable("mimeTypeVal", mimeType);
                    runner.variable("mediaTypeVal", "image");
                    break;
                }
                case "image/jpg": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.asset.jpg");
                    runner.variable("mimeTypeVal", mimeType);
                    runner.variable("mediaTypeVal", "image");
                    break;
                }
                case "video/mp4": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.asset.mp4");
                    runner.variable("mimeTypeVal", mimeType);
                    runner.variable("mediaTypeVal", "video");
                    break;
                }
                case "video/webm": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.asset.webm");
                    runner.variable("mimeTypeVal", mimeType);
                    runner.variable("mediaTypeVal", "video");
                    break;
                }
                case "video/mpeg": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.asset.mpeg");
                    runner.variable("mimeTypeVal", mimeType);
                    runner.variable("mediaTypeVal", "video");
                    break;
                }
                case "audio/mp3": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.asset.mp3");
                    runner.variable("mimeTypeVal", mimeType);
                    runner.variable("mediaTypeVal", "audio");
                    break;
                }

            }
            return createContent(runner, runner.testContext, null, CREATE_ASSET_CONTENT_EXPECT_200, headers);
        }
        data.put("content_id", contentId);
        data.put("versionKey", versionKey);
        return data;
    }

    public static Map<String, Object> createCollectionContent(BaseCitrusTestRunner runner, String payload, String collectionType, Map<String, Object> headers) {
        String contentId = "KP_FT_12345";
        String versionKey = "12345";
        Map<String, Object> data = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(payload)) {
            return createContent(runner, runner.testContext, payload, null, headers);
        } else if (StringUtils.isNotBlank(collectionType)) {
            switch (collectionType.toLowerCase()) {
                case "collection": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.collection."+collectionType.toLowerCase());
                    runner.variable("contentTypeVal", StringUtils.capitalize(collectionType));
                    break;
                }
                case "textbook": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.collection."+collectionType.toLowerCase());
                    runner.variable("contentTypeVal", StringUtils.capitalize(collectionType));
                    break;
                }
                case "course": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.collection."+collectionType.toLowerCase());
                    runner.variable("contentTypeVal", StringUtils.capitalize(collectionType));
                    break;
                }
                case "lessonplan": {
                    runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
                    runner.variable("codeVal", "kp.ft.collection."+collectionType.toLowerCase());
                    runner.variable("contentTypeVal", StringUtils.capitalize(collectionType));
                    break;
                }
            }
            return createContent(runner, runner.testContext, null, CREATE_COLLECTION_CONTENT_EXPECT_200, headers);
        }
        data.put("content_id", contentId);
        data.put("versionKey", versionKey);
        return data;
    }

    //TODO: Update File Name for All.
    public static Map<String, Object> uploadResourceContent(BaseCitrusTestRunner runner, String contentId, String mimeType, Map<String, Object> headers) {
        if (StringUtils.isNotBlank(mimeType) && StringUtils.isNotBlank(contentId)) {
            switch (mimeType.toLowerCase()) {
                case "application/pdf": {
                    runner.testContext.setVariable("fileNameValue", "sample.pdf");
                    break;
                }
                case "application/vnd.ekstep.ecml-archive": {
                    runner.testContext.setVariable("fileNameValue", "sample.pdf");
                    break;
                }
                case "application/vnd.ekstep.html-archive": {
                    runner.testContext.setVariable("fileNameValue", "sample.pdf");
                    break;
                }
                case "application/vnd.ekstep.h5p-archive": {
                    runner.testContext.setVariable("fileNameValue", "sample.pdf");
                    break;
                }
                case "application/vnd.ekstep.plugin-archive": {
                    runner.testContext.setVariable("fileNameValue", "sample.pdf");
                    break;
                }
                case "video/x-youtube": {
                    runner.testContext.setVariable("fileNameValue", "sample.pdf");
                    break;
                }
            }
            return uploadContent(runner, runner.testContext, UPLOAD_RESOURCE_CONTENT_EXPECT_200, contentId, headers);
        }
        return null;
    }

    public static Map<String, Object> uploadAssetContent(BaseCitrusTestRunner runner, String contentId, String mimeType, Map<String, Object> headers) {
        if (StringUtils.isNotBlank(mimeType) && StringUtils.isNotBlank(contentId)) {
            switch (mimeType.toLowerCase()) {
                case "image/jpeg": {
                    runner.testContext.setVariable("fileNameValue", "sample.jpeg");
                    break;
                }
                case "image/png": {
                    runner.testContext.setVariable("fileNameValue", "sample.png");
                    break;
                }
                case "image/jpg": {
                    runner.testContext.setVariable("fileNameValue", "sample.jpg");
                    break;
                }
                case "video/mp4": {
                    runner.testContext.setVariable("fileNameValue", "sample.mp4");
                    break;
                }
                case "video/webm": {
                    runner.testContext.setVariable("fileNameValue", "sample.webm");
                    break;
                }
                case "video/mpeg": {
                    runner.testContext.setVariable("fileNameValue", "sample.mpeg");
                    break;
                }
                case "audio/mp3": {
                    runner.testContext.setVariable("fileNameValue", "sample.mp3");
                    break;
                }
            }
            return uploadContent(runner, runner.testContext, UPLOAD_ASSET_CONTENT_EXPECT_200, contentId, headers);
        }
        return null;
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
            System.out.println("Static Payload Used With Dynamic Values...");
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

    private static Map<String, Object> uploadContent(BaseCitrusTestRunner runner, TestContext testContext, String testName, String contentId, Map<String, Object> headers) {
        Map<String, Object> data = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(testName)) {
            System.out.println("Static Payload Used With Dynamic Values...");
            runner.getTestCase().setName(testName);
            runner.http(
                    builder ->
                            TestActionUtil.processMultipartRequest(
                                    testContext,
                                    builder,
                                    Constant.KP_ENDPOINT,
                                    CONTENT_PAYLOAD_DIR,
                                    testName,
                                    APIUrl.UPLOAD_CONTENT+contentId,
                                    Constant.REQUEST_FORM_DATA,
                                    getHeaders(headers),
                                    runner.getClass().getClassLoader()
                            ));

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
            data.put("content_url", (String) result.get("content_url"));
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
