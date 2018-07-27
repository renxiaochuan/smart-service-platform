package com.logistics.platform.exception;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * 
 *
 */
@Deprecated
public class ExceptionUtil {

	/**
	 * Check exception所包含的causes是否包含clazz类型的Exception
	 * 
	 * @param exception a Exception to Check with
	 * @param clazz A Exception Class
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean containsException(Exception exception, Class clazz) {
		if (ExceptionUtils.indexOfThrowable(exception, clazz) != -1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 取出exception所包含causes类型为clazz的Exception
	 * <p>
	 * 先调用ExceptionUtil.containsException()返回true时才执行这个method,
	 * 否则找不到时抛出异常IllegalArgumentException
	 * </p>
	 * 
	 * @param exception
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Throwable fetchExceptionWithClass(Exception exception, Class clazz) {
		int idx = ExceptionUtils.indexOfThrowable(exception, clazz);
		if (idx == -1) {
			throw new IllegalArgumentException("传入的exception所包含的causes不包含类别:" + clazz);
		}
		Throwable t = exception.getCause();
		while (idx > 1) {
			t = exception.getCause();
			idx--;
		}
		return t;
	}
	
	/**
	 * 使用在出错页面，取得出错信息
	 */
	public static String fectcErrorCodeAndExplanation(String message) {
		int idx = message.indexOf("!!!!!");
		if (idx > 0) {
			return message.substring(idx + 5);
		}
		return "";
	}
}
