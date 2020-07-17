package it.dstech.formazione.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.formazione.models.Evento;
import it.dstech.formazione.models.Utente;
import it.dstech.formazione.repository.UtenteRepository;

@Service
public class UtenteServiceDAOImpl implements UtenteServiceDAO {
	@Autowired
	private UtenteRepository repo;

	@Override
	public Utente add(Utente utente) {
repo.save(utente);
		return utente;
	}

	@Override
	public List<Utente> findAll() {
		
		return repo.findAll();
	}

	@Override
	public void remove(Utente utente) {
	repo.delete(utente);

	}

	@Override
	public Utente edit(Utente utente) {
		repo.save(utente);
		return utente;
	}

	@Override
	public Utente findById(Long id) {
	
		return repo.findById(id).get();
	}

	@Override
	public Utente findByUsername(String username) {	
		return repo.findByUsername(username);
	}

	@Override
	public Utente findByUsernameAndPassword(String username, String password) {	
		return repo.findByUsernameAndPassword(username, password);
	}

	@Override
	public void addEvento(Utente utente, Evento evento) {
		utente.getEventi().add(evento);
		repo.save(utente);
		
	}

}
