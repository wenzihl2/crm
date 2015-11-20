/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.wzbuaa.crm.exception.user;

import com.wzbuaa.crm.exception.BaseException;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-11 下午8:19
 * <p>Version: 1.0
 */
@SuppressWarnings("serial")
public class UserException extends BaseException {

    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }

}
