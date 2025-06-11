package com.gopay.money.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gopay.common.RechargingMoneyTask;
import com.gopay.money.application.port.out.SendRechargingMoneyTaskPort;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class TaskProducer implements SendRechargingMoneyTaskPort {

    private final KafkaProducer<String, String> producer;
    private final String topic;

    public TaskProducer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                        @Value("${task.topic}") String topic) {

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

    @Override
    public void sendRechargingMoneyTask(RechargingMoneyTask task) {
        // task를 받은 후 내부적으로 jsonString 형태로 파싱해서 카프카에 프로듀싱
            this.sendMessage(task.getTaskId(), task);
    }


    private void sendMessage(String key, RechargingMoneyTask value) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStringToProduce;

        // jsonString
        try{
            jsonStringToProduce = mapper.writeValueAsString(value);
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
