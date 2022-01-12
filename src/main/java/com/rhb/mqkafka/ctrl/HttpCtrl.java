package com.rhb.mqkafka.ctrl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.rhb.mqkafka.pojo.Order;
import com.rhb.mqkafka.pojo.SampleReq;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * {desc}
 *
 * @author renhuibo
 * @date 2022/1/11 14:07
 */
@RestController
@RequestMapping("/http")
public class HttpCtrl {

  private static final Order order = Order.builder()
      .createTime(new Date())
      .overTime(new Date())
      .price(new BigDecimal(56.6))
      .sku("89123121")
      .spu("12344343")
      .status(1)
      .build();

  @GetMapping("/get/str")
  public String get(@RequestBody SampleReq req){
    if(ObjectUtil.isNotEmpty(req)){
      return JSONUtil.toJsonStr(req);
    }
    return "hello world!";
  }

  @GetMapping("/get/json")
  public Order getObject(@RequestParam(required = false) String sku){
    order.setSku(sku);
    return order;
  }

  @PostMapping("/post")
  public Order post(){
    return order;
  }

  @PostMapping("/head")
  public Map<String,Object> head(HttpServletRequest request){
    Enumeration<String> headerNames = request.getHeaderNames();
    Map<String,Object> resultMap = new HashMap<>();
    while(headerNames.hasMoreElements()){
      String key = headerNames.nextElement();
      String value = request.getHeader(key);
      resultMap.put(key,value);
    }
    return resultMap;
  }

}
