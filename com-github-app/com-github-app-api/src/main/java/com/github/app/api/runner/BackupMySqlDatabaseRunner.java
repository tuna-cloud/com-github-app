package com.github.app.api.runner;

import com.github.app.api.services.SystemOperationService;
import com.github.app.api.services.impl.MySqlOperationServiceImpl;
import com.github.app.api.utils.CmdParase;
import com.github.app.utils.LogbackLoaderUtils;
import com.github.app.utils.Runner;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackupMySqlDatabaseRunner implements Runner {
    private static Logger logger = LoggerFactory.getLogger(InitMySqlDataBaseRunner.class);

    @Override
    public String name() {
        return "backupmysql";
    }

    @Override
    public void usage(StringBuilder builder) {
        builder.append("\t-name backupmysql").append("\n");
        builder.append("\t\tbackup mysql database schema and data").append("\n");
    }

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
            operationService.backup(config);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("err", e);
        }
    }
}
