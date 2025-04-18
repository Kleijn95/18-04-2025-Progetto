package it.epicode.gestione_viaggi.common;


import com.github.javafaker.Faker;
import it.epicode.gestione_viaggi.dipendenti.Dipendente;
import it.epicode.gestione_viaggi.dipendenti.DipendenteRepository;
import it.epicode.gestione_viaggi.prenotazioni.Prenotazione;
import it.epicode.gestione_viaggi.prenotazioni.PrenotazioneRepository;
import it.epicode.gestione_viaggi.viaggi.StatoViaggio;
import it.epicode.gestione_viaggi.viaggi.Viaggio;
import it.epicode.gestione_viaggi.viaggi.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class CommonRunner implements CommandLineRunner {
    @Autowired
    private DipendenteRepository dipendenteRepository;
    @Autowired
    private ViaggioRepository viaggioRepository;
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;
    @Autowired
    private Faker faker;


    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 50; i++) {
            Dipendente dipendente = new Dipendente();
            dipendente.setNome(faker.name().firstName());
            dipendente.setCognome(faker.name().lastName());
            dipendente.setEmail(faker.internet().emailAddress());
            dipendente.setUsername(faker.name().username());
            dipendenteRepository.save(dipendente);

            Viaggio viaggio = new Viaggio();
            viaggio.setDestinazione(faker.address().city());
            Date start = Date.from(LocalDate.now().minusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date end = Date.from(LocalDate.now().plusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

            LocalDate dataViaggio = faker.date()
                    .between(start, end)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            viaggio.setData(dataViaggio);
            viaggio.setStato(dataViaggio.isBefore(LocalDate.now()) ? StatoViaggio.COMPLETATO : StatoViaggio.IN_PROGRAMMA);
            viaggioRepository.save(viaggio);

            // CREAZIONE PRENOTAZIONE
            Prenotazione prenotazione = new Prenotazione();
            prenotazione.setDipendente(dipendente);
            prenotazione.setViaggio(viaggio);
            prenotazione.setNote(faker.lorem().sentence());

            // Genera dataRichiesta prima della data del viaggio
            LocalDate dataRichiesta = faker.date()
                    .between(
                            Date.from(dataViaggio.minusDays(90).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                            Date.from(dataViaggio.minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant())
                    )
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            prenotazione.setDataRichiesta(dataRichiesta);
            prenotazioneRepository.save(prenotazione);
        }
    }
}
