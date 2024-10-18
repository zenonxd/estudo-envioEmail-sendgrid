package com.devsuperior.integrations.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.devsuperior.integrations.dto.EmailDTO;
import com.devsuperior.integrations.services.exceptions.EmailException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

public class SendGridEmailService implements EmailService {

	//criamos isso para verificar o que aconteceu ao enviar o email
	private Logger LOG = LoggerFactory.getLogger(SendGridEmailService.class);

	@Autowired
	private SendGrid sendGrid;

	public void sendPlainTextEmail(EmailDTO dto) {

		//puxando tudo do DTO que por sua vez, é tudo que é passado
		//no POST do POSTMAN
		Email from = new Email(dto.getFromEmail(), dto.getFromName());
		Email to = new Email(dto.getTo());
		Content content = new Content(dto.getContentType(), dto.getBody());
		Mail mail = new Mail(from, dto.getSubject(), to, content);
		mail.setReplyTo(new Email(dto.getReplyTo()));

		//metodo para o envio do email
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			//usando endpoint da API deles vamos chamar
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			LOG.info("Sending email to: " + to.getEmail());


			Response response = sendGrid.api(request);
			//se a resposta estiver no intervalo de 400 a 500, deu algum problema
			//e irá mostrar o log
			if (response.getStatusCode() >= 400 && response.getStatusCode() <= 500) {
				//mostra o erro
				LOG.error("Error sending email: " + response.getBody());
				throw new EmailException(response.getBody());
			}
			LOG.info("Email sent! Status = " + response.getStatusCode());
		} catch (IOException e) {
			throw new EmailException(e.getMessage());
		}
	}
}
