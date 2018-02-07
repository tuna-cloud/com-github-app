package com.github.app.api.runner;

import com.github.app.api.services.SystemOperationService;
import com.github.app.api.services.impl.MySqlOperationServiceImpl;
import com.github.app.api.utils.ConfigLoader;
import com.github.app.utils.Runner;
import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BackupMySqlDatabaseRunner implements Runner {
    private Logger logger = LogManager.getLogger(BackupMySqlDatabaseRunner.class);

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
            JsonObject config = ConfigLoader.getServerCfg();

            SystemOperationService operationService = new MySqlOperationServiceImpl();
            operationService.backup(config);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("err", e);
        }
    }
}
