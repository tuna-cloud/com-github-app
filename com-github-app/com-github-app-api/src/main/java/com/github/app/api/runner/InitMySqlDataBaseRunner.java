package com.github.app.api.runner;

import com.github.app.api.services.SystemOperationService;
import com.github.app.api.services.impl.MySqlOperationServiceImpl;
import com.github.app.api.utils.ConfigLoader;
import com.github.app.utils.Runner;
import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InitMySqlDataBaseRunner implements Runner {
    private Logger logger = LogManager.getLogger(InitMySqlDataBaseRunner.class);

    @Override
    public String name() {
        return "initmysql";
    }

    @Override
    public void usage(StringBuilder builder) {
        builder.append("\t-name initmysql").append("\n");
        builder.append("\t\t install mysql database schemas").append("\n");
    }

    @Override
    public void start(String[] args) {
        try {
            JsonObject config = ConfigLoader.getServerCfg();

            SystemOperationService operationService = new MySqlOperationServiceImpl();
            operationService.install(config);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("err", e);
        }
    }
}
