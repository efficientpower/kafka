package org.wjh.kafka.web;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wjh.kafka.KafkaProducer;

@Controller
public class KafkaController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @ResponseBody
    @RequestMapping("/kafka/send.do")
    public Object send() {
        String uuid = UUID.randomUUID().toString();
        kafkaProducer.send(uuid);
        return uuid;
    }
}
