package com.devsuperior.integrations.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.devsuperior.integrations.services.EmailService;
import com.devsuperior.integrations.services.SendGridEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Bean
	public EmailService emailService() {
		return new SendGridEmailService();
	}
}
