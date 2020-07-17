package it.dstech.formazione.service;

import java.util.List;

import it.dstech.formazione.models.Evento;
import it.dstech.formazione.models.Utente;

public interface UtenteServiceDAO {

	Utente add(Utente utente);

	List<Utente> findAll();

	void remove(Utente utente);

	Utente edit(Utente utente);

	Utente findById(Long Id);

	Utente findByUsername(String username);

	Utente findByUsernameAndPassword(String username, String password);
	
	void addEvento(Utente utente, Evento evento);

}
