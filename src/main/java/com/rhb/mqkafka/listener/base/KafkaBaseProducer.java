package com.rhb.mqkafka.listener.base;

import java.util.List;
import javax.annotation.Resource;
import lombok.Data;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * kafka生成者
 *
 * @author renhuibo
 * @date 2022/1/7 16:11
 */
@Component
public class KafkaBaseProducer {

  @Resource
  KafkaTemplate<String,String> kafkaTemplate;

  /**
   * 带有回调的发送（1）
   *
   * @param topic 主题
   * @param message 消息
   */
  public void sendAndCallBack_1(String topic,String message){
    kafkaTemplate.send(topic,message)
        .addCallback(
            success->{
              RecordMetadata recordMetadata = success.getRecordMetadata();
              String topic2 = recordMetadata.topic();
              long offset = recordMetadata.offset();
              int partition = recordMetadata.partition();
              System.out.println("kafka send success: "+topic2 + ","+partition+","+offset);
            },
            failure->{
              Throwable cause = failure.getCause();
              System.out.println("kafak send failure: "+topic+"("+cause.getMessage()+")");
            });
  }

  /**
   * 带有回调的发送（2）
   *
   * @param topic 主题
   * @param message 消息
   */
  public void sendAndCallBack_2(String topic,String message){
    kafkaTemplate.send(topic, message)
        .addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
          @Override
          public void onFailure(Throwable ex) {
            System.out.println("kafak send failure: "+topic+"("+ex.getMessage()+")");
          }

          @Override
          public void onSuccess(SendResult<String, String> result) {
            RecordMetadata recordMetadata = result.getRecordMetadata();
            String topic2 = recordMetadata.topic();
            long offset = recordMetadata.offset();
            int partition = recordMetadata.partition();
            System.out.println("kafka send success: "+topic2 + ","+partition+","+offset);
          }
        });
  }

  /**
   * 简单发送
   *
   * @param topic 主题
   * @param message 消息
   */
  public void send_1(String topic,String message){
    kafkaTemplate.send(topic,message);
  }

  public void sendWithTransaction(List<TopicMessage> list){
    kafkaTemplate.executeInTransaction(kafkaOperations -> {
      ListenableFuture<SendResult<String, String>> send = kafkaOperations.send("", "");
      return send;
    });
  }

  @Data
  public class TopicMessage{
    private String topic;
    private String message;
  }
}
