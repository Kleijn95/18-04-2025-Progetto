package it.epicode.gestione_viaggi.viaggi;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViaggioRequest {
    @NotBlank(message = "Nome is required")
    private String destinazione;
    @NotNull(message = "Data is required")
    private LocalDate data;
    @NotNull(message = "Stato is required")
    private StatoViaggio stato;
}
