package it.dstech.formazione.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.dstech.formazione.models.Ruolo;
import it.dstech.formazione.repository.RuoloRepository;

public class RuoloServiceDAOImpl implements RuoloServiceDAO {
	@Autowired
	private RuoloRepository ruoloRepo;

	@Override
	public Ruolo add(Ruolo ruolo) {
		ruoloRepo.save(ruolo);
		return ruolo;
	}

	@Override
	public List<Ruolo> findAll() {
		return ruoloRepo.findAll();
	}

	@Override
	public void remove(Ruolo ruolo) {
		ruoloRepo.delete(ruolo);

	}

	@Override
	public Ruolo edit(Ruolo ruolo) {
	  ruoloRepo.save(ruolo);
	  return ruolo;
	}

	@Override
	public Ruolo findById(Long id) {
		return ruoloRepo.findById(id).get();
	}

	@Override
	public Ruolo findByRuolo(String ruolo) {
		return ruoloRepo.findByRuolo(ruolo);
	}

}
