package com.src.printing.office.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan({ "com.src.printing.office" })
@EntityScan("com.src.printing.office.po.entity")
@EnableJpaRepositories("com.src.printing.office.po.repository")
@EnableEncryptableProperties
public class PrintingOfficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrintingOfficeApplication.class, args);
	}

}