package com.doudou.core.exception;

/**
 * @author: liwei
 * @version: 1.0.0
 * @email: <a href="mailto:liwei@pingpongx.com">联系作者</a>
 * @date: 2019-11-10
 */
public class RedisLockException extends RuntimeException {

    private static final long serialVersionUID = 8377201174950924685L;

    public RedisLockException() {
    }

    public RedisLockException(String message) {
        super(message);
    }
}
