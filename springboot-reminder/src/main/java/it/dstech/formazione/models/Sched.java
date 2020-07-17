package it.dstech.formazione.models;

import javax.mail.MessagingException;

import it.dstech.formazione.service.MailServiceDAO;

public class Sched implements Runnable {
	private Evento evento;
	private MailServiceDAO mailServ;

	public Sched(Evento evento, MailServiceDAO mailServ) {
		this.evento = evento;
		this.mailServ = mailServ;
	}

	@Override
	public void run() {
		Utente utente = evento.getUtente();
		try {
			mailServ.inviaMail(utente.getMail(), "Notifica " + evento.getNome(),
					"Tra 5 minuti inizia " + evento.getNome() + " che consiste in " + evento.getDescrizione());
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
