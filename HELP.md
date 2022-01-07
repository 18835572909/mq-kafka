#  关注点
* 发送消息设置回调函数
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



参考 [https://blog.csdn.net/yuanlong122716/article/details/105160545/](https://blog.csdn.net/yuanlong122716/article/details/105160545/)



