package it.epicode.gestione_viaggi.prenotazioni;


import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    boolean existsByDipendenteIdAndViaggioIdAndDataRichiesta(Long dipendenteId, Long viaggioId, LocalDate dataRichiesta);

}