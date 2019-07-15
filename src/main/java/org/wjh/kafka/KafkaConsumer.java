package org.wjh.kafka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class KafkaConsumer implements Runnable {
    private static final Log logger = LogFactory.getLog(KafkaConsumer.class);
    private String msg;

    public KafkaConsumer() {
    }

    public KafkaConsumer(String msg) {
        this.msg = msg;
    }

    public void run() {
        // TODO Auto-generated method stub
        logger.info("接收消息 " + msg);
    }

}
