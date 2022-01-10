package com.rhb.mqkafka.config;

import com.rhb.mqkafka.constant.SysConstant;
import javax.annotation.Resource;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;

/**
 * 创建主题和异常处理类
 *
 * @author renhuibo
 * @date 2022/1/7 18:32
 */
@Configuration
public class KafkaConfiguration {

  @Bean
  public NewTopic topic1001(){
    return new NewTopic(SysConstant.TOPIC_1001,5,(short)1);
  }

  @Bean
  public NewTopic topic1002(){
    return new NewTopic(SysConstant.TOPIC_1002,5,(short)1);
  }

  @Bean
  public NewTopic topic1003(){
    return new NewTopic(SysConstant.TOPIC_1003,1,(short)1);
  }

  @Bean("consumerErrorHandler")
  public ConsumerAwareListenerErrorHandler errorHandler(){
    return (message, exception, consumer) -> {
      System.out.println("消费异常："+message.getPayload());
      return null;
    };
  }

  /**
   *  消息过滤器
   */

  @Resource
  ConsumerFactory consumerFactory;

  @Resource
  KafkaTemplate kafkaTemplate;

  @Bean
  public ConcurrentKafkaListenerContainerFactory filterContainerFactory() {
    ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
    factory.setConsumerFactory(consumerFactory);
    // 被过滤的消息将被丢弃
    factory.setAckDiscarded(true);
    factory.setReplyTemplate(kafkaTemplate);
    // 消息过滤策略
    factory.setRecordFilterStrategy(consumerRecord -> {
      System.out.println("触发消息过滤策略...");
      try{
        if (Integer.parseInt(consumerRecord.value().toString()) % 2 == 0) {
          return true;
        }
      }catch (Exception e){
        return true;
      }
      //返回true消息则被过滤
      return false;
    });
    return factory;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory cronContainerFactory(){
    ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
    factory.setAutoStartup(false);
    factory.setConsumerFactory(consumerFactory);
    return factory;
  }

}

