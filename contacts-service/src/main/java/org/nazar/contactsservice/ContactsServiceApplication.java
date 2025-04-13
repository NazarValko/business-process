package org.nazar.contactsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
@ComponentScan(basePackages = {"org.nazar.contactsservice", "org.nazar.common.config"})
public class ContactsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactsServiceApplication.class, args);
	}

}
