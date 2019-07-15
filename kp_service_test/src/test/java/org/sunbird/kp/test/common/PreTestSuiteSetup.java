package org.sunbird.kp.test.common;

import com.consol.citrus.dsl.design.TestDesigner;
import com.consol.citrus.dsl.design.TestDesignerBeforeSuiteSupport;

/**
 * Test Data Cleanup Setup
 * @author Kumar Gauraw
 */
public class PreTestSuiteSetup extends TestDesignerBeforeSuiteSupport {

    @Override
    public void beforeSuite(TestDesigner testDesigner) {
        System.out.println("PreTestSuiteSetup --> beforeSuite() Called....");
    }
}
