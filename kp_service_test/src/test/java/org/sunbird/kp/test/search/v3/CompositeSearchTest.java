package org.sunbird.kp.test.search.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang.StringUtils;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.common.Constant;
import org.sunbird.kp.test.util.CompositeSearchUtil;
import org.sunbird.kp.test.util.ContentUtil;
import org.sunbird.kp.test.util.SearchPayload;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Functional Test Cases for search-service (Composite Search Api)
 *
 * @author Kumar Gauraw
 */
public class CompositeSearchTest extends BaseCitrusTestRunner {

	private static final String TEMPLATE_DIR = "templates/search/v3/search";

	@Test
	@CitrusTest
	public void testSearchForTextBookHavingConsumeAs() throws JsonProcessingException {
		String testName = "testSearchForTextBookHavingConsumeAs";
		this.getTestCase().setName(testName);
		getAuthToken(this, Constant.CREATOR);
		// Prepare Data
		String channel1 = "test.channel-" + generateRandomDigits(5);
		String channel2 = "consume.channel-" + generateRandomDigits(5);
		Map<String, Object> header1 = new HashMap<String, Object>() {{
			put(Constant.X_CHANNEL_ID, channel1);
		}};
		Map<String, Object> header2 = new HashMap<String, Object>() {{
			put(Constant.X_CHANNEL_ID, channel2);
		}};
		String textbookId = (String) ContentUtil.createCollectionContent(this, null, "textbook", header1).get("content_id");
		System.out.println("Textbook Id : " + textbookId);
		String consumableTextbookId = (String) ContentUtil.createCollectionContent(this, null, "textbook", header2).get("content_id");
		String payload = "{\"request\":{\"content\":{\"consumeAs\":{\"channel\":\"channelIdVal\"}}}}".replace("channelIdVal", channel1);
		String updatedId = (String) ContentUtil.systemUpdate(this, testContext, consumableTextbookId, payload, testName, header2).get("content_id");
		Assert.assertTrue(StringUtils.isNotBlank(textbookId) && StringUtils.isNotBlank(updatedId));
		delay(this, 60000);
		//search content and validate
		String searchPayload = SearchPayload.SEARCH_CONTENT_WITH_IDENTIFIER_AND_CHANNEL.replace("contentIdVal", objectMapper.writeValueAsString(new ArrayList<String>() {{
			add(textbookId);
		}})).replace("channelIdVal", channel1);
		System.out.println("searchPayload : " + searchPayload);
		Map<String, Object> searchResult = CompositeSearchUtil.searchContent(this, payload, testName, null);
		int count = (int) searchResult.get("count");
		Assert.assertEquals(count, 2);
	}
}
