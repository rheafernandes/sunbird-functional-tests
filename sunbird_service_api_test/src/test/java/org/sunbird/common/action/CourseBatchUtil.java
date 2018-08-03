package org.sunbird.common.action;

import com.consol.citrus.context.TestContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.sunbird.common.util.Constant;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.sunbird.integration.test.course.batch.CreateCourseBatchTest;

public class CourseBatchUtil {

    private static String openCourseBatchId ="";
    private static String inviteOnlyCourseBatchId ="";

    private static String getCreateCourseBatchUrl(BaseCitrusTestRunner runner) {
        return runner.getLmsApiUriPath("/api/course/v1/batch/create", "/v1/course/batch/create");
    }

    public static String getOpenCourseBatchId(BaseCitrusTestRunner runner, TestContext testContext) {
        if(StringUtils.isBlank(openCourseBatchId)){
            openCourseBatchId = getCourseBaychId(runner, testContext, CreateCourseBatchTest.TEST_NAME_CREATE_COURSE_BATCH_SUCCESS_OPEN);
        }
        return openCourseBatchId;
    }

    public static String getInviteOnlyCourseBatchId(BaseCitrusTestRunner runner, TestContext testContext) {
        if(StringUtils.isBlank(inviteOnlyCourseBatchId)){
            inviteOnlyCourseBatchId = getCourseBaychId(runner, testContext, CreateCourseBatchTest.TEST_NAME_CREATE_COURSE_BATCH_SUCCESS_INVITE_ONLY);
        }
        return inviteOnlyCourseBatchId;
    }

    private static String getCourseBaychId(BaseCitrusTestRunner runner, TestContext testContext, String testName){
        runner.http(
                builder ->
                        TestActionUtil.getPostRequestTestAction(
                                builder,
                                Constant.LMS_ENDPOINT,
                                CreateCourseBatchTest.TEMPLATE_DIR,
                                testName,
                                getCreateCourseBatchUrl(runner),
                                Constant.REQUEST_JSON,
                                MediaType.APPLICATION_JSON.toString(),
                                TestActionUtil.getHeaders(true)));
        runner.http(
                builder ->
                        TestActionUtil.getExtractFromResponseTestAction(
                                testContext,
                                builder,
                                Constant.LMS_ENDPOINT,
                                HttpStatus.OK,
                                "$.result.batchId",
                                "courseId"));
        return testContext.getVariable("courseId");
    }
}
