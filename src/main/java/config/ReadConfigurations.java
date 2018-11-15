package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadConfigurations {
    private static final File CONFIG_DIR = new File("src/main/resources/config/");
    private static Properties appProps = new Properties();

//    public void loadConfigs() {
        public static void loadConfigs() {
//        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = CONFIG_DIR.getAbsolutePath() + "/" + "app.properties";

        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return appProps.getProperty(key);
    }
}
