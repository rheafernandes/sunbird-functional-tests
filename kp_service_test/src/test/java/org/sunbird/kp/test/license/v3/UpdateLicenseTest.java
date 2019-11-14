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

/**
 * Integration Test Cases for Update License API
 *
 * @author rheashalomannfernandes
 *
 * Number of Test cases :
 * Last Updated the Test cases on:
 */
public class UpdateLicenseTest extends BaseCitrusTestRunner {
    private static final String TEMPLATE_DIR = "templates/license/v3/update";
    // This map populates the id's and directories
    private static Map<String, String> dirIdMap = new HashMap<>();

    //Comment this out after populating the data
    @AfterClass
    public static void populateAssertionData() {
        TestSetupUtil.createDirectoriesForTestCases(dirIdMap, "validate.json", TEMPLATE_DIR);
    }

    @Test(dataProvider = "updateLicenseWithValidRequest")
    @CitrusParameters("testName")
    @CitrusTest
    public void testCreateResourceContentWithValidRequest(String testName) {
//        String identifier = (String) LicenseUtil.createLicense(this, null, null).get("identifier");
        dirIdMap.put(testName, "");

    }

    @DataProvider(name = "updateLicenseWithValidRequest")
    public Object[][] createLicenseWithValidRequest() {
        return new Object[][]{
                new Object[]{
                        LicenseV3Scenario.TEST_UPDATE_LICENSE_WITH_VALID_REQUEST
                },
                new Object[]{
                        LicenseV3Scenario.TEST_UPDATE_LICENSE_WITH_STATUS
                },
                new Object[]{
                        LicenseV3Scenario.TEST_UPDATE_LICENSE_WITH_NEW_METADATA
                },
                new Object[]{
                        LicenseV3Scenario.TEST_UPDATE_LICENSE_WITH_METADATA_NAME_CASE_MISMATCH
                },
                new Object[]{
                        LicenseV3Scenario.TEST_UPDATE_LICENSE_WITH_CODE
                },
                new Object[]{
                        LicenseV3Scenario.TEST_UPDATE_LICENSE_WITH_DATA_TYPE_MISMATCH
                },
                new Object[]{
                        LicenseV3Scenario.TEST_UPDATE_LICENSE_WITH_IDENTIFIER
                },
                new Object[]{
                        LicenseV3Scenario.TEST_UPDATE_LICENSE_WITH_INVALID_STATUS
                },
        };
    }
}
