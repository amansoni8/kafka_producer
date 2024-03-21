package com.conductor.demos.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerCallback {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());

    public static void main(String[] args) {
        log.info("Hello World");

        Properties properties = new Properties();

        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        KafkaProducer<String ,String> producer = new KafkaProducer<>(properties);

        for(int i=0;i<10;i++){

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("demo_java", "hello world" +i);

        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception e) {
                if (e == null){
                    log.info("Recieved new metadata \n"+
                            "Topic" + metadata.topic() + "\n"+
                            "Partition" + metadata.partition() + "\n"+
                            "OffSet" + metadata.offset() + "\n"+
                            "TimeStamp" + metadata.timestamp() + "\n");
                }
                    else{
                        log.info("error while producing" + e);
                    }

            }
        });
}
        producer.flush();

        producer.close();
    }
}
