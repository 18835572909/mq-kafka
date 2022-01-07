package com.rhb.mqkafka.config;

import java.util.Map;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

/**
 * {desc}
 *
 * @author renhuibo
 * @date 2022/1/7 16:27
 */
public class CustomizePartitioner implements Partitioner {

  @Override
  public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1,
      Cluster cluster) {
    System.out.println("分区策略：");
    System.out.println(s);
    System.out.println(o);
    if(bytes!=null){
      System.out.println(new String(bytes));
    }
    System.out.println(o1);
    if(bytes1!=null){
      System.out.println(new String(bytes1));
    }

    return 1;
  }

  @Override
  public void close() {

  }

  @Override
  public void configure(Map<String, ?> map) {

  }
}
