package com.cai310.freemarker.pipeline;

public class PipeException extends RuntimeException {
	private static final long serialVersionUID = -6662116336848933447L;

	public PipeException() {
		super();
	}

	public PipeException(String message, Throwable cause) {
		super(message, cause);
	}

	public PipeException(String message) {
		super(message);
	}

	public PipeException(Throwable cause) {
		super(cause);
	}

}
