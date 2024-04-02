package libs.test;

import com.google.common.util.concurrent.RateLimiter;
import util.Timestamp;

import java.io.Serializable;

public class Main {
	public static void main(String[]args){
		System.out.println("Here is Libs Test");

		RateLimiter rateLimiter = RateLimiter.create(100);

		Timestamp ts = new Timestamp();

		for (int i = 0; i < 1000; ++i) {
			rateLimiter.acquire();
			//System.out.println(i + 1);
		}

		System.out.println(ts.elapsed());


	}
}
