package com.gopay.taskconsumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gopay.common.RechargingMoneyTask;
import com.gopay.common.SubTask;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Component
public class TaskConsumer {

    private final KafkaConsumer<String, String> consumer;

    private final TaskResultProducer taskResultProducer;

    public TaskConsumer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                        @Value("${task.topic}") String topic, TaskResultProducer taskResultProducer) {
        this.taskResultProducer = taskResultProducer;



        // producer 초기화
        Properties props = new Properties();
        // kafka:29092
        props.put("bootstrap.servers", bootstrapServers);

        // consumer group
        props.put("group.id", "my-group");

        // "key:value"
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        this.consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));

        Thread consumerThread = new Thread(() -> {
            try {
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

                    ObjectMapper mapper = new ObjectMapper();
                    for (ConsumerRecord<String, String> record : records) {
                        // recode: RechargingMoneyTask (jsonString)
                        // task run
                        RechargingMoneyTask task = mapper.readValue(record.value(), RechargingMoneyTask.class);
                        // task result

                        for (SubTask subTask : task.getSubTaskList()) {
                            // 어떤 서브테스크인지 파악해서 external port, adapter를 통해서
                            // membership, banking 통신

                            // all subtask is done. true
                            subTask.setStatus("success");
                        }
                        // produce TaskResult
                        this.taskResultProducer.sendTaskResult(task.getTaskId(), task);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                consumer.close();
            }
        });

        consumerThread.start();
    }


}
