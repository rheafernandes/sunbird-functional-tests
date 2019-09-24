//package org.sunbird.kp.test.content.v3;
//
//import com.consol.citrus.annotations.CitrusTest;
//import com.consol.citrus.testng.CitrusParameters;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.collections4.MapUtils;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang.StringUtils;
//import org.sunbird.kp.test.common.BaseCitrusTestRunner;
//import org.sunbird.kp.test.util.ContentUtil;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//import com.rits.cloning.Cloner;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class GetHierarchyTest extends BaseCitrusTestRunner {
//    private static final String TEMPLATE_DIR = "templates/hierarchy/v3/get";
//
//    private final Map<String, String> identifierURLMap = new HashMap<String, String>() {{
////        put("do_312526596370825216240764", "https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_312526596370825216240764/itihaas-aanni-raajyshaastr-iyttaa-nvvii_1554515624484_do_312526596370825216240764_6.0_spine.ecar");
////        put("do_312526581812248576240484", "https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_312526581812248576240484/prisr-abhyaas-bhaag-1-iyttaa-cauthii_1548046278433_do_312526581812248576240484_9.0_spine.ecar");
////        put("do_312528252356567040253693", "https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_312528252356567040253693/prisr-abhyaas-iyttaa-tisrii_1554527782140_do_312528252356567040253693_7.0_spine.ecar");
////        put("do_312528261559533568155573", "https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_312528261559533568155573/bhuugol-iyttaa-saatvii_1554528295508_do_312528261559533568155573_6.0_spine.ecar");
////        put("do_312526596342243328142864", "https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_312526596342243328142864/itihaas-v-naagrikshaasny-iyttaa-saatvii_1554519300555_do_312526596342243328142864_6.0_spine.ecar");
////        put("do_31276878209125580812236", "https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_31276878209125580812236/test_1558689232309_do_31276878209125580812236_1.0_spine.ecar");
////        put("do_312528261235818496155570", "https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_312528261235818496155570/bhuugol-iyttaa-shaavii_1554528290185_do_312528261235818496155570_7.0_spine.ecar");
////        put("do_312528261678243840254512", "https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_312528261678243840254512/bhuugol-iyttaa-nvvii_1554320426396_do_312528261678243840254512_6.0_spine.ecar");
////        put("do_312528209262018560153224", "https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_312528209262018560153224/gnnit-iyttaa-dusrii_1554517868737_do_312528209262018560153224_7.0_spine.ecar");
////        put("do_312526596803018752142911", "https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_312526596803018752142911/prisr-abhyaas-bhaag-2-iyttaa-cauthii_1554527807482_do_312526596803018752142911_8.0_spine.ecar");
////        put("do_312528209279262720252122", "https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_312528209279262720252122/gnnit-iyttaa-cauthii_1552890990643_do_312528209279262720252122_11.0_spine.ecar");
////        put("do_312528209300701184153324", "https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_312528209300701184153324/gnnit-bhaag-1-iyttaa-9-vii_1554518919950_do_312528209300701184153324_8.0_spine.ecar");
////        put("do_312526596839809024241040", "https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_312526596839809024241040/prisr-abhyaas-bhaag-2-iyttaa-paacvii_1553494543097_do_312526596839809024241040_7.0_spine.ecar");
////        put("do_3123357600460800001602","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_3123357600460800001602/std-2-lesson-5-seasons_1531376415173_do_3123357600460800001602_3.0_spine.ecar");
////        put("do_31223664149528576021678","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_31223664149528576021678/angrjii-mraatthiituun-shikaa-m_1539244208757_do_31223664149528576021678_4.0_spine.ecar");
//
//
////        put("do_31241483168799129614865","https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_31241483168799129614865/tm4t2-pirrnt-naall-villlaa_1539251345797_do_31241483168799129614865_4.0_spine.ecar");
////        put("do_312241583228624896176","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_312241583228624896176/leapforwords-english-literacy-program-beta_1500029159794_do_312241583228624896176_2.0.ecar");
////        put("do_3124020536208261121765","https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_3124020536208261121765/m2fa18_1539249543989_do_3124020536208261121765_2.0_spine.ecar");
//////        put("do_312352489715515392249","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_312352489715515392249/session-3.-internet_1539245058219_do_312352489715515392249_2.0_spine.ecar");
////        put("do_3123327205955911682267","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_3123327205955911682267/lesson-5-school_m4c_1505463739076_do_3123327205955911682267_1.0.ecar");
//////        put("do_31241483423806259214933","https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_31241483423806259214933/tm1s2_1539252134562_do_31241483423806259214933_2.0_spine.ecar");
////        put("do_312275525889368064189","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_312275525889368064189/2.2-tiin-akssrii-shbd-erh_1498822020714_do_312275525889368064189_1.0.ecar");
////        put("do_312201725717536768255","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_312201725717536768255/word-duniya_1539243939926_do_312201725717536768255_2.0_spine.ecar");
////        put("do_31240717808327884822025","https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_31240717808327884822025/m2efa18_1539250773543_do_31240717808327884822025_2.0_spine.ecar");
////        put("do_312297939016597504150","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_312297939016597504150/animals-and-birds-copied_1501216192679_do_312297939016597504150_2.0.ecar");
////        put("do_30108386","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_30108386/gnnit-rsprshne_1482488500510_do_30108386_1.0.ecar");
////        put("do_3123327374507540482278","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_3123327374507540482278/6-ranis-family_1539245708474_do_3123327374507540482278_2.0_spine.ecar");
////        put("do_30103866","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_30103866/math-quiz-for-class-2_1539243653205_do_30103866_2.0_spine.ecar");
////        put("do_3123199810000404482449","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_3123199810000404482449/ranis-family_m4c_1539244665429_do_3123199810000404482449_2.0_spine.ecar");
//////       put("do_312352463379111936153","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_312352463379111936153/session-1-introduction-to-the-course_1539245044228_do_312352463379111936153_3.0_spine.ecar");
//////        put("do_312352521348579328174","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_312352521348579328174/session-6-inkscape_1539245731880_do_312352521348579328174_2.0_spine.ecar");
////        put("do_30103906","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_30103906/math-quiz-for-class-4_1539243813922_do_30103906_2.0_spine.ecar");
////        put("do_30103869","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_30103869/math-quiz-for-class-3_1539243806256_do_30103869_2.0_spine.ecar");
////        put("do_30103925","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_30103925/math-quiz-for-class-5_1539243681219_do_30103925_2.0_spine.ecar");
////        put("do_3122960110616821762300","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_3122960110616821762300/3.-animals-and-birds_1500980652128_do_3122960110616821762300_1.0.ecar");
////        put("do_312456724069564416111602","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_312456724069564416111602/gpf-lp-marathi_1539253475450_do_312456724069564416111602_2.0_spine.ecar");
////        put("do_3122725514256547841326","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_3122725514256547841326/2.1-svron-ko-vynjnon-se-jodddhne-pr-bnne-vaalii-aavaazerh_1498632279410_do_3122725514256547841326_1.0.ecar");
//////        put("do_312262778787160064149","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_312262778787160064149/5.9-phoniiks-pddhti-men-n-baitthne-vaale-shbd-e-se-smaapt-honevaale-shbderh_1539244484199_do_312262778787160064149_2.0_spine.ecar");
////        put("do_3123327399866941441311","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_3123327399866941441311/lesson-7-transport_1539244849376_do_3123327399866941441311_2.0_spine.ecar");
////        put("do_30099766","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_30099766/tvrgdkaagunnit_1539243625151_do_30099766_2.0_spine.ecar");
////        put("do_3124020511713853441697","https://ntpproductionall.blob.core.windows.net/ntp-content-production/ecar_files/do_3124020511713853441697/t2d9_1539248833746_do_3124020511713853441697_3.0_spine.ecar");
////        put("do_30099767","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_30099767/pvrgdkaagunnit_1539243750123_do_30099767_2.0_spine.ecar");
////        put("do_3123477412789452802307","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_3123477412789452802307/energized-textbook-examples_1507307780970_do_3123477412789452802307_1.0.ecar");
//////       put("do_312352486170533888248","https://ekstep-public-prod.s3-ap-south-1.amazonaws.com/ecar_files/do_312352486170533888248/session-2-email_1539245304065_do_312352486170533888248_2.0_spine.ecar");
//    }};
//
//    //    @Test(dataProvider = "getHierarchyWith")
////    @CitrusParameters({"testName", "requestUrl", "httpStatusCode", "userType", "valParams", "mimeType", "needImage", "workflow"})
////    @CitrusTest
////    public void testUpdateResourceContent(
////            String testName, String requestUrl, HttpStatus httpStatusCode, String userType,
////            Map<String, Object> valParams, String mimeType, Boolean needImage, String workflow) {
////        getAuthToken(this, userType);
////        Map<String, Object> map = ContentUtil.prepareResourceContent(workflow, this, null, mimeType, null);        String contentId = (String) map.get("content_id");
////        String versionKey = (String) map.get("versionKey");
////        this.variable("versionKeyVal", versionKey);
////        this.variable("contentIdVal", contentId);
////        contentId = needImage? contentId + IMAGE_SUFFIX : contentId;
////        performPatchTest(
////                this,
////                TEMPLATE_DIR,
////                testName,
////                requestUrl + contentId,
////                null,
////                REQUEST_JSON,
////                MediaType.APPLICATION_JSON,
////                httpStatusCode,
////                valParams,
////                RESPONSE_JSON
////        );
////    }
////    @DataProvider(name = "updateResourceContent")
////    public Object[][] updateResourceContent() {
////        return new Object[][]{
////                //Valid Requests (200) are here
////                new Object[]{
////                        ContentV3Scenario.TEST_UPDATE_RESOURCE_PDF_CONTENT_WITH_VALID_REQUEST, APIUrl.UPDATE_CONTENT, HttpStatus.OK, Constant.CREATOR, null, "application/pdf", false, "contentInDraft"
////                },
////
////
////        };
////    }
//    @Test(dataProvider = "getHierarchy")
//    @CitrusParameters({"identifier"})
//    @CitrusTest
//    public void testUpdateResourceContent(List<String> identifiers) {
//        Map<String, Object> pkgVersionMap = new HashMap<>();
//        List<String> invalidIdentifiers = new ArrayList<>();
//        FileWriter file = null;
//        try {
//            file = new FileWriter("/Users/rheashalomannfernandes/Sunbird/sunbird-functional-tests/amit/hierarchy.txt");
//            for (String identifier : identifiers) {
//                Map<String, Object> result = ContentUtil.readCollectionHierarchy(this, identifier);
//                List<Map<String, Object>> children = (List<Map<String, Object>>) ((Map<String, Object>) result.get("content")).get("children");
//                if (CollectionUtils.isNotEmpty(children)) {
//                    System.out.println("getHierarchy ****" + children);
//                    Map<String, Object> hierarchy = new HashMap<String, Object>() {{
//                        put("identifier", identifier);
//                        put("children", children);
//                    }};
//                    try {
//                        String hierarchyString = new ObjectMapper().writeValueAsString(hierarchy);
//                        System.out.println(identifier + " *** String *** " + hierarchyString);
////                        String query = "INSERT into prod_hierarchy_store.content_hierarchy( " +
////                                "SET hierarchy = '" + hierarchyString + "' " +
////                                "WHERE identifier = '" + identifier + ".img';";
//                        String query = "INSERT into prod_hierarchy_store.content_hierarchy(identifier, hierarchy) values ('" + identifier + ".img', '" + hierarchyString + "');";
//                        file.write(query + "\n\n");
//                        file.flush();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    invalidIdentifiers.add(identifier);
//                    pkgVersionMap.put(identifier, ((Map<String, Object>) result.get("content")).get("pkgVersion"));
//                }
//            }
//            System.out.println("All textbooks with no hierarchy" + invalidIdentifiers);
//            pkgVersionMap.keySet().forEach(key -> System.out.println(key + "->" + pkgVersionMap.get(key)));
//            constructHierarchy(identifierURLMap);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (null != file)
//                try {
//                    file.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//        }
//
//    }
//
//
//    @DataProvider(name = "getHierarchy")
//    public Object[][] getHierarchy() {
//        return new Object[][]{
//                //Valid Requests (200) are here
////                new Object[]{
////                        Arrays.asList("do_30100743", "do_30017131", "do_30022223", "do_30022225", "do_3122039697574461441591", "do_30070910", "do_30081227", "do_30081228", "do_30081229", "do_30081230", "do_30083004", "do_30084085", "do_30086024", "do_30086169", "do_30088286", "do_30091227", "do_30091777", "do_30091828", "do_30091835", "do_30091838", "do_30091844", "do_30019820", "do_30083030", "do_30099768", "do_30099769", "do_30099770", "do_30104195", "do_30108377", "do_30108449", "do_30108527", "do_30108551", "do_3121671920967352321257", "do_3123328543425331201333", "do_31225574758821068822343", "do_31225635724469862422539", "do_31225700791716249622739", "do_31225701754238566422751", "do_31225713162034380812682", "do_3123064860860088322909", "do_31236950878664294414641", "do_31237864211822182411632", "do_31238097277284352012180", "do_31239737313020313616281", "do_3124020349106667521531", "do_31241483460746444825019", "do_31241483460836556825020", "do_31241483460928307214942", "do_31241483461011865625021", "do_31241483461093785614943", "do_31241483461168332814944", "do_31241483461246976025022", "do_31241483461328896014945", "do_31241483461406720025023", "do_31241483461487001625024", "do_31241483461576294414946", "do_31241483461659852825025", "do_31241483461742592025026", "do_31241483461940019225027", "do_31245242237163110418167", "do_312526581846130688240509", "do_312526581812248576240484", "do_312526596309655552142847", "do_312526581859311616240510", "do_312526596342243328142864", "do_312526596370825216240764", "do_312526596839809024241040", "do_312526596803018752142911", "do_312528209262018560153224", "do_312528209279262720252122", "do_312528252356567040253693", "do_312528209300701184153324", "do_312528261559533568155573", "do_312528261235818496155570", "do_312528261678243840254512", "do_3125672107011194881582", "do_31259982560801587222661", "do_312605501694861312211114", "do_3126124924261171202421", "do_31261835498101145611414", "do_3126201538247966721244", "do_31269307669954560013698", "do_31269654608235724815137", "do_31269654128441753611272", "do_31279358887178240016880", "do_31279441155633152019589")
////                }
////                new Object[]{
////                        Arrays.asList("do_31276878209125580812236",
////                                "do_312526596370825216240764",
////                                "do_312526596309655552142847",
////                                "do_312526596342243328142864",
////                                "do_312528209279262720252122",
////                                "do_312528209262018560153224",
////                                "do_312528209300701184153324",
////                                "do_312528252356567040253693",
////                                "do_312526581812248576240484",
////                                "do_312526596803018752142911",
////                                "do_312526596839809024241040",
////                                "do_312605501694861312211114",
////                                "do_312528261678243840254512",
////                                "do_312528261235818496155570",
////                                "do_312528261559533568155573",
////                                "do_31259982560801587222661",
////                                "do_312526581846130688240509",
////                                "do_312526581859311616240510")
////                }
////
//////                new Object[]{
////                        Arrays.asList("do_3122725514256547841326", "do_312275525889368064189", "do_31225713162034380812682", "do_3122960110616821762300", "do_31225635724469862422539", "do_312262778787160064149", "do_3123327374507540482278", "do_30081227", "do_3126124924261171202421", "do_312297939016597504150", "do_3125672107011194881582", "do_30108377", "do_30070910", "do_30081230", "do_31261835498101145611414", "do_31245242237163110418167", "do_3123477412789452802307", "do_31279358887178240016880", "do_30108449", "do_312456724069564416111602", "do_3123064860860088322909", "do_312241583228624896176", "do_30100743", "do_3123327205955911682267", "do_31238097277284352012180", "do_3123327399866941441311", "do_31239737313020313616281", "do_3122039697574461441591", "do_31240717808327884822025", "do_3124020536208261121765", "do_30108551", "do_30086024", "do_30104195", "do_30103866", "do_30103869", "do_30103906", "do_30103925", "do_30086169", "do_30081229", "do_30083030", "do_3123199810000404482449", "do_31269307669954560013698", "do_312352463379111936153", "do_312352486170533888248", "do_312352489715515392249", "do_312352521348579328174", "do_31236950878664294414641", "do_3123357600460800001602", "do_30088286", "do_31269654608235724815137", "do_30081228", "do_3124020349106667521531", "do_3124020511713853441697", "do_31269654128441753611272", "do_31279441155633152019589", "do_3123328543425331201333", "do_30019820", "do_31241483461487001625024", "do_31241483461576294414946", "do_31241483461659852825025", "do_31241483461742592025026", "do_31241483461940019225027", "do_31241483460746444825019", "do_31241483423806259214933", "do_31241483460836556825020", "do_31241483460928307214942", "do_31241483461011865625021", "do_31241483168799129614865", "do_31241483461093785614943", "do_31241483461168332814944", "do_31241483461246976025022", "do_31241483461328896014945", "do_31241483461406720025023", "do_312201725717536768255", "do_31223664149528576021678", "do_31225700791716249622739", "do_30083004", "do_31225574758821068822343", "do_31225701754238566422751", "do_30108527", "do_30022225", "do_30091844", "do_30091227", "do_30099770", "do_3121671920967352321257", "do_30108386", "do_30091838", "do_30099766", "do_30099767", "do_30091835", "do_30017131", "do_30099768", "do_30084085", "do_30022223", "do_30091777", "do_30091828", "do_30099769"
////                        )
//////                }
//                new Object[] {
//                        Arrays.asList("do_3123314774135767042146","do_312526581831311360142794","do_31259888027666841621857"),
//                }
//        };
//    }
//
//    public static void constructHierarchy(Map<String, String> identifierURLMap) throws Exception {
//        FileWriter file = new FileWriter("/Users/rheashalomannfernandes/Sunbird/sunbird-functional-tests/amit/hierarchyInvalid.txt");
//        identifierURLMap.keySet().forEach(key -> {
//            Map<String, Object> manifestMap = getManifestMap(identifierURLMap.get(key));
//            Map<String, Object> childrenIdentifiersMap = manifestMap.keySet()
//                    .stream()
//                    .filter(identifier -> (CollectionUtils.isNotEmpty((List) ((Map<String, Object>) manifestMap.get(identifier)).get("children"))))
//                    .collect(Collectors.toMap(identifier -> identifier, identifier -> ((List<Map<String, Object>>) ((Map<String, Object>) manifestMap.get(identifier)).get("children"))
//                            .stream().map(child -> child.get("identifier")).collect(Collectors.toList())));
//            childrenIdentifiersMap.putAll(manifestMap.keySet()
//                    .stream().filter(identifier -> (CollectionUtils.isEmpty((List) ((Map<String, Object>) manifestMap.get(identifier)).get("children")))).collect(Collectors.toMap(identifier -> identifier, identifier -> new ArrayList())));
//            Map<String, Object> populatedChildMap = new HashMap<String, Object>();
//            Cloner cloner = new Cloner();
//            Map<String, Object> clonedMap = cloner.deepClone(childrenIdentifiersMap);
//            do {
//                childrenIdentifiersMap.keySet().forEach(key1 -> {
//                            if (CollectionUtils.isEmpty((List) childrenIdentifiersMap.get(key1))) {
//                                populatedChildMap.put(key1, manifestMap.get(key1));
//                                populatedChildMap.keySet().forEach(k -> clonedMap.remove(k));
//                            } else {
//                                if (isFullyPopulated((List) childrenIdentifiersMap.get(key1), populatedChildMap)) {
//                                    Map<String, Object> tempMap = (Map<String, Object>) manifestMap.get(key1);
//                                    List<Object> tempChildren = (List) ((List) childrenIdentifiersMap.get(key1)).stream().map(k -> populatedChildMap.get(k)).collect(Collectors.toList());
//                                    tempMap.put("children", tempChildren);
//                                    populatedChildMap.put(key1, tempMap);
//                                    populatedChildMap.keySet().forEach(k -> clonedMap.remove(k));
//                                }
//                            }
//                        }
//                );
//            } while (MapUtils.isNotEmpty(clonedMap));
//            try {
//                System.out.println("TextBook" + new ObjectMapper().writeValueAsString(populatedChildMap.get(key)));
//                List<Map<String, Object>> children = (List) ((Map<String, Object>) populatedChildMap.get(key)).get("children");
//                if (CollectionUtils.isNotEmpty(children)) {
//                    Map<String, Object> hierarchy = new HashMap<String, Object>() {{
//                        put("identifier", key);
//                        put("children", children);
//                    }};
//                    try {
//
//                        String hierarchyString = new ObjectMapper().writeValueAsString(hierarchy);
//                        String query = "INSERT into prod_hierarchy_store.content_hierarchy(identifier, hierarchy) values ('" + key + ".img', '" + hierarchyString + "');";
//
//                        file.write(query + "\n\n");
//                        file.flush();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    public static Boolean isFullyPopulated(List<String> children, Map populatedChildMap) {
//        Boolean flag = true;
//        for (String child : children) {
//            if (!populatedChildMap.containsKey(child))
//                flag = false;
//        }
//        return flag;
//    }
//
//    public static Map<String, Object> getManifestMap(String url) {
//        String ecarName = "test_bundle";
//        String downloadPath = "/data/testBundle/";
//        String ecarPath = downloadPath + ecarName + ".zip";
//        File[] files;
//        try {
//            // Download Ecar
//            FileUtils.copyURLToFile(new URL(url), new File(downloadPath + ecarName + ".zip"));
//            // Extract Ecar
//            File bundleExtract = new File(downloadPath + ecarName);
//            String bundleExtractPath = bundleExtract.getPath();
//
//            ZipFile bundleZip = new ZipFile(ecarPath);
//            bundleZip.extractAll(bundleExtractPath);
//
//            File fileName = new File(bundleExtractPath);
//            files = fileName.listFiles();
//            Map<String, Object> metadataMap = null;
//            for (File file : files) {
//                if (file.isFile() && file.getName().endsWith("json")) {
//                    Map<String, Object> manifestMap = new ObjectMapper().readValue(file, new TypeReference<Map<String, Object>>() {
//                    });
//                    Map<String, Object> archive = (Map<String, Object>) manifestMap.get("archive");
//                    List<Map<String, Object>> it = (List<Map<String, Object>>) archive.get("items");
//                    System.out.println("ITEMS SIZE" +it.size());
//                    List<Map<String, Object>> items = new ArrayList<>();
//                    for (Map<String, Object> item : it) {
//                        if (!StringUtils.equalsIgnoreCase((String) item.get("mimeType"), "application/vnd.ekstep.content-collection")
//                                && StringUtils.equalsIgnoreCase((String) item.get("contentType"), "Resource")) {
//                            item.put("visibility", "Default");
//                        }
//                        if (StringUtils.equalsIgnoreCase((String) item.get("mimeType"), "application/vnd.ekstep.content-collection")
//                                && StringUtils.equalsIgnoreCase((String) item.get("contentType"), "Collection") && StringUtils.equalsIgnoreCase((String) item.get("resourceType"), "Collection")) {
//                            item.put("visibility", "Default");
//                        }
//                        if (StringUtils.equalsIgnoreCase((String) item.get("mimeType"), "application/vnd.ekstep.content-collection")
//                                && (StringUtils.equalsIgnoreCase((String) item.get("contentType"), "TextBook") || StringUtils.equalsIgnoreCase((String) item.get("contentType"), "LessonPlan") || StringUtils.equalsIgnoreCase((String) item.get("contentType"), "Course"))) {
//                            item.put("visibility", "Default");
//                        }
//                        items.add(item);
//                    }
//                    metadataMap = items.stream().collect(Collectors.toMap(item -> (String) item.get("identifier"), item -> item));
//                }
//            }
//            FileUtils.deleteDirectory(new File(downloadPath));
//            return metadataMap;
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                FileUtils.deleteDirectory(new File(downloadPath));
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }
//        return null;
//    }
//}