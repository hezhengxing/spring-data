package com.example.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.example.data.repository"})
@EntityScan(basePackages={"com.example.data.entity"})
public class DataApplication {

	private static final Logger log = LoggerFactory.getLogger(DataApplication.class);
	public static void main(String[] args) throws UnknownHostException {
		//打印出对应的信息
		SpringApplication springApplication = new SpringApplication(DataApplication.class);
		ApplicationContext applicationContext = springApplication.run(args);
		Environment env = applicationContext.getEnvironment();
		log.info("\n----------------------------------------------------------\n\t" +
						"Application '{}' is running! Access URLs:\n\t" +
						"Local: \t\thttp://localhost:{}\n\t" +
						"External: \thttp://{}:{}\n\t" +
						"----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				env.getProperty("server.port"),
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"),
				env.getProperty("server.port"));
	}
}
