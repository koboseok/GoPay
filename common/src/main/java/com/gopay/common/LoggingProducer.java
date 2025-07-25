package com.gopay.common;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class LoggingProducer {

    private final KafkaProducer<String, String> producer;
    private final String topic;

    public LoggingProducer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                           @Value("${logging.topic}") String topic) {


        System.out.println("bootstrapServers :: " + bootstrapServers);
        System.out.println("topic :: " + topic);
        // producer 초기화
        Properties props = new Properties();
        // kafka:29092
        props.put("bootstrap.servers", bootstrapServers);
        // "key:value"
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    // kafka Cluster [key, value] Produce
    public void sendMessage(String key, String value) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                System.out.println("Successfully sent message to " + metadata.offset());
            } else {
                exception.printStackTrace();
                System.out.println("Failed to send message to " + exception.getMessage());
            }
        });
    }
}
