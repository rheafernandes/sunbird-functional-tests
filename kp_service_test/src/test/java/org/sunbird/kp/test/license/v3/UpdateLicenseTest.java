package org.sunbird.kp.test.license.v3;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.sunbird.kp.test.common.BaseCitrusTestRunner;
import org.sunbird.kp.test.util.TestSetupUtil;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class UpdateLicenseTest extends BaseCitrusTestRunner {
    private static final String TEMPLATE_DIR = "templates/license/v3/update";
    private static Map<String, String> dirIdMap = new HashMap<>();

    @AfterClass
    public static void populateAssertionData() {
        TestSetupUtil.createDirectoriesForTestCases(dirIdMap, "validate.json", TEMPLATE_DIR);
    }

    @Test(dataProvider = "createLicenseWithValidRequest")
    @CitrusParameters("testName")
    @CitrusTest
    public void testCreateResourceContentWithValidRequest(String testName) {

    }

    @DataProvider(name = "createLicenseWithValidRequest")
    public Object[][] createLicenseWithValidRequest() {
        return null;
    }
}
