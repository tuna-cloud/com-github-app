package com.github.app.utils;

import java.io.File;

public class ServerEnvConstant {
	public static final String APP_HOME = "APPLICATION_HOME";
	public static final String APP_LOG4J2_CFG_FILE = "APPLICATION_LOG4J2_CFG";
	public static final String APP_SERVER_CFG_FILE = "APPLICATION_SERVER_CFG";

	/**
	 * @return
	 */
	public static String getLog4j2CfgFilePath() {
		String appLog4j2CfgFile = System.getenv(APP_LOG4J2_CFG_FILE);

		if (appLog4j2CfgFile == null || appLog4j2CfgFile.length() < 1) {
			appLog4j2CfgFile = System.getenv(APP_HOME) + File.separator + "config" + File.separator + "log4j2.xml";
		}
		return appLog4j2CfgFile;
	}

	/**
	 * @return
	 */
	public static String getAppServerCfgFile() {
		String path = System.getenv(APP_SERVER_CFG_FILE);
		if (path == null || path.length() < 1) {
			path = System.getenv(APP_HOME) + File.separator + "config" + File.separator + "server.json";
		}
		return path;
	}

	/**
	 * 验证码存储位置
	 * 
	 * @return
	 */
	public static String getAppCaptchaTmpPath() {
		return System.getenv(APP_HOME) + File.separator + "web" + File.separator + "tmp";
	}
}
