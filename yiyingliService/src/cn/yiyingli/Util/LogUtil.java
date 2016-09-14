package cn.yiyingli.Util;

import org.apache.log4j.Logger;


public class LogUtil {

	public static void trace(String msg, Class<?> class1) {
		Logger log = Logger.getLogger(class1);
		log.trace(msg);
	}

	public static void debug(String msg, Class<?> class1) {
		Logger log = Logger.getLogger(class1);
		log.debug(msg);
	}

	public static void info(String msg, Class<?> class1) {
		Logger log = Logger.getLogger(class1);
		log.info(msg);
	}

	public static void warn(String msg, Class<?> class1) {
		Logger log = Logger.getLogger(class1);
		log.warn(msg);
	}

	public static void error(String msg, Class<?> class1) {
		Logger log = Logger.getLogger(class1);
		log.error(msg);
	}

	public static void fatal(String msg, Class<?> class1) {
		Logger log = Logger.getLogger(class1);
		log.fatal(msg);
	}
}
