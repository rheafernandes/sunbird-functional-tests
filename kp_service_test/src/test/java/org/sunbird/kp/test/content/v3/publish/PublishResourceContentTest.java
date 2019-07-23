package org.sunbird.kp.test.content.v3.publish;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.apache.commons.collections4.MapUtils;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.content.v3.ContentV3Scenario;
import org.sunbird.kp.test.util.ContentUtil;
import org.sunbird.kp.test.util.PublishValidationUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Publish API Tests for Resource Content
 *
 * @author Kumar Gauraw
 */
public class PublishResourceContentTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/content/v3/publish";

    @Test(dataProvider = "publishValidResourceContent")
    @CitrusParameters({"testName", "mimeType"})
    @CitrusTest
    public void testPublishValidResourceContent(String testName, String mimeType) {
        getAuthToken(this, null);
        this.getTestCase().setName(testName);
        //String contentId = (String) ContentUtil.prepareResourceContent("contentInLive", this, null, mimeType, null).get("content_id");
        String contentId = (String) ContentUtil.createResourceContent(this, null, mimeType, null).get("content_id");
        String contentUrl = (String) ContentUtil.uploadResourceContent(this, contentId, mimeType, null).get("content_url");
        Assert.assertNotNull(contentUrl);
        ContentUtil.publishContent(this, null, "public", contentId, null);
        delay(this, 10000);
        Map<String, Object> contentMap = (Map<String, Object>) ContentUtil.readContent(this, contentId, "edit", null).get("content");
        Assert.assertTrue(MapUtils.isNotEmpty(contentMap));
        PublishValidationUtil.validateMetadata(contentMap, null);
        //TODO: Enable It after having method implementation and supply required valParams
        //PublishValidationUtil.validateEcarManifest((String)contentMap.get("downloadUrl"), new HashMap<String, Object>());
    }

    //TODO: Enable ecml and h5p after having proper file for upload.
    @DataProvider(name = "publishValidResourceContent")
    public Object[][] publishValidResourceContent() {
        return new Object[][]{
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_RESOURCE_PDF_CONTENT, "application/pdf"
                },
                /*new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_RESOURCE_ECML_CONTENT, "application/vnd.ekstep.ecml-archive"
                },*/
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_RESOURCE_HTML_CONTENT, "application/vnd.ekstep.html-archive"
                }/*,
                new Object[]{
                        ContentV3Scenario.TEST_PUBLISH_RESOURCE_H5P_CONTENT, "application/vnd.ekstep.h5p-archive"
                },*/
        };
    }

}
