package com.rhb.mqkafka.pojo;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

/**
 * {desc}
 *
 * @author renhuibo
 * @date 2022/1/11 14:10
 */

@Data
@Builder
public class Order {
  private String sku;
  private String spu;
  private BigDecimal price;
  private Date createTime;
  private Date overTime;
  private Integer status;
}
