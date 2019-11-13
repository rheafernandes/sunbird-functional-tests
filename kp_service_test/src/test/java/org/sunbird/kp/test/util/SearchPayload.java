package org.sunbird.kp.test.util;

public class SearchPayload {

	public static final String SEARCH_CONTENT_WITH_IDENTIFIER_AND_CHANNEL= "{\n" +
			"    \"request\": {\n" +
			"        \"filters\": {\n" +
			"            \"objectType\": \"Content\",\n" +
			"            \"identifier\": contentIdVal,\n" +
			"            \"channel\": \"channelIdVal\",\n" +
			"            \"status\": []\n" +
			"        }\n" +
			"    }\n" +
			"}";
}
