package com.github.app.api.utils;

import com.github.app.utils.ServerEnvConstant;
import io.vertx.core.json.JsonObject;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ConfigLoader {
    private static Logger logger = LogManager.getLogger(ConfigLoader.class);

    public static JsonObject getServerCfg() {
        String path = ServerEnvConstant.getAppServerCfgFilePath();
        try {
            String confFileContent = FileUtils.readFileToString(new File(path), "UTF-8");
            String rep = System.getenv(ServerEnvConstant.APP_HOME);
            if(System.getProperty("os.name").toLowerCase().startsWith("win")) {
                rep = rep.replace("\\", File.separator + File.separator + File.separator + File.separator);
            }
            confFileContent = confFileContent.replaceAll("\\$\\{" + ServerEnvConstant.APP_HOME + "}", rep);
            logger.info("server.json:" + confFileContent);
            return new JsonObject(confFileContent);
        }  catch (Exception e) {
            logger.error("server.json file missing", e);
        }
        System.exit(-1);
        return null;
    }
}
