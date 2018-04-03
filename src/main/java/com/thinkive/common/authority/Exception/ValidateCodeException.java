package com.thinkive.common.authority.Exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Description ${DESCRIPTION}
 * @Author dengchangneng
 * @Create 2018-04-03-15:40
 **/
public class ValidateCodeException extends AuthenticationException {

    /**
     *
     */
    private static final long serialVersionUID = -7285211528095468156L;

    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
