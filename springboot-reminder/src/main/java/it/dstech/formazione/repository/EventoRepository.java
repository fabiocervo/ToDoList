package it.dstech.formazione.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.dstech.formazione.models.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long>{

}
