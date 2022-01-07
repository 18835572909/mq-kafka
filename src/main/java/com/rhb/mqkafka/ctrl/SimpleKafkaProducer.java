package com.rhb.mqkafka.ctrl;

import com.rhb.mqkafka.constant.SysConstant;
import com.rhb.mqkafka.listener.base.KafkaBaseProducer;
import javax.annotation.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * {desc}
 *
 * @author renhuibo
 * @date 2022/1/7 15:52
 */
@RestController
public class SimpleKafkaProducer {

  @Resource
  KafkaTemplate<String,String> kafkaTemplate;

  @GetMapping("/kafka/1/{topic}/{message}")
  public void sendMsg(@PathVariable("topic")String topic, @PathVariable("message")String message){
    kafkaTemplate.send(topic,message);
  }

  @Resource
  KafkaBaseProducer kafkaBaseProducer;

  @GetMapping("/kafka/2/{topic}/{message}")
  public void sendAndCallBack(@PathVariable("topic")String topic,@PathVariable("message")String message){
    kafkaBaseProducer.sendAndCallBack_1(topic,message);
  }

}
