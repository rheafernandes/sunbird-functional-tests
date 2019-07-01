package org.sunbird.kp.test.common;

import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.dsl.runner.TestRunnerAfterSuiteSupport;

/**
 * Test Data Cleanup Setup
 * @author Kumar Gauraw
 */
public class PostTestSuiteSetup extends TestRunnerAfterSuiteSupport {

    @Override
    public void afterSuite(TestRunner testRunner) {
        //TODO: Provide Test Data Cleanup  Here
        System.out.println("PostTestSuiteSetup --> afterSuite() is called....");
    }
}