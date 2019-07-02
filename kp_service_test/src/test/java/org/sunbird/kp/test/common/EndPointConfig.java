package org.sunbird.kp.test.common;

import com.consol.citrus.dsl.endpoint.CitrusEndpoints;
import com.consol.citrus.http.client.HttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * End Point Configuration Class
 * @author Kumar Gauraw
 */
@Configuration
public class EndPointConfig {

    @Bean
    public HttpClient kpRestClient() {
        return CitrusEndpoints.http()
                .client()
                .requestUrl(AppConfig.config.getString("kp_base_uri"))
                .build();
    }

    @Bean
    public HttpClient keycloakTestClient() {
        return CitrusEndpoints.http().client().requestUrl(AppConfig.config.getString("env_base_path") + APIUrl.USER_AUTH).build();
    }


    @Bean
    public TestGlobalProperty initGlobalValues() {
        TestGlobalProperty property = new TestGlobalProperty();
        //TODO: Revert back to kp_api_key
        property.setKpApiKey(AppConfig.config.getString("kp_api_key"));
        //property.setKpApiKey(AppConfig.config.getString("kp_private_api_key"));
        property.setKpBaseUri(AppConfig.config.getString("kp_base_uri"));
        property.setContentCreatorUser(AppConfig.config.getString("kp_creator_user"));
        property.setContentCreatorUser(AppConfig.config.getString("kp_creator_password"));
        property.setContentCreatorUser(AppConfig.config.getString("kp_reviewer_user"));
        property.setContentCreatorUser(AppConfig.config.getString("kp_reviewer_password"));
        return property;
    }


    public class TestGlobalProperty {

        private String kpApiKey;
        private String kpBaseUri;
        private String contentCreatorUser;
        private String contentCreatorPass;
        private String contentReviewerUser;
        private String contentReviewerPass;

        public String getKpApiKey() {
            return kpApiKey;
        }

        public void setKpApiKey(String kpApiKey) {
            this.kpApiKey = kpApiKey;
        }

        public String getKpBaseUri() {
            return kpBaseUri;
        }

        public void setKpBaseUri(String kpBaseUri) {
            this.kpBaseUri = kpBaseUri;
        }

        public String getContentCreatorUser() {
            return contentCreatorUser;
        }

        public void setContentCreatorUser(String contentCreatorUser) {
            this.contentCreatorUser = contentCreatorUser;
        }

        public String getContentCreatorPass() {
            return contentCreatorPass;
        }

        public void setContentCreatorPass(String contentCreatorPass) {
            this.contentCreatorPass = contentCreatorPass;
        }

        public String getContentReviewerUser() {
            return contentReviewerUser;
        }

        public void setContentReviewerUser(String contentReviewerUser) {
            this.contentReviewerUser = contentReviewerUser;
        }

        public String getContentReviewerPass() {
            return contentReviewerPass;
        }

        public void setContentReviewerPass(String contentReviewerPass) {
            this.contentReviewerPass = contentReviewerPass;
        }

        @Override
        public String toString() {
            return "TestGlobalProperty [kpBaseUri="
                    + kpBaseUri
                    + ", kpApiKey="
                    + kpApiKey
                    + ", contentCreatorUser="
                    + contentCreatorUser
                    + ", contentCreatorPass="
                    + contentCreatorPass
                    + ", contentReviewerUser="
                    + contentReviewerUser
                    + ", contentReviewerPass="
                    + contentReviewerPass
                    + "]";
        }
    }

}
