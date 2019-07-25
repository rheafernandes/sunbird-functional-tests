package org.sunbird.kp.test.util;

public class WorkflowConstants {
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
    public static final String collectionWorkFlows = "{\n" +
            "\"collectionDraft\":[],\n" +
            "\"collectionDraftUnits\":[\"Update\"],\n" +
            "\"collectionDraftUnitsResources\":[\"CreateResources\", \"Update\"],\n" +
            "}";

}
