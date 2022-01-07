package com.rhb.mqkafka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MqKafkaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MqKafkaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("v1 启动成功...");
	}
}
