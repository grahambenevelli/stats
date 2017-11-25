package com.grahamsfault.nfl.stats.model;

/**
 * An abstract class to represent actual stats of a player
 */
public class GameImportLog {

	private final String eid;
	private final boolean playerIdImported;

	private GameImportLog(String eid, boolean playerIdImported) {
		this.eid = eid;
		this.playerIdImported = playerIdImported;
	}

	public static Builder builder() {
		return new Builder();
	}

	public String getEid() {
		return eid;
	}

	public boolean hasPlayerIdImported() {
		return playerIdImported;
	}

	public static class Builder {

		private String eid;
		private boolean playerIdImported;

		public GameImportLog build() {
			return new GameImportLog(
					this.eid,
					this.playerIdImported
			);
		}

		public Builder eid(String eid) {
			this.eid = eid;
			return this;
		}

		public Builder playerIdImported(boolean playerIdImported) {
			this.playerIdImported = playerIdImported;
			return this;
		}
	}
}
