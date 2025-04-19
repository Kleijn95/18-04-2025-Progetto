package it.epicode.gestione_viaggi.prenotazioni;

import it.epicode.gestione_viaggi.common.CommonResponse;
import it.epicode.gestione_viaggi.dipendenti.Dipendente;
import it.epicode.gestione_viaggi.dipendenti.DipendenteRepository;
import it.epicode.gestione_viaggi.email.EmailService;
import it.epicode.gestione_viaggi.viaggi.Viaggio;
import it.epicode.gestione_viaggi.viaggi.ViaggioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;
    @Autowired
    private ViaggioRepository viaggioRepository;
    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private EmailService emailService;


    // Find by id
    public PrenotazioneResponse findById(Long id) {
        Prenotazione prenotazione = prenotazioneRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prenotazione non trovata"));
        return fromEntity(prenotazione);
    }

    // Find all
    public Page<PrenotazioneResponse> findAll(Pageable pageable) {
        return prenotazioneRepository.findAll(pageable)
                .map(this::fromEntity);
    }

    // update
    public PrenotazioneResponse update(Long id, PrenotazioneRequest request) {
        Prenotazione prenotazione = prenotazioneRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prenotazione non trovata"));
        Dipendente dipendente = dipendenteRepository
                .findById(request.getDipendenteId())
                .orElseThrow(() -> new EntityNotFoundException("Dipendente non trovato"));
        Viaggio viaggio = viaggioRepository
                .findById(request.getViaggioId())
                .orElseThrow(() -> new EntityNotFoundException("Viaggio non trovato"));
// Controllo dataRichiesta < dataViaggio
        if (!request.getDataRichiesta().isBefore(viaggio.getData())) {
            throw new IllegalArgumentException("La data richiesta deve essere antecedente alla data del viaggio");
        }
        BeanUtils.copyProperties(request, prenotazione);
        prenotazione.setDipendente(dipendente);
        prenotazione.setViaggio(viaggio);

        prenotazione = prenotazioneRepository.save(prenotazione);
        return fromEntity(prenotazione);

    }

    // create
    public CommonResponse create(@Valid PrenotazioneRequest request) {
        if (prenotazioneRepository.existsByDipendenteIdAndDataRichiesta(
                request.getDipendenteId(),
                request.getDataRichiesta())) {
            throw new IllegalStateException("Il dipendente ha già una prenotazione in questa data.");
        }



        Prenotazione prenotazione = new Prenotazione();
        Dipendente dipendente = dipendenteRepository
                .findById(request.getDipendenteId())
                .orElseThrow(() -> new EntityNotFoundException("Dipendente non trovato"));
        Viaggio viaggio = viaggioRepository
                .findById(request.getViaggioId())
                .orElseThrow(() -> new EntityNotFoundException("Viaggio non trovato"));
        // Controllo dataRichiesta < dataViaggio
        if (!request.getDataRichiesta().isBefore(viaggio.getData())) {
            throw new IllegalArgumentException("La data richiesta deve essere antecedente alla data del viaggio");
        }
        BeanUtils.copyProperties(request, prenotazione);
        prenotazione.setDipendente(dipendente);
        prenotazione.setViaggio(viaggio);
        prenotazione = prenotazioneRepository.save(prenotazione);
        try {
            emailService.sendEmail(
                    dipendente.getEmail(),
                    "Prenotazione confermata",
                    "Ciao " + dipendente.getNome() + ",<br><br>La tua prenotazione per il viaggio a <b>" +
                            viaggio.getDestinazione() + "</b> del <b>" + viaggio.getData() +
                            "</b> è stata confermata.<br><br>Note: " + request.getNote()
            );
        } catch (Exception e) {
            System.out.println("Errore durante l'invio dell'email: " + e.getMessage());
        }

        return new CommonResponse(prenotazione.getId());
    }

// delete
    public void delete(Long id) {
        Prenotazione prenotazione = prenotazioneRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prenotazione non trovata"));
        prenotazioneRepository.delete(prenotazione);
    }



    public PrenotazioneResponse fromEntity(Prenotazione prenotazione) {
        PrenotazioneResponse response = new PrenotazioneResponse();
        BeanUtils.copyProperties(prenotazione, response);
        response.setDipendenteId(prenotazione.getDipendente().getId());
        response.setViaggioId(prenotazione.getViaggio().getId());
        return response;
    }

    public List<PrenotazioneResponse> fromEntity(List<Prenotazione> prenotazioni) {
       return prenotazioni.stream()
               .map(this::fromEntity)
               .toList();
    }

}
