package com.logistics.platform.exception;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class ErrorCodeHelper {

	private static ErrorCodeHelper singleton = new ErrorCodeHelper();

	private Map<String, String> msgMap = new HashMap<String, String>();

	static {

	}



	protected ErrorCodeHelper() {
		init();
	}



	protected void init() {
		// 05000 EAO
		add(ErrorCode.SAVE_EXCEPTION, "信息保存失败 (entity={0}, id={1})");
		add(ErrorCode.SAVE_CONSTRAINT_VIOLATION, "信息无法保存(违反约束) (entity={0}, id={1})");
		add(ErrorCode.SAVE_NOT_NULL_PROPERTY_WITH_NULL, "字段 {0} 不可为空值 (entity={1})");
		add(ErrorCode.SAVE_DATA_EXCEPTION, "保存时字段长度超长或者类型不正确 (entity={0}, message={1})");
		add(ErrorCode.DELETE_EXCEPTION, "信息删除时发生错误 (entity={0}, id={1})");
		add(ErrorCode.DELETE_CONSTRAINT_VIOLATION, "该数据被关联使用，不能删除 (entity={0}, id={1})");
		add(ErrorCode.DATA_MODIFIED_ERROR, "数据已被他人修改，不可操作(entity={0}, id={1})");
		add(ErrorCode.NOPERMISSION_ERROR, "无操作权限(entity={0}, id={1})");
		
		add(ErrorCode.INCORRECT_REQUEST_ERROR, "非法请求！");

		
		add(ErrorCode.UNEXPECTED_ERROR, "未知错误!");
	}



	public static ErrorCodeHelper getInstance() {
		return singleton;
	}



	protected void add(String code, String messageTemplate) {
		msgMap.put(code, messageTemplate);
	}



	public String getPatternMessage(String errorCode, Object... parameters) {
		String msgPattern = msgMap.get(errorCode);
		if (msgPattern == null) {
			return "未定义异常!";
		}

		return MessageFormat.format(msgPattern, parameters);
	}
}
