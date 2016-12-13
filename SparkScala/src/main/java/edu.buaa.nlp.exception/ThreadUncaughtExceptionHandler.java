package edu.buaa.nlp.exception;

import edu.buaa.nlp.util.ExceptionUtil;
import org.apache.log4j.Logger;

import java.lang.Thread.UncaughtExceptionHandler;

public class ThreadUncaughtExceptionHandler implements UncaughtExceptionHandler {

	private static Logger logger=Logger.getLogger(ThreadUncaughtExceptionHandler.class);

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		logger.error("非受检异常："+t.getName()+"---"+ExceptionUtil.getExceptionTrace(e));
	}
	
}
