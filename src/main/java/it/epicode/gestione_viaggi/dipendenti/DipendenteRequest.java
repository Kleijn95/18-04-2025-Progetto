package it.epicode.gestione_viaggi.dipendenti;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DipendenteRequest {
    @NotBlank(message = "Nome is required")
    private String nome;
    @NotBlank(message = "Cognome is required")
    private String cognome;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Email is required")
    private String email;

}
