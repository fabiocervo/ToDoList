package it.dstech.formazione.service;

import java.util.List;

import it.dstech.formazione.models.Evento;

public interface EventoServiceDAO {
Evento add(Evento evento);

List<Evento> findAll();

void remove(Evento evento);

Evento edit(Evento evento);

Evento findById(Long Id);

}
