package it.dstech.formazione.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.formazione.models.Evento;
import it.dstech.formazione.repository.EventoRepository;

@Service
public class EventoServiceDAOImpl implements EventoServiceDAO{
@Autowired
private EventoRepository repo;

	@Override
	public Evento add(Evento evento) {
		repo.save(evento);
		return evento;
	}

	@Override
	public List<Evento> findAll() {
		return repo.findAll();
	}

	@Override
	public void remove(Evento evento) {
		repo.delete(evento);
		
	}

	@Override
	public Evento edit(Evento evento) {
		repo.save(evento);
		return evento;
	}

	@Override
	public Evento findById(Long id) {
		
		return repo.findById(id).get();
	}

}
