package kafka.demos;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class Producer {
	public static void run() {
		System.out.println("I am a Kafka Producer");

		String bootstrapServers = "127.0.0.1:29092";

		// create Producer properties
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		// create the producer
		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

		// create a producer record
		ProducerRecord<String, String> producerRecord =
				new ProducerRecord<>("quickstart-events", "hello world");

		int cnt = 3;
		while (cnt-- > 0) {
			// send data - asynchronous
			producer.send(producerRecord);

			// flush data - synchronous
			producer.flush();
		}

		// flush and close producer
		producer.close();
	}
}
