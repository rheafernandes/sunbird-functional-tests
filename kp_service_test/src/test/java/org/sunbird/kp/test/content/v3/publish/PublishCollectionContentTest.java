package org.sunbird.kp.test.content.v3.publish;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.apache.commons.collections4.MapUtils;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.content.v3.ContentV3Scenario;
import org.sunbird.kp.test.util.ContentUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Publish API Tests for Collection Content
 *
 * @author Kumar Gauraw
 */
public class PublishCollectionContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/publish";

    @Test(dataProvider = "publishValidCollectionContent")
    @CitrusParameters({"testName", "collectionType"})
    @CitrusTest
    public void testPublishValidCollectionContent(String testName, String collectionType) {
        getAuthToken(this, null);
        this.getTestCase().setName(testName);
        String resourceId = (String) ContentUtil.prepareResourceContent("contentInLive", this, null, "application/pdf", null).get("content_id");
        System.out.println("Resource Id : " + resourceId);
        Map<String, Object> contentMap = (Map<String, Object>) ContentUtil.readContent(this, resourceId, null, null).get("content");
        Assert.assertTrue(MapUtils.isNotEmpty(contentMap));

        String textbookId = (String) ContentUtil.createCollectionContent(this, null, collectionType, null).get("content_id");
        System.out.println("Textbook Id : " + textbookId);
        ContentUtil.updateContentHierarchy(this, textbookId, "textbook", resourceId, null, null);
        ContentUtil.publishContent(this, null, "public", textbookId, null);
        Map<String, Object> textbookMap = (Map<String, Object>) ContentUtil.readCollectionHierarchy(this, textbookId).get("content");
        //TODO: Validate Metadata
        //PublishValidationUtil.validateMetadata(textbookMap, new HashMap<String, Object>());
        //TODO: Enable It after having method implementation and supply required valParams
        //PublishValidationUtil.validateEcarManifest((String)textbookMap.get("downloadUrl"), new HashMap<String, Object>());
    }

    @DataProvider(name = "publishValidCollectionContent")
    public Object[][] publishValidCollectionContent() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COLLECTION_WITH_RESOURCE_CONTENT, "collection"
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_TEXTBOOK_WITH_RESOURCE_CONTENT, "textbook"
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_COURSE_WITH_RESOURCE_CONTENT, "course"
                },
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_LESSONPLAN_WITH_RESOURCE_CONTENT, "lessonplan"
                },
        };
    }
}
