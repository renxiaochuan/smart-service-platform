package com.logistics.platform.exception;


/**
 * 系统共用错误代码
 *
 *
 */
@Deprecated
public interface ErrorCode {

	/**
	 * 999
	 */
	String UNEXPECTED_ERROR = "999";

	/**
	 * 001
	 */
	String SAVE_EXCEPTION = "001";

	/**
	 * 002
	 */
	String SAVE_CONSTRAINT_VIOLATION = "002";

	/**
	 * 003
	 */
	String SAVE_NOT_NULL_PROPERTY_WITH_NULL = "003";

	/**
	 * 004
	 */
	String SAVE_DATA_EXCEPTION = "004";

	/**
	 * 005
	 */
	String DELETE_EXCEPTION = "005";

	/**
	 * 006
	 */
	String DELETE_CONSTRAINT_VIOLATION = "006";


	/**
	 * 007
	 */
	String DATA_MODIFIED_ERROR = "007";


	/**
	 * 008
	 */
	String NOPERMISSION_ERROR = "008";


	/**
	 * 009 非法请求
	 */
	String INCORRECT_REQUEST_ERROR = "009";

}
