package com.rhb.mqkafka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MqKafkaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MqKafkaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("v1 启动成功...");
	}
}
