package com.grahamsfault.nfl.api.exception;

public abstract class NflServiceException extends Exception {
	public NflServiceException(String message) {
		super(message);
	}
}
