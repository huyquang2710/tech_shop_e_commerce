package com.techshop.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@EntityScan({"com.techshop.common.entity", "com.techshop.admin.user"})
public class TechShopBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechShopBackEndApplication.class, args);
	}

}
