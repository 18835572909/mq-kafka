package com.rhb.mqkafka.listener;

import com.rhb.mqkafka.constant.SysConstant;
import java.util.List;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.stereotype.Component;

/**
 * 注解式，kafka消费
 *
 * @author renhuibo
 * @date 2022/1/7 15:58
 */
@Component
public class SimpleKafkaConsumer {

  @KafkaListener(id="simple",topics = {SysConstant.TEST_TOPIC_SINGLE_PARTITION})
  public void onMessage1(ConsumerRecord<String,String> record){
    // 消费的哪个topic、partition的消息,打印出消息内容
    System.out.println("简单消费："+record.topic()+"-"+record.partition()+"-"+record.value());
  }


  /**
   * id : 消费者id
   * groupId: 消费者组id
   * topic: 主题名
   * partitions: 分区数组
   * partition: 分区
   * initialOffset: 分区偏移量
   *
   * 当前注释的意思：
   *  监听主题topic_1的0分区
   *  监听主题topic_5的0分区 和 1分区的偏移量>=3 范围的消息
   *
   * 注意; @KafkaListener标签内的 topic 和 topicPartitions 不能同时使用
   * @param record 接受信息
   */
  @KafkaListener(id = "annotation",groupId = "simple-group",topicPartitions = {
      @TopicPartition(topic = SysConstant.TEST_TOPIC_SINGLE_PARTITION,partitions = {"0"}),
      @TopicPartition(topic = SysConstant.TEST_TOPIC_MULTI_PARTITIONS,partitions = {"0"},
          partitionOffsets = @PartitionOffset(partition = "1",initialOffset = "3"))
  })
  public void onMessage2(ConsumerRecord<String,String> record){
    System.out.println("标签-简单消费："+record.topic()+"-"+record.partition()+"-"+record.offset()+"-"+record.value());
  }


  /**
   * 开启批量消费后，接受参数必须是List
   * 相关参数设置：
   *    设置批量消费
   *    spring.kafka.listener.type=batch
   *    批量消费每次最多消费多少条消息
   *    spring.kafka.consumer.max-poll-records=50
   *
   * @param records 接受信息
   */
  @KafkaListener(id="simple2",topics = {SysConstant.TEST_TOPIC_SINGLE_PARTITION})
  public void onMessage3(List<ConsumerRecord<String,String>> records){
    System.out.println("batch consumer...");
    records.forEach(record->{
      // 消费的哪个topic、partition的消息,打印出消息内容
      System.out.println("批量-简单消费："+record.topic()+"-"+record.partition()+"-"+record.value());
    });
    System.out.println(".................");
  }

  @KafkaListener(id="exception",topics = {SysConstant.TEST_TOPIC_EXCEPTION})
  public void onMessage4(ConsumerRecord<String,String> record){
    System.out.println("异常-简单消费："+record.topic()+"-"+record.partition()+"-"+record.value());
    throw new RuntimeException("exception test");
  }

}
