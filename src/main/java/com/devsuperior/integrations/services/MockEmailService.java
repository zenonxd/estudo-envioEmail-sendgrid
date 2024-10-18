package com.devsuperior.integrations.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devsuperior.integrations.dto.EmailDTO;

public class MockEmailService implements EmailService {

	private Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	public void sendPlainTextEmail(EmailDTO dto) {
		LOG.info("Email sent to: " + dto.getTo());
	}
}
