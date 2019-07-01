package org.sunbird.kp.test.common;

import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * Base Runner Class
 * @author Kumar Gauraw
 */
public class BaseCitrusTestRunner extends TestNGCitrusTestRunner {

    @Autowired
    protected EndPointConfig.TestGlobalProperty config;

    @Autowired
    protected TestContext testContext;

    public static final String REQUEST_FORM_DATA = "request.params";
    public static final String REQUEST_JSON = "request.json";
    public static final String RESPONSE_JSON = "response.json";

    public static final String KP_ENDPOINT = "kpRestClient";
    public static final String KEYCLOAK_ENDPOINT = "keycloakTestClient";



    public BaseCitrusTestRunner() {}

    public void performGetTest(
            TestNGCitrusTestRunner runner,
            String templateDir,
            String testName,
            String requestUrl,
            Boolean isAuthRequired,
            HttpStatus responseCode,
            String responseJson) {
        getTestCase().setName(testName);
        getAuthToken(runner, isAuthRequired);
        runner.http(
                builder ->
                        TestActionUtil.performGetTest(
                                builder,
                                KP_ENDPOINT,
                                testName,
                                requestUrl,
                                TestActionUtil.getHeaders(isAuthRequired),
                                config));
        runner.http(
                builder ->
                        TestActionUtil.getResponseTestAction(
                                builder, KP_ENDPOINT, templateDir, testName, responseCode, responseJson));
    }

    public void performPostTest(
            TestNGCitrusTestRunner runner,
            String templateDir,
            String testName,
            String requestUrl,
            String requestJson,
            String contentType,
            boolean isAuthRequired,
            HttpStatus responseCode,
            String responseJson) {
        getTestCase().setName(testName);
        runner.http(
                builder ->
                        TestActionUtil.getPostRequestTestAction(
                                builder,
                                KP_ENDPOINT,
                                templateDir,
                                testName,
                                requestUrl,
                                requestJson,
                                contentType,
                                TestActionUtil.getHeaders(isAuthRequired)));

        runner.http(
                builder ->
                        TestActionUtil.getResponseTestAction(
                                builder, KP_ENDPOINT, templateDir, testName, responseCode, responseJson));
    }

    public void performPatchTest(
            TestNGCitrusTestRunner runner,
            String templateDir,
            String testName,
            String requestUrl,
            String requestJson,
            String contentType,
            boolean isAuthRequired,
            HttpStatus responseCode,
            String responseJson) {
        getTestCase().setName(testName);
        runner.http(
                builder ->
                        TestActionUtil.getPatchRequestTestAction(
                                builder,
                                KP_ENDPOINT,
                                templateDir,
                                testName,
                                requestUrl,
                                requestJson,
                                contentType,
                                TestActionUtil.getHeaders(isAuthRequired)));
        runner.http(
                builder ->
                        TestActionUtil.getResponseTestAction(
                                builder, KP_ENDPOINT, templateDir, testName, responseCode, responseJson));
    }

    public void performDeleteTest(
            TestNGCitrusTestRunner runner,
            String templateDir,
            String testName,
            String requestUrl,
            String requestJson,
            String contentType,
            boolean isAuthRequired,
            HttpStatus responseCode,
            String responseJson) {
        getTestCase().setName(testName);
        runner.http(
                builder ->
                        TestActionUtil.getDeleteRequestTestAction(
                                builder,
                                KP_ENDPOINT,
                                templateDir,
                                testName,
                                requestUrl,
                                requestJson,
                                contentType,
                                TestActionUtil.getHeaders(isAuthRequired)));
        runner.http(
                builder ->
                        TestActionUtil.getResponseTestAction(
                                builder, KP_ENDPOINT, templateDir, testName, responseCode, responseJson));
    }



    public void performMultipartTest(
            TestNGCitrusTestRunner runner,
            String templateDir,
            String testName,
            String requestUrl,
            String requestFile,
            Map<String, Object> requestHeaders,
            Boolean isAuthRequired,
            HttpStatus responseCode,
            String responseJson) {
        getTestCase().setName(testName);
        runner.http(
                builder ->
                        TestActionUtil.getMultipartRequestTestAction(
                                testContext,
                                builder,
                                KP_ENDPOINT,
                                templateDir,
                                testName,
                                requestUrl,
                                requestFile,
                                TestActionUtil.getHeaders(isAuthRequired, requestHeaders),
                                runner.getClass().getClassLoader(),
                                config));
        runner.http(
                builder ->
                        TestActionUtil.getResponseTestAction(
                                builder, KP_ENDPOINT, templateDir, testName, responseCode, responseJson));
    }







    public void getAuthToken(TestNGCitrusTestRunner runner, Boolean isAuthRequired) {

        if (isAuthRequired) {
            //runner.http(builder -> TestActionUtil.getTokenRequestTestAction(builder, KEYCLOAK_ENDPOINT));
            //runner.http(builder -> TestActionUtil.getTokenResponseTestAction(builder, KEYCLOAK_ENDPOINT));
            //TODO: Change logic to take user details in other way.
            getUserAuthToken(runner,config.getContentCreatorUser(),config.getContentCreatorPass());
        }
    }


    private void getUserAuthToken(TestNGCitrusTestRunner runner, String userName, String password) {
        runner.http(
                builder ->
                        TestActionUtil.getTokenRequestTestAction(
                                builder, KEYCLOAK_ENDPOINT, userName, password));
        runner.http(builder -> TestActionUtil.getTokenResponseTestAction(builder, KEYCLOAK_ENDPOINT));
    }


}
