package jedis.demos;

import java.time.Duration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolSimpleDemo {
	private static JedisPoolConfig buildPoolConfig() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(128);
		poolConfig.setMaxIdle(128);
		poolConfig.setMinIdle(16);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);
		poolConfig.setTestWhileIdle(true);
		poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
		poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
		poolConfig.setNumTestsPerEvictionRun(3);
		poolConfig.setBlockWhenExhausted(true);
		return poolConfig;
	}
	public static void main(String[] args) {
		JedisPoolConfig poolConfig = buildPoolConfig();
		JedisPool jedisPool = new JedisPool(poolConfig, "localhost", 6379);

		try (Jedis jedis = jedisPool.getResource()) {
			// Ping
			System.out.println(jedis.ping());

			// Store & Retrieve a simple string
			jedis.set("foo", "bar");
			System.out.println(jedis.get("foo")); // prints bar
//
//			// Store & Retrieve a HashMap
//			Map<String, String> hash = new HashMap<>();;
//			hash.put("name", "John");
//			hash.put("surname", "Smith");
//			hash.put("company", "Redis");
//			hash.put("age", "29");
//			jedis.hset("user-session:123", hash);
//
//			System.out.println(jedis.hgetAll("user-session:123"));
//			// Prints: {name=John, surname=Smith, company=Redis, age=29}
		}
	}
}
