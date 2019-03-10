package com.fb.lab;

import com.fb.lab.service.HelloMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.System.exit;

@SpringBootApplication
public class LabApplication implements CommandLineRunner {

	private final HelloMessageService helloService;
	private Logger log = LoggerFactory.getLogger(LabApplication.class);

	@Autowired
	public LabApplication(HelloMessageService helloService) {
		this.helloService = helloService;

	}

	public static void main(String[] args) {
		SpringApplication.run(LabApplication.class, args);
	}

	@Override
	public void run(String... args) {

		if (args.length > 0) {
			log.debug("arg.length > 0, so hello+name");
			log.info(helloService.getMessage(args[0]));
		} else {
			log.debug("args.length == 0, so hello world");
			log.info(helloService.getMessage());
		}

		exit(0);
	}
}
