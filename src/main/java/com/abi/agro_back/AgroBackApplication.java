package com.abi.agro_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class AgroBackApplication {

	public static void main(String[] args) throws IOException {
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
