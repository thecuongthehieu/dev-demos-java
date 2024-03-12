#!/bin/bash

docker-compose up -d

nc -zv localhost 22181 # zookeeper
nc -zv localhost 29092 # kafka

docker exec -it kafka-dev /bin/bash
docker run -it --network=resources_default --rm edenhill/kcat:1.7.1 -b kafka:9092 -L
kafkacat -L -b localhost:29092

./kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:29092

./kafka-topics.sh --describe --topic quickstart-events --bootstrap-server localhost:29092

./kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:29092

./kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:29092 --group test-cli --property print.key=true  --property key.separator="-"

./kafka-consumer-groups.sh  --list --bootstrap-server localhost:29092

./kafka-topics.sh --delete --topic quickstart-events --bootstrap-server localhost:29092

./kafka-consumer-groups.sh --bootstrap-server localhost:29092  --group test-cli --describe
./kafka-consumer-groups.sh --bootstrap-server localhost:29092 --group test-cli --reset-offsets --to-earliest --topic quickstart-events:0 --execute
