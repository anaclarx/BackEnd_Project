package com.emse.spring.faircop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@EnableGlobalMethodSecurity(securedEnabled = true)
@SpringBootTest
class FaircopApplicationTests {

	@Test
	void contextLoads() {
	}

}
