package org.acme;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.kafka.companion.ConsumerTask;
import io.smallrye.reactive.messaging.kafka.companion.ProducerTask;
import io.smallrye.reactive.messaging.kafka.companion.test.KafkaCompanionTestBase;

@QuarkusTest
public class KafkaWithBaseTest extends KafkaCompanionTestBase
{
//https://smallrye.io/smallrye-reactive-messaging/4.5.0/kafka/test-companion/
  @Test
  public void testWithBase() {
    // companion is created by the base class
    // produce 100 records to messages topic
    ProducerTask producerTask = companion.produceIntegers()
        .usingGenerator(i -> new ProducerRecord<>("messages", i), 100);
    long msgCount = producerTask.awaitCompletion().count();
    Assertions.assertEquals(msgCount, 100);

    // consume 100 records from messages topic
    ConsumerTask<String, Integer> consumerTask = companion.consumeIntegers()
        .fromTopics("messages", 100);
    ConsumerRecord<String, Integer> lastRecord = consumerTask.awaitCompletion().getLastRecord();
    Assertions.assertEquals(lastRecord.value(), 99);
  }
}
