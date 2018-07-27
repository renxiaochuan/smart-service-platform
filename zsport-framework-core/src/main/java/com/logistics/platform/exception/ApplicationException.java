package com.logistics.platform.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * 所有应用程序所自行定义的 checked exception 继承这个exception
 * <p>
 * 需要强制客户端处理的exception才使用这个类别及自行定义的子类别,
 * 一般来说客户端程序(或存在操作系统的User)有机会做错误复原时，才使用checked exception.
 * </p>
 * 
 */
@Slf4j
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 1L;

	private static final ErrorCodeHelper errorCodeHelper = ErrorCodeHelper.getInstance();

	/**
	 * get error message by error code, and format it with specieid parameters.
	 * <p>
	 * the error code was defined in {@link ErrorCode}, and the error
	 * message(pattern) was defined in {@link ErrorCodeHelper}. you must refer
	 * to these class to know the message pattern for correct error message.
	 * </p>
	 * 
	 * @param errorCode
	 * @param params
	 * @return
	 * @see ErrorCode
	 * @see ErrorCodeHelper
	 */
	protected static String getErrorMessage(String errorCode, Object... params) {
		return errorCodeHelper.getPatternMessage(errorCode, params);
	}

	/**
	 * 异常代码
	 */
	private String code = "";

	/**
	 * 异常描述
	 */
	private String explanation = "";

	public ApplicationException() {
	}

	public ApplicationException(String message) {
		super(message);
		log.error(message);
	}

	public ApplicationException(Throwable cause) {
		super(cause);
		log.error("", cause);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
		log.error(message, cause);
	}

	public ApplicationException(String code, String explanation) {
		super(code + " - " + explanation);
		this.code = code;
		this.explanation = explanation;
		log.error(getMessage());
	}

	public ApplicationException(String code, String explanation, Throwable cause) {
		super(code + explanation, cause);
		this.code = code;
		this.explanation = explanation;
	}

	public String getFullMessage() {
		StringBuffer buf = new StringBuffer();
		buf.append("!!!!![Error Code: ");
		buf.append(getCode());
		buf.append("] \"");
		buf.append(getExplanation());
		buf.append("\" ");
		if (super.getMessage() != null) {
			buf.append(super.getMessage());
		}
		return buf.toString();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getCodeAndExplanation() {
		return code + " - " + explanation;
	}

}
