package com.rhb.mqkafka.ctrl;

import cn.hutool.core.collection.CollectionUtil;
import com.rhb.mqkafka.listener.base.KafkaBaseProducer;
import com.rhb.mqkafka.listener.base.KafkaBaseProducer.TopicMessage;
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

  @GetMapping("/kafka/3/{topic}/{message}")
  public void sendWithTransaction(@PathVariable("topic")String topic,@PathVariable("message")String message){
    kafkaBaseProducer.sendWithTransaction(CollectionUtil.newArrayList(new TopicMessage(topic,message)));
  }

}
