package org.wjh.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

@SuppressWarnings("deprecation")
public class KafkaConsumerContainer {

    private static final Log logger = LogFactory.getLog(KafkaConsumerContainer.class);

    private ConsumerConfig consumerConfig;
    private String topic;
    private ExecutorService executor;

    public ConsumerConfig getConsumerConfig() {
        return consumerConfig;
    }

    public void setConsumerConfig(ConsumerConfig consumerConfig) {
        this.consumerConfig = consumerConfig;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void start(){
        new Thread(){
            public void run(){
                executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                ConsumerConnector consumerConnector = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);
                Map<String, Integer> topickMap = new HashMap<String, Integer>();
                topickMap.put(topic, 1);
                Map<String, List<KafkaStream<byte[], byte[]>>> streamMap = consumerConnector.createMessageStreams(topickMap);
                while (true) {
                    String msg = "";
                    try {
                        // 获取消息
                        KafkaStream<byte[], byte[]> stream = streamMap.get(topic).get(0);
                        ConsumerIterator<byte[], byte[]> it = stream.iterator();
                        // 处理消息
                        while (it.hasNext()) {
                            msg = new String(it.next().message());
                            KafkaConsumer consumer = new KafkaConsumer(msg);
                            executor.submit(consumer);
                            logger.info("receive msg " + msg);
                        }
                    } catch (Exception e) {
                        logger.error("处理消息出现异常:" + msg, e);
                        continue;
                    }
                }
            }
        }.start();
    }
}
