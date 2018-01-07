package com.grahamsfault.stats.server.api.exception;

/**
 * Nfl Service exception about accessing player data
 */
public class NflServiceGameDataException extends NflServiceException {
	public NflServiceGameDataException(String message) {
		super(message);
	}
}
