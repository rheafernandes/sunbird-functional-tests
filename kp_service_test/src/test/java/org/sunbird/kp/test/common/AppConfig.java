package org.sunbird.kp.test.common;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Application Configuration File.
 * @author Kumar Gauraw
 */
public class AppConfig {

    private static Config defaultConf = ConfigFactory.load();
    private static Config envConf = ConfigFactory.systemEnvironment();
    public static Config config = defaultConf.withFallback(envConf);

}
