package it.dstech.formazione.service;

import javax.mail.MessagingException;

public interface MailServiceDAO {

	void inviaMail(String destinatarioMail, String oggettoMail, String messaggioMail) throws MessagingException;
}
