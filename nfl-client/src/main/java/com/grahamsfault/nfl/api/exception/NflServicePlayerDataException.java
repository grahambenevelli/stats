package com.grahamsfault.nfl.api.exception;

/**
 * Nfl Service exception about accessing player data
 */
public class NflServicePlayerDataException extends NflServiceException {
	public NflServicePlayerDataException(String message) {
		super(message);
	}
}
