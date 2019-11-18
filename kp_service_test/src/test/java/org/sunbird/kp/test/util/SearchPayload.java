package org.sunbird.kp.test.util;

public class SearchPayload {

	public static final String SEARCH_CONTENT_WITH_IDENTIFIER_AND_BOARD= "{\n" +
			"    \"request\": {\n" +
			"        \"filters\": {\n" +
			"            \"objectType\": \"Content\",\n" +
			"            \"identifier\": contentIdVal,\n" +
			"            \"board\": \"boardVal\",\n" +
			"            \"status\": []\n" +
			"        }\n" +
			"    }\n" +
			"}";
}
