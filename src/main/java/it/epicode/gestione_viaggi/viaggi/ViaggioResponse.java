package it.epicode.gestione_viaggi.viaggi;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViaggioResponse {
    private  Long id;
    private String destinazione;
    private LocalDate data;
    private StatoViaggio stato;

}
