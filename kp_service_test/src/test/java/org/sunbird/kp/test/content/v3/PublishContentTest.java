package org.sunbird.kp.test.content.v3;

import com.consol.citrus.annotations.CitrusTest;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.util.ContentUtil;
import org.sunbird.kp.test.util.DynamicPayload;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * Integration Test for Content Publish API
 * @author Kumar Gauraw
 */
public class PublishContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/publish";

    @Test
    @CitrusTest
    public void testPublishTextbookContentWithResourceHavingConceptRelation() {
        String resourceMimeType = "application/pdf";
        getAuthToken(this, null);

        String resourceId = (String) ContentUtil.createResourceContent(this, DynamicPayload.CREATE_RESOURCE_CONTENT_WITH_CONCEPT, null, null).get("content_id");
        System.out.println("Resource Content Id:" + resourceId);
        Map<String, Object> uploadResult = ContentUtil.uploadResourceContent(this, resourceId, resourceMimeType, null);
        Assert.assertNotNull(uploadResult.get("content_url"));
        ContentUtil.publishContent(this, null, "public", resourceId, null);
        Map<String, Object> contentMap = ContentUtil.readContent(this, resourceId);
        System.out.println("Content Map (Resource): " + contentMap);

        String collectionId = (String) ContentUtil.createCollectionContent(this, null, "collection", null).get("content_id");
        System.out.println("Collection Id : " + collectionId);
        ContentUtil.updateContentHierarchy(this, collectionId, "collection", resourceId, null, null);
        ContentUtil.publishContent(this, null, "public", collectionId, null);

        String textbookId = (String) ContentUtil.createCollectionContent(this, null, "textbook", null).get("content_id");
        System.out.println("Textbook Id : " + textbookId);
        ContentUtil.updateContentHierarchy(this, textbookId, "textbook", collectionId, null, null);
        ContentUtil.publishContent(this, null, "public", textbookId, null);

        this.getTestCase().setName("testPublishTextbookContentWithResourceHavingConceptRelation");
        delay(this, 60000);
        Map<String, Object> textbookMap = (Map<String, Object>) ContentUtil.readCollectionHierarchy(this, textbookId).get("content");
        Assert.assertNotNull(textbookMap);
        List<Map<String, Object>> list = (List<Map<String, Object>>) textbookMap.get("concepts");
        Map<String, Object> concept = list.get(0);
        Assert.assertEquals((String) textbookMap.get("status"), "Live");
        Assert.assertNotNull(textbookMap.get("variants"));
        Assert.assertEquals("LO53", (String) concept.get("identifier"));
    }

}
