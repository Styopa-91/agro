package com.abi.agro_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgroBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgroBackApplication.class, args);
	}
//	@Bean
//	CommandLineRunner runner(Parser parser) {
//		return args -> {
//			parser.jsonToHashMap();
//			parser.start();
//		};
//	}
//	@Bean
//	CommandLineRunner runner(DelBucket delBucket) {
//		return args -> {
//			delBucket.delBuck();
//		};
//	}
}
