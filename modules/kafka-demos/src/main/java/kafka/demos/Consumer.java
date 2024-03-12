package kafka.demos;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class Consumer {
	public static void run() {
		System.out.println("I am a Kafka Consumer");

		String bootstrapServers = "localhost:29092";
		String groupId = "dev-demos";
		String topic = "quickstart-events";

		// create consumer configs
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		// create consumer
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);

		// subscribe consumer to our topic(s)
		kafkaConsumer.subscribe(Arrays.asList(topic));

		// poll for new data
		while(true){
			ConsumerRecords<String, String> records =
					kafkaConsumer.poll(Duration.ofMillis(100));

			for (ConsumerRecord<String, String> record : records){
				System.out.println("Key: " + record.key() + ", Value: " + record.value());
				System.out.println("Partition: " + record.partition() + ", Offset:" + record.offset());
			}
		}
	}
}
