package org.sunbird.integration.test.textbook.toc;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.TOCUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * Integration Test Cases for Textbook Toc Upload, Update & Download API's
 * @author Gauraw
 */
public class TextbookTocTest extends BaseCitrusTestRunner {

    private static final String TEMPLATE_DIR = "templates/textbook/toc";

    private static final String TEST_TOC_UPLOAD_WITHOUT_ACCESS_TOKEN = "testTocUploadFailureWithoutAccessToken";

    private static final String TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_TEXTBOOK = "testTocUploadSuccessWithValidFileAndTextbook";
    private static final String TEST_TOC_UPLOAD_WITH_VALID_FILE_URL_AND_TEXTBOOK = "testTocUploadSuccessWithValidFileUrlAndTextbook";
    private static final String TEST_TOC_UPDATE_WITH_VALID_FILE_AND_TEXTBOOK = "testTocUpdateSuccessWithValidFileAndTextbook";

    private static final String TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_TEXTBOOK_ID = "testTocUploadFailureWithValidFileAndInvalidTextbookId";
    private static final String TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_TEXTBOOK_MIME_TYPE = "testTocUploadFailureWithValidFileAndInvalidTextbookMimeType";
    private static final String TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_TEXTBOOK_CONTENT_TYPE = "testTocUploadFailureWithValidFileAndInvalidTextbookContentType";
    private static final String TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_TEXTBOOK_CHILDREN_EXISTS = "testTocUploadFailureWithValidFileAndInvalidTextbookChildrenExists";

    private static final String TEST_TOC_UPLOAD_BLANK_CSV_FILE = "testTocUploadFailureBlankCsvFile";
    private static final String TEST_TOC_UPLOAD_BLANK_CSV_FILE_HEADERS_ONLY = "testTocUploadFailureBlankCsvFileheadersOnly";
    private static final String TEST_TOC_UPLOAD_CSV_ROWS_EXCEED = "testTocUploadFailureCsvRowsExceed";
    private static final String TEST_TOC_UPLOAD_CSV_DUPLICATE_ROWS = "testTocUploadFailureCsvDuplicateRows";
    private static final String TEST_TOC_UPLOAD_CSV_INVALID_TEXTBOOK_NAME = "testTocUploadFailureCsvInValidTextbookName";
    private static final String TEST_TOC_UPLOAD_CSV_REQUIRED_FIELD_HEADER_MISSING = "testTocUploadFailureCsvRequiredFieldHeaderMissing";
    private static final String TEST_TOC_UPLOAD_CSV_REQUIRED_FIELD_DATA_MISSING = "testTocUploadFailureCsvRequiredFieldDataMissing";

    private static final String TEST_TOC_DOWNLOAD_VALID_TEXTBOOK_ID = "testTocDownloadSuccessValidTextbookId";
    private static final String TEST_TOC_DOWNLOAD_INVALID_TEXTBOOK_ID = "testTocDownloadFailureInvalidTextbookId";
    private static final String TEST_TOC_DOWNLOAD_INVALID_TEXTBOOK_CONTENT_TYPE = "testTocDownloadFailureInvalidTextbookContentType";
    private static final String TEST_TOC_DOWNLOAD_INVALID_TEXTBOOK_MIME_TYPE = "testTocDownloadFailureInvalidTextbookMimeType";
    private static final String TEST_TOC_DOWNLOAD_INVALID_TEXTBOOK_CHILDREN_NOT_EXIST = "testTocDownloadFailureInvalidTextbookChildrenNotExist";


    @Test(dataProvider = "tocUploadSuccessDataProvider")
    @CitrusParameters({"testName", "httpStatusCode", "isAuthRequired", "contentType"})
    @CitrusTest
    public void testTocUploadSuccess(
            String testName, HttpStatus httpStatusCode, boolean isAuthRequired, String contentType) {
        getTestCase().setName(testName);
        getAuthToken(this, isAuthRequired);

        performMultipartTest(
                this,
                TEMPLATE_DIR,
                testName,
                getTocUploadUrl(getContentId(contentType)),
                REQUEST_FORM_DATA,
                null,
                isAuthRequired,
                httpStatusCode,
                RESPONSE_JSON);
    }

    @Ignore
    @Test(dataProvider = "tocUploadFailureDataProvider")
    @CitrusParameters({"testName", "httpStatusCode", "isAuthRequired", "contentType"})
    @CitrusTest
    public void testTocUploadFailure(
            String testName, HttpStatus httpStatusCode, boolean isAuthRequired, String contentType) {
        getTestCase().setName(testName);
        getAuthToken(this, isAuthRequired);
        String textbookId = "";
        performMultipartTest(
                this,
                TEMPLATE_DIR,
                testName,
                getTocUploadUrl(getContentId(contentType)),
                REQUEST_FORM_DATA,
                null,
                isAuthRequired,
                httpStatusCode,
                RESPONSE_JSON);
    }


    @DataProvider(name = "tocUploadSuccessDataProvider")
    public Object[][] tocUploadSuccessDataProvider() {
        return new Object[][]{
                new Object[]{
                        TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_TEXTBOOK, HttpStatus.OK, true, "TextBook"
                }
        };
    }

    @DataProvider(name = "tocUploadFailureDataProvider")
    public Object[][] tocUploadFailureDataProvider() {
        return new Object[][]{
                new Object[]{
                        TEST_TOC_UPLOAD_WITHOUT_ACCESS_TOKEN, HttpStatus.UNAUTHORIZED, true, true
                },
                new Object[]{
                        TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_TEXTBOOK_ID, HttpStatus.BAD_REQUEST, true, "Identifier"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_TEXTBOOK_MIME_TYPE, HttpStatus.BAD_REQUEST, true, "Resource"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_TEXTBOOK_CONTENT_TYPE, HttpStatus.BAD_REQUEST, true, "Resource"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_TEXTBOOK_CHILDREN_EXISTS, HttpStatus.BAD_REQUEST, true, "Resource"
                },
                //TODO: Fix Children Exist Test Scenario
                /*new Object[]{
                        TEST_TOC_UPLOAD_WITH_VALID_FILE_AND_INVALID_TEXTBOOK_CHILDREN_EXISTS, HttpStatus.BAD_REQUEST, true, "TextBook"
                },*/
                new Object[]{
                        TEST_TOC_UPLOAD_BLANK_CSV_FILE, HttpStatus.BAD_REQUEST, true, "Identifier"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_BLANK_CSV_FILE_HEADERS_ONLY, HttpStatus.BAD_REQUEST, true, "Identifier"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_CSV_ROWS_EXCEED, HttpStatus.BAD_REQUEST, true, "Identifier"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_CSV_DUPLICATE_ROWS, HttpStatus.BAD_REQUEST, true, "TextBook"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_CSV_INVALID_TEXTBOOK_NAME, HttpStatus.BAD_REQUEST, true, "TextBook"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_CSV_REQUIRED_FIELD_HEADER_MISSING, HttpStatus.BAD_REQUEST, true, "TextBook"
                },
                new Object[]{
                        TEST_TOC_UPLOAD_CSV_REQUIRED_FIELD_DATA_MISSING, HttpStatus.BAD_REQUEST, true, "TextBook"
                }
        };

    }

    @DataProvider(name = "tocUpdateSuccessDataProvider")
    public Object[][] tocUpdateSuccessDataProvider() {
        return null;
    }

    @DataProvider(name = "tocUpdateFailureDataProvider")
    public Object[][] tocUpdateFailureDataProvider() {
        return null;
    }

    @DataProvider(name = "tocDownloadSuccessDataProvider")
    public Object[][] tocDownloadSuccessDataProvider() {
        return null;
    }

    @DataProvider(name = "tocDownloadFailureDataProvider")
    public Object[][] tocDownloadFailureDataProvider() {
        return null;
    }

    private String getTocUploadUrl(String contentId) {
        return getLmsApiUriPath("/api/textbook/v1/toc/upload", "/v1/textbook/toc/upload", contentId);
    }

    private String getTocDownloadUrl(String contentId) {
        return getLmsApiUriPath("/api/textbook/v1/toc/download", "/v1/textbook/toc/download", contentId);
    }

    private String getContentId(String contentType) {
        String contentId = "do_12345";
        if ("TextBook".equalsIgnoreCase(contentType))
            contentId = TOCUtil.createTextbook(this, testContext);
        else if ("Resource".equalsIgnoreCase(contentType))
            contentId = TOCUtil.createResourceContent(this, testContext);
        return contentId;
    }

}
