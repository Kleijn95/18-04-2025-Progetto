package it.epicode.gestione_viaggi.prenotazioni;

import it.epicode.gestione_viaggi.dipendenti.Dipendente;
import it.epicode.gestione_viaggi.viaggi.Viaggio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prenotazioni", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"dipendente_id", "dataRichiesta"})
})

public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private LocalDate dataRichiesta;
    private String note;
    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;
    @ManyToOne
    @JoinColumn(name = "viaggio_id")
    private Viaggio viaggio;



}