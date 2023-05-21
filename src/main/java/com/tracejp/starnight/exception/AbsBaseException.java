package com.tracejp.starnight.exception;

/**
 * <p><p/>
 *
 * @author traceJP
 * @since 2021/4/15 20:27
 */
public abstract class AbsBaseException extends RuntimeException {

    public AbsBaseException(String message) {
        super(message);
    }

    public AbsBaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
