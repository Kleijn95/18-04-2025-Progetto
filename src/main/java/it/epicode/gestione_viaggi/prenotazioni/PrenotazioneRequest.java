package it.epicode.gestione_viaggi.prenotazioni;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrenotazioneRequest {
    @NotNull(message = "Data richiesta is required")
    private LocalDate dataRichiesta;
    private String note;
    @NotNull(message = "Dipendente ID is required")
    private Long dipendenteId;
    @NotNull(message = "Viaggio ID is required")
    private Long viaggioId;
}
