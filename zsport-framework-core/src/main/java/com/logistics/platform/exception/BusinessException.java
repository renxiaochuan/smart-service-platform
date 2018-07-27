package com.logistics.platform.exception;

import cn.housecenter.dlfc.framework.exception.errorloader.ExceptionMessageProperties;
import com.thoughtworks.xstream.core.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

/**
 * 业务异常
 * <p>
 * <p>
 * 需要强制客户端处理的exception才使用这个类别及自行定义的子类别,
 * 一般来说客户端程序(或存在操作系统的User)有机会做错误复原时，才使用checked exception.
 * </p>
 *
 * @author gaosong
 */
@Slf4j
public class BusinessException extends Exception {

    private static final long serialVersionUID = 7150090552018913059L;

    public BusinessException() {
        super();
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public static BusinessException newInstance(String exceptionCode, Object... parameters) {
        String message = ExceptionMessageProperties.getExceptionMessage(exceptionCode, parameters);
        return new BusinessException(message);
    }

    public static BusinessException newInstance(Throwable cause, String exceptionCode, Object... parameters) {
        String message = ExceptionMessageProperties.getExceptionMessage(exceptionCode, parameters);
        return new BusinessException(message, cause);
    }

}
