package org.wjh.kafka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer {
    private static final Log logger = LogFactory.getLog(KafkaConsumerContainer.class);

    private Producer<String, String> producer;
    private ProducerConfig producerConfig;
    private String topic;

    public ProducerConfig getProducerConfig() {
        return producerConfig;
    }

    public void setProducerConfig(ProducerConfig producerConfig) {
        this.producerConfig = producerConfig;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void init() {
        producer = new Producer<String, String>(producerConfig);
    }

    public void send(String data) {
        try {
            // 如果topic不存在，则会自动创建，默认replication-factor为1，partitions为0
            KeyedMessage<String, String> data1 = new KeyedMessage<String, String>(topic, "", data);
            producer.send(data1);
            logger.info("发送消息：" + data);
        } catch (Exception e) {
            logger.error("发送消息失败,topic=" + topic + ";msg=" + data, e);
        }
    }

}
