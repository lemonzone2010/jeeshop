package org.hum.framework.hawaii.orm.exception;

public class HawaiiMapperException extends RuntimeException {

	private static final long serialVersionUID = 946829769621711935L;

	public HawaiiMapperException(String message) {
		super(message);
	}

    public HawaiiMapperException(String message, Throwable cause) {
        super(message, cause);
    }
}
