package com.gopay.taskconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class TaskResultProducer {

    private final KafkaProducer<String, String> producer;
    private final String topic;

    public TaskResultProducer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                        @Value("${task.result.topic}") String topic) {

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


    public void sendTaskResult(String key, Object task) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStringToProduce;

        // jsonString
        try{
            jsonStringToProduce = mapper.writeValueAsString(task);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, jsonStringToProduce);
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
