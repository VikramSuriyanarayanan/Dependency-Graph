package com.cerner.devcenter.dependencygraph.exceptions;

/**
 * Exception Class for Invalid mavenObjects. 
 * <p>
 * This exception will be thrown when inputStream to Java Object conversion fails unexpectedly during runtime.
 * @author Vikram Suriyanarayanan
 *
 */
@SuppressWarnings("serial")
public class InvalidMavenObjectException extends RuntimeException{

	public InvalidMavenObjectException(String message, Throwable exception) {
		super(message, exception);
	}

}
