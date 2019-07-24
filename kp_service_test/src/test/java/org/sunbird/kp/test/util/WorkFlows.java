package org.sunbird.kp.test.util;

import org.sunbird.kp.test.common.BaseCitrusTestRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class WorkFlows {
    // TODO: Add all the workflows for all test objects possible (Of Resource Type and Collection Type)
    public static final String contentWorkFlows = "{\n" +
            "\t\"contentInDraft\" : [],\n" +
            "\t\"contentUpload\" : [\"Upload\"],\n" +
            "\t\"contentDraftUpdated\" : [\"Update\"],\n" +
            "\t\"contentInReview\" : [\"Upload\", \"Review\"],\n" +
            "\t\"contentInLive\": [\"Upload\", \"Publish\"],\n" +
            "\t\"contentInLiveImageDraft\" : [\"Upload\", \"Publish\", \"Get\", \"Update\"],\n" +
            "\t\"contentInLiveImageReview\" : [\"Upload\", \"Publish\", \"Get\", \"Update\", \"Review\"],\n" +
            "\t\"contentInUnlisted\" : [\"Upload\", \"Unlisted\"],\n" +
            "\t\"contentInFlagged\" : [\"Upload\", \"Publish\", \"Get\", \"Flag\"],\n" +
            "\t\"contentInFlagDraft\" :  [\"Upload\", \"Publish\", \"Get\", \"Flag\", \"AcceptFlag\"],\n" +
            "\t\"contentInFlagReview\" :  [\"Upload\", \"Publish\", \"Get\", \"Flag\", \"AcceptFlag\", \"Review\"],\n" +
            "\t\"contentRetired\" : [\"Retire\"],\n" +
            "\t\"contentDiscarded\" : [\"Discard\"]\n" +
            "}";
    public static final String assetWorkFlows = "{\n" +
            "\"assetDraft\":[ ],\n" +
            "\"assetLive\":[\"Upload\"]\n" +
            "}";
    public static final String collectionWorkFlows = "";

    public static Map<String, Supplier<Map<String, Object>>> getContentWorkflowMap(BaseCitrusTestRunner runner, String contentId, String mimeType, Map<String,Object> headers) {
        return new HashMap<String, Supplier<Map<String, Object>>>() {
            {
                put("Upload", () -> ContentUtil.uploadResourceContent(runner, contentId, mimeType, headers));
                put("Publish", () -> ContentUtil.publishContent(runner, null, "listed", contentId, headers));
                put("Review", () -> ContentUtil.reviewContent(runner, null, null, contentId, headers));
                put("Update", () -> ContentUtil.updateContent(runner, null, null, contentId, headers));
                put("Unlisted", () -> ContentUtil.publishContent(runner, null, "unlisted", contentId, headers));
                put("Retire", () -> ContentUtil.retireContent(runner, contentId, headers));
                put("Discard", () -> ContentUtil.discardContent(runner, contentId, headers));
                put("Flag", () -> ContentUtil.flagContent(runner, null, null, contentId, headers));
                put("AcceptFlag", () -> ContentUtil.acceptFlagContent(runner, null, null, contentId, headers));
                put("RejectFlag", () -> ContentUtil.rejectFlagContent(runner, null, contentId, headers));
                put("Get", () -> ContentUtil.readContent(runner, contentId, null, null));
            }
        };
    }
    public static Map<String, Supplier<Map<String, Object>>> getAssetWorkFlowMap(BaseCitrusTestRunner runner, String contentId, String mimeType, Map<String,Object> headers) {
        return new HashMap<String, Supplier<Map<String, Object>>>() {
            {
                put("Upload", () -> ContentUtil.uploadAssetContent(runner, contentId, mimeType, headers));
                put("Retire", () -> ContentUtil.retireContent(runner, contentId, headers));
                put("Discard", () -> ContentUtil.discardContent(runner, contentId, headers));
                put("Flag", () -> ContentUtil.flagContent(runner, null, null, contentId, headers));
                put("AcceptFlag", () -> ContentUtil.acceptFlagContent(runner, null, null, contentId, headers));
                put("RejectFlag", () -> ContentUtil.rejectFlagContent(runner, null, contentId, headers));
                put("Get", () -> ContentUtil.readContent(runner, contentId, null, null));
            }
        };
    }

}
