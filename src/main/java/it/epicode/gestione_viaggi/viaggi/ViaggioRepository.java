package it.epicode.gestione_viaggi.viaggi;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ViaggioRepository extends JpaRepository<Viaggio, Long> {
}