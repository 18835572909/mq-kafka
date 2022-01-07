package com.rhb.mqkafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
    return new NewTopic("topic_1001",5,(short)1);
  }

  @Bean
  public NewTopic topic1002(){
    return new NewTopic("topic_1002",5,(short)1);
  }

  @Bean
  public NewTopic topic1003(){
    return new NewTopic("topic_1003",1,(short)1);
  }

  @Bean("consumerErrorHandler")
  public ConsumerAwareListenerErrorHandler errorHandler(){
    return (message, exception, consumer) -> {
      System.out.println("消费异常："+message.getPayload());
      return null;
    };
  }

}

