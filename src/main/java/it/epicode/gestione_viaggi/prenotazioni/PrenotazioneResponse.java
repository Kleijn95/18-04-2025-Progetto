package it.epicode.gestione_viaggi.prenotazioni;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazioneResponse {
    private Long id;
    private LocalDate dataRichiesta;
    private String note;
    private Long dipendenteId; // solo l'ID del dipendente
    private Long viaggioId; // solo l'ID del viaggio
}