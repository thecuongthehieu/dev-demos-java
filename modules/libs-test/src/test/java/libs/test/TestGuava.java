package libs.test;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Timestamp;

public class TestGuava {
	private static final int TOLERANCE = 10; // 10 millisecond
	private static final Logger LOGGER = LoggerFactory.getLogger(TestGuava.class);

	@Test
	public void testAcquire() {
		RateLimiter rateLimiter = RateLimiter.create(1);

		Timestamp ts = new Timestamp();

		rateLimiter.acquire();
		rateLimiter.acquire();

		long elapsedTime = ts.elapsed();
		LOGGER.info("Elapsed Time = " + elapsedTime);

		Assertions.assertTrue(Math.abs(elapsedTime - 1000) < TOLERANCE);
	}

	@Test
	public void testAcquirePermits() {
		RateLimiter rateLimiter = RateLimiter.create(0.5);

		rateLimiter.acquire(2);

		Timestamp ts = new Timestamp();

		rateLimiter.acquire(1);

		long elapsedTime = ts.elapsed();
		LOGGER.info("Elapsed Time = " + elapsedTime);

		Assertions.assertTrue(Math.abs(elapsedTime - 4000) < TOLERANCE);
	}
	@Test
	public void testRateChange() {
		RateLimiter rateLimiter = RateLimiter.create(1);

		Timestamp ts = new Timestamp();

		rateLimiter.acquire();
		rateLimiter.acquire();

		long elapsedTime = ts.elapsed();
		LOGGER.info("Elapsed Time = " + elapsedTime);

		Assertions.assertTrue(Math.abs(elapsedTime - 1000) < TOLERANCE);

		// Change rate
		rateLimiter.setRate(0.5);

		ts = new Timestamp();
		rateLimiter.acquire();
		rateLimiter.acquire();
		rateLimiter.acquire();

		elapsedTime = ts.elapsed();
		LOGGER.info("Elapsed Time = " + elapsedTime);

		Assertions.assertTrue(Math.abs(elapsedTime - 5000) < TOLERANCE);

	}
}
