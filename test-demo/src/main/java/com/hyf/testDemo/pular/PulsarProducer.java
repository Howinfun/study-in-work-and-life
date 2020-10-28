package com.hyf.testDemo.pular;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;

public class PulsarProducer {

    public static void main(String[] args) throws Exception{
        PulsarClient client = PulsarClient.builder()
                .serviceUrl("pulsar://localhost:6650")
                .build();
        Producer<byte[]> producer = client.newProducer().topic("my-topic").create();
        for (int i = 0; i < 10; i++) {
            producer.send(new String(i+"message").getBytes());
        }
    }
}
