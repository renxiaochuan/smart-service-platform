package com.logistics.platform.exception;

/**
 * 所有应用程序所自定义的 runtime exception 继承这个exception
 * <p>
 * 不需要强制客户端的程序处理的exception使用这个类别及自行定义的子类别,
 * 一般来说客户端(或正在操作系统的Usre)无法直接做错误还原时, 这个时候使用runtime exception,
 * 客户端程序可以选择处理或不处理而交由error page处理.
 * </p>
 * 
 *
 */
public class AppRuntimeException extends AbstractRuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AppRuntimeException() {
	}

	public AppRuntimeException(String message) {
		super(message);
	}

	public AppRuntimeException(Throwable cause) {
		super(cause);
	}

	public AppRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AppRuntimeException(String code, String explanation) {
		super(code, explanation);
	}

	public AppRuntimeException(String code, String explanation, Throwable cause) {
		super(code, explanation, cause);
	}

}
