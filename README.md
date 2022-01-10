#  关注点
* 发送消息设置回调函数
```
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
```
* kafka支持的分片策略
```
1. kafka默认策略：轮询
2. 允许消息设置key，key根据哈希计算路由到指定分区，同一个key的分区是一样的
3. 自定义分区策略： 配置参数spring.kafka.producer.properties.partitioner.class
```
* 消费值批量消费
```
设置批量消费
spring.kafka.listener.type=batch
批量消费每次最多消费多少条消息
spring.kafka.consumer.max-poll-records=50
```
* 消费者注解式监听(特别注意，id要唯一，会导致自动消费停止)
```
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
  @KafkaListener(id = "simple-id",groupId = "simple-group",topicPartitions = {
      @TopicPartition(topic = SysConstant.TEST_TOPIC_1,partitions = {"0"}),
      @TopicPartition(topic = SysConstant.TEST_TOPIC_2,partitions = {"0"},
          partitionOffsets = @PartitionOffset(partition = "1",initialOffset = "3"))
  })
  public void onMessage2(ConsumerRecord<String,String> record)
```
* 事务提交
```
在使用kafka的事务过程中，遇到的异常提示，kafka的提示还是很清晰的
1. Producer factory does not support transactions
修改配置： spring.kafka.producer.transaction-id-prefix=tx-id  （这是开启事务的标志）  
2. Must set retries to non-zero when using the idempotent producer.  
修改配置： spring.kafka.producer.retries=1 （不要是0）
3. Must set acks to all in order to use the idempotent producer. Otherwise we cannot guarantee idempotence.
修改配置： spring.kafka.producer.acks=all （必须说所有ack都影响，才能保证冥等性）
```
* 消费者异常处理器 （不生效）

* 消费者不消费： 一般情况是消费id重复

* 消息过滤器（在消息到达消费者之前进行拦截过滤）
```
1. 定义拦截器：ConcurrentKafkaListenerContainerFactory
2. 标签声明拦截器：@KafkaListener(id="simple",topics = {SysConstant.TEST_TOPIC_SINGLE_PARTITION},containerFactory = "filterContainerFactory")
```

* 消息转发
```
1. 添加@SendTo标签： 转发消费函数返回值
  @KafkaListener(id="simple",topics = {SysConstant.TEST_TOPIC_SINGLE_PARTITION},containerFactory = "filterContainerFactory")
  @SendTo(SysConstant.TOPIC_1001)
  public String onMessage2(ConsumerRecord<String,String> record){
    System.out.println("标签-简单消费："+record.topic()+"-"+record.partition()+"-"+record.offset()+"-"+record.value());
    return "sendTo Message";
  }
2. a KafkaTemplate is required to support replies
  原因：在定义过滤策略的时候，重新定义了ConcurrentKafkaListenerContainerFactory，但是没有设置factory.setReplyTemplate(kafkaTemplate);
  解决：如果覆盖了SpringBoot的工厂来，设置setReplyTemplate值
```

* 消息调度消费
```
1. @KafkaListener注解所标注的方法并不会在IOC容器中被注册为Bean，而是会被注册在KafkaListenerEndpointRegistry中，而KafkaListenerEndpointRegistry在SpringIOC中已经被注册为Bean
2. registry.getListenerContainer(LISTENER_CONTAINER_ID); 
   LISTENER_CONTAINER_ID: 指的是  @KafkaListener(id = "cron") 标签的id值
```


### 剩余知识点

* 重复消费问题
* offset重置和当前值



参考 [https://blog.csdn.net/yuanlong122716/article/details/105160545/](https://blog.csdn.net/yuanlong122716/article/details/105160545/)



