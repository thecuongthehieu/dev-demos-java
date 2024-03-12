package util;

import java.time.Duration;
import java.time.Instant;

public class Timestamp {
	private final Instant start;

	/** Constructs timestamp with current start time. */
	public Timestamp() {
		this.start = Instant.now();
	}

	/** Returns start time in milliseconds. */
	public Instant start() {
		return this.start;
	}

	/** Returns difference between current time and start time in milliseconds. */
	public long elapsed() {
		Instant end = Instant.now();
		return Duration.between(start, end).toMillis();
	}
}
