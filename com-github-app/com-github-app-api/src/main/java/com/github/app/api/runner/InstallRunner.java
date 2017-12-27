package com.github.app.api.runner;

import com.github.app.api.services.SystemOperationService;
import com.github.app.api.services.impl.MySqlOperationServiceImpl;
import com.github.app.api.utils.CmdParase;
import com.github.app.utils.LogbackLoaderUtils;
import com.github.app.utils.Runner;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstallRunner implements Runner {
    private static Logger logger = LoggerFactory.getLogger(InstallRunner.class);

    @Override
    public void start(String[] args) {
        try {
            CmdParase.build(args);
            /**
             * set logback config to external config file
             */
            LogbackLoaderUtils.loadConfig(CmdParase.getInstance().getLogbackCfg());

            JsonObject config = CmdParase.getInstance().getServerCfg();

            SystemOperationService operationService = new MySqlOperationServiceImpl();
            operationService.install(config);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("err", e);
        }
    }
}