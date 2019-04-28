package com.xly.mall.common.base;

import org.slf4j.Logger;

public class LogUtil {

	public static final String LOG_ID = "logId";
	
	private static final ThreadLocal<String> logContext = new ThreadLocal<String>();

	public static void log(Logger log, String info) {
		String logId = getLogId();
		log.info(logId + " " + info);
	}

	public static void log(Logger log, String logId, String info) {
		log.info(logId + " " + info);
	}
	
	public static String getLogId() {
		String logId = (String) logContext.get();

		if (StringUtil.isBlank(logId)) {
			logId = UUIDUtil.getUUID();
			logContext.set(logId);
		}

		return logId;
	}

	public static void clean() {
		logContext.remove();
	}
}
