package com.grahamsfault.nfl.api.exception;

/**
 * Abstract Exception for talking to the Nfl Service
 */
public abstract class NflServiceException extends Exception {
	public NflServiceException(String message) {
		super(message);
	}
}
