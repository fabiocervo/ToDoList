package it.dstech.formazione.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import it.dstech.formazione.models.Evento;
import it.dstech.formazione.models.Ruolo;
import it.dstech.formazione.models.Utente;
import it.dstech.formazione.repository.RuoloRepository;
import it.dstech.formazione.repository.UtenteRepository;

@Service
public class UtenteServiceDAOImpl implements UtenteServiceDAO {
	
	private UtenteRepository utenteRepo;
	private RuoloRepository ruoloRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UtenteServiceDAOImpl(UtenteRepository utenteRepo, RuoloRepository ruoloRepo,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.utenteRepo = utenteRepo;
		this.ruoloRepo = ruoloRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	} 
	
	
	@Override
	public Utente add(Utente utente) {
		utente.setPassword(bCryptPasswordEncoder.encode(utente.getPassword()));
		utente.setActive(true);
		Ruolo ruolo = ruoloRepo.findByRuolo("ADMIN");
		utente.setRuolo(new HashSet<Ruolo>(Arrays.asList(ruolo)));
		return utenteRepo.save(utente);
	}

	@Override
	public List<Utente> findAll() {
		
		return utenteRepo.findAll();
	}

	@Override
	public void remove(Utente utente) {
		utenteRepo.delete(utente);

	}

	@Override
	public Utente edit(Utente utente) {
		utenteRepo.save(utente);
		return utente;
	}

	@Override
	public Utente findById(Long id) {
	
		return utenteRepo.findById(id).get();
	}

	@Override
	public Utente findByUsername(String username) {	
		return utenteRepo.findByUsername(username);
	}

	@Override
	public Utente findByUsernameAndPassword(String username, String password) {	
		return utenteRepo.findByUsernameAndPassword(username, password);
	}

	@Override
	public void addEvento(Utente utente, Evento evento) {
		utente.getEventi().add(evento);
		utenteRepo.save(utente);
		
	}

}
