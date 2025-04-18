package it.epicode.gestione_viaggi.dipendenti;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DipendenteResponse {
    private Long id;
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String fotoProfilo;
}
