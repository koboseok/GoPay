package com.gopay.money.adapter.in.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gopay.common.CountDownLatchManager;
import com.gopay.common.LoggingProducer;
import com.gopay.common.RechargingMoneyTask;
import com.gopay.common.SubTask;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Component
public class RechargingMoneyResultConsumer {

    private final KafkaConsumer<String, String> consumer;
    private final LoggingProducer loggingProducer;
    @NotNull
    private final CountDownLatchManager countDownLatchManager;



    public RechargingMoneyResultConsumer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                                         @Value("${task.result.topic}") String topic, LoggingProducer loggingProducer, CountDownLatchManager countDownLatchManager) {
        this.loggingProducer = loggingProducer;
        this.countDownLatchManager = countDownLatchManager;


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
                ObjectMapper mapper = new ObjectMapper();
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.println("Received message: " + record.key() + " / " + record.value());

                        // record: RechargingMoneyTask, (all subtask is done)


                        RechargingMoneyTask task;

                        try {
                            task = mapper.readValue(record.value(), RechargingMoneyTask.class);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                        List<SubTask> subTaskList = task.getSubTaskList();

                        boolean taskResult = true;

                        // validation membership
                        // validation banking
                        for (SubTask subTask : subTaskList) {
                            if (subTask.getStatus().equals("fail")) {
                                taskResult = false;
                                break;
                            }
                        }

                        // 모두 정상적으로 성공했다면
                        if (taskResult) {
                            this.loggingProducer.sendMessage(task.getTaskId(), "task success");
                            this.countDownLatchManager.setDataForKey(task.getTaskId(), "success");
                        } else {
                            this.loggingProducer.sendMessage(task.getTaskId(), "task failed");
                            this.countDownLatchManager.setDataForKey(task.getTaskId(), "failed");
                        }

                        // test
//                        try {
//                            Thread.sleep(3000);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }

                        this.countDownLatchManager.getCountDownLatch(task.getTaskId()).countDown();



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
