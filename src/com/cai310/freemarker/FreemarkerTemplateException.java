package com.cai310.freemarker;

/**
 * FreemarkerException等价的异常类，不过继承之RuntimeException
 * 
 */
public class FreemarkerTemplateException extends RuntimeException {
	private static final long serialVersionUID = -5702818669940023641L;

	public FreemarkerTemplateException() {
		super();
	}

	public FreemarkerTemplateException(String message, Throwable cause) {
		super(message, cause);
	}

	public FreemarkerTemplateException(String message) {
		super(message);
	}

	public FreemarkerTemplateException(Throwable cause) {
		super(cause);
	}

}
