package org.sunbird.kp.test.util;

/**
 * @author : Rhea Fernandes
 */

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * This is an util method, which will return a test object
 */

public class ContentWorkFlow extends ContentUtil {
    public static final ObjectMapper mapper = new ObjectMapper();

    // TODO: Add all the workflows for all test objects possible (Of Resource Type and Collection Type)
    private static final String contentWorkFlows = "{\n" +
            "\t\"contentInDraft\" : [],\n" +
            "\t\"contentInDraftUpdated\" : [\"Update\"],\n" +
            "\t\"contentInReview\" : [\"Upload\", \"Review\"],\n" +
            "\t\"contentInLive\": [\"Upload\", \"Publish\"],\n" +
            "\t\"contentInUnlisted\": [\"Upload\", \"Unlisted\"],\n" +
            "\t\"contentInLiveImageDraft\" : [\"Upload\", \"Publish\", \"Update\"],\n" +
            "\t\"contentInLiveImageReview\" : [\"Upload\", \"Publish\", \"Update\", \"Review\"]\n" +
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
}
