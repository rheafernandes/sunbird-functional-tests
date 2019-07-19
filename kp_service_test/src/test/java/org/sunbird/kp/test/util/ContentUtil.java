package org.sunbird.kp.test.util;

import com.consol.citrus.context.TestContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.sunbird.kp.test.common.APIUrl;
import org.sunbird.kp.test.common.AppConfig;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.common.TestActionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

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
    private static final String REVIEW_RESOURCE_CONTENT_EXPECT_200 = "reviewResourceContentExpect200";
    private static final String UPDATE_RESOURCE_CONTENT_EXPECT_200 = "updateResourceContentExpect200";
    private static final String PUBLISH_CONTENT_EXPECT_200 = "publishContentExpect200";

    private static final ObjectMapper mapper = new ObjectMapper();

    // TODO: Add all the workflows for all test objects possible (Of Resource Type and Collection Type)
    private static final String contentWorkFlows = "{\n" +
            "\t\"contentInDraft\" : [],\n" +
            "\t\"contentInDraftUpdated\" : [\"Update\"],\n" +
            "\t\"contentInReview\" : [\"Upload\", \"Review\"],\n" +
            "\t\"contentInLive\": [\"Upload\", \"Publish\"],\n" +
            "\t\"contentInUnlisted\": [\"Upload\", \"Unlisted\"],\n" +
            "\t\"contentInLiveImageDraft\" : [\"Upload\", \"Publish\", \"Update\"],\n" +
            "\t\"contentInLiveImageReview\" : [\"Upload\", \"Publish\", \"Update\", \"Review\"]\n" +
            "\t\"contentInFlagged\" : [\"Upload\", \"Publish\", \"Flag\"],\n" +
            "\t\"contentInFlagDraft\" : [\"Upload\", \"Publish\", \"Flag\", \"AcceptFlag\"],\n" +
            "\t\"contentInFlagReview\" : [\"Upload\", \"Publish\", \"Flag\", \"AcceptFlag\", \"Review\"],\n" +
            "}";


    public static Map<String, Object> prepareResourceContent(String type, BaseCitrusTestRunner runner, String payload,
                                                             String mimeType, Map<String, Object> headers) {
        Map contentWorkMap = null;
        Map contentMap = ContentUtil.createResourceContent(runner, payload, mimeType, headers);
        Map<String, Object> result = new HashMap<>();
        String contentId = (String) contentMap.get("content_id");
        result.put("content_id", contentId);
        result.put("versionKey", contentMap.get("versionKey"));
        runner.variable("versionKeyVal", contentMap.get("versionKey"));
        try {
            contentWorkMap = mapper.readValue(contentWorkFlows, new TypeReference<Map<String, Object>>() {
            });
            List contentWorkList = (List<String>) contentWorkMap.get(type);
            Map<String, Supplier<Map<String, Object>>> actionMap = new HashMap<String, Supplier<Map<String, Object>>>() {
                {
                    put("Upload", () -> uploadResourceContent(runner, contentId, mimeType, headers));
                    put("Publish", () -> publishContent(runner, payload, "listed", contentId, headers));
                    put("Review", () -> reviewContent(runner, payload, REVIEW_RESOURCE_CONTENT_EXPECT_200, contentId, headers));
                    put("Update", () -> updateContent(runner, payload, UPDATE_RESOURCE_CONTENT_EXPECT_200, contentId, headers));
                    put("Unlisted", () -> publishContent(runner, payload, "unlisted", contentId, headers));
                }
            };
            if (!CollectionUtils.isEmpty(contentWorkList))
                contentWorkList.forEach(action -> {
                    Map response = actionMap.get(action).get();
                    if (StringUtils.isNotBlank((String) response.get("versionKey"))) {
                        result.put("versionKey", response.get("versionKey"));
                        runner.variable("versionKeyVal", response.get("versionKey"));
                    }
                    if (StringUtils.isNotBlank((String) response.get("content_url")))
                        result.put("content_url", response.get("content_url"));
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



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
            runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
            runner.variable("mimeTypeVal", mimeType);
            String[] mimeTypeArray = mimeType.toLowerCase().split("/");
            switch (mimeTypeArray[0]) {
                case "image": {
                    runner.variable("codeVal", "kp.ft.asset." + mimeTypeArray[1]);
                    runner.variable("mediaTypeVal", mimeTypeArray[0]);
                    break;
                }
                case "video": {
                    runner.variable("codeVal", "kp.ft.asset." + mimeTypeArray[1]);
                    runner.variable("mediaTypeVal", mimeTypeArray[0]);
                    break;
                }
                case "audio": {
                    runner.variable("codeVal", "kp.ft.asset." + mimeTypeArray[1]);
                    runner.variable("mediaTypeVal", mimeTypeArray[0]);
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
            runner.variable("idVal", String.valueOf(System.currentTimeMillis()));
            switch (collectionType.toLowerCase()) {
                case "collection": {
                    runner.variable("codeVal", "kp.ft.collection."+collectionType.toLowerCase());
                    runner.variable("contentTypeVal", StringUtils.capitalize(collectionType));
                    break;
                }
                case "textbook": {
                    runner.variable("codeVal", "kp.ft.collection."+collectionType.toLowerCase());
                    runner.variable("contentTypeVal", StringUtils.capitalize(collectionType));
                    break;
                }
                case "course": {
                    runner.variable("codeVal", "kp.ft.collection."+collectionType.toLowerCase());
                    runner.variable("contentTypeVal", StringUtils.capitalize(collectionType));
                    break;
                }
                case "lessonplan": {
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
                    runner.testContext.setVariable("fileNameValue", "sample_ecml.zip");
                    break;
                }
                case "application/vnd.ekstep.html-archive": {
                    runner.testContext.setVariable("fileNameValue", "sample_Html.zip");
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

    public static Map<String, Object> publishContent(BaseCitrusTestRunner runner, String payload, String publishType, String contentId, Map<String, Object> headers) {
        final String url = StringUtils.equalsIgnoreCase("unlisted",publishType.toLowerCase())?(APIUrl.UNLISTED_PUBLISH_CONTENT + contentId) : (APIUrl.PUBLIC_PUBLISH_CONTENT + contentId);
        final TestContext testContext = runner.testContext;
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
        } else {
            runner.getTestCase().setName(PUBLISH_CONTENT_EXPECT_200);
            runner.http(
                    builder ->
                            TestActionUtil.processPostRequest(
                                    builder,
                                    Constant.KP_ENDPOINT,
                                    CONTENT_PAYLOAD_DIR,
                                    PUBLISH_CONTENT_EXPECT_200,
                                    url,
                                    Constant.REQUEST_JSON,
                                    Constant.CONTENT_TYPE_APPLICATION_JSON,
                                    getHeaders(headers)));
        }
        runner.http(
                builder ->TestActionUtil.getResponse(
                        builder, Constant.KP_ENDPOINT, "", PUBLISH_CONTENT_EXPECT_200, HttpStatus.OK, "", null));
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
            result = objectMapper.readValue(testContext.getVariable("result"), new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            System.out.println("Exception Occurred While parsing context variable : " + e);
        }
        Map<String, Object> data = new HashMap<String, Object>();
        if (MapUtils.isNotEmpty(result))
            data.put("content_id", result.get("node_id"));
        runner.sleep(Constant.PUBLISH_WAIT_TIME);
        return data;
    }

    /**
     * This method can review content with a static payload.
     * TODO: NEED TO ADD FOR DYNAMIC PAYLOAD
     * @param runner
     * @param payload
     * @param testName
     * @param contentId
     * @param headers
     * @return
     */

    public static Map<String, Object> reviewContent(BaseCitrusTestRunner runner, String payload, String testName, String contentId, Map<String, Object> headers) {
        final TestContext testContext = runner.testContext;
        final String url = APIUrl.REVIEW_CONTENT + contentId;
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
        } else if (StringUtils.isNotBlank(testName)) {
            runner.getTestCase().setName(testName);
            runner.http(
                    builder ->
                            TestActionUtil.processPostRequest(
                                    builder,
                                    Constant.KP_ENDPOINT,
                                    CONTENT_PAYLOAD_DIR,
                                    testName,
                                    url,
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
            result = objectMapper.readValue(testContext.getVariable("result"), new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            System.out.println("Exception Occurred While parsing context variable : " + e);
        }
        Map<String, Object> data = new HashMap<String, Object>();
        if (MapUtils.isNotEmpty(result))
            data.put("content_id", result.get("node_id"));

        return data;
    }

    /**
     * This method can update content with static payload
     * TODO: Need to add dynamic payload
     * @param runner
     * @param payload
     * @param testName
     * @param contentId
     * @param headers
     * @return
     */
    public static Map<String, Object> updateContent(BaseCitrusTestRunner runner, String payload, String testName, String contentId, Map<String, Object> headers) {
        final String url = APIUrl.UPDATE_CONTENT + contentId;
        final TestContext testContext = runner.testContext;
        if (StringUtils.isNotBlank(payload)) {
            runner.http(
                    builder ->
                            TestActionUtil.getPatchRequestTestAction(
                                    builder,
                                    Constant.KP_ENDPOINT,
                                    url,
                                    MediaType.APPLICATION_JSON.toString(),
                                    payload,
                                    getHeaders(headers)));
        } else if (StringUtils.isNotBlank(testName)) {
            runner.getTestCase().setName(testName);
            runner.http(
                    builder ->
                            TestActionUtil.processPatchRequest(
                                    builder,
                                    Constant.KP_ENDPOINT,
                                    CONTENT_PAYLOAD_DIR,
                                    testName,
                                    url,
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
            result = objectMapper.readValue(testContext.getVariable("result"), new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            System.out.println("Exception Occurred While parsing context variable : " + e);
        }
        Map<String, Object> data = new HashMap<String, Object>();
        if (MapUtils.isNotEmpty(result)) {
            data.put("content_id", result.get("node_id"));
            data.put("versionKey", result.get("versionKey"));
        }
        return data;
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
