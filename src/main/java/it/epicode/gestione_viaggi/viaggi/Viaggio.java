package it.epicode.gestione_viaggi.viaggi;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "viaggi")

public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;
    @Column(nullable = false)
    private String destinazione;
    @Column(nullable = false)
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoViaggio stato;


}