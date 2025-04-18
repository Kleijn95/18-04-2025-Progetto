package it.epicode.gestione_viaggi.viaggi;


import it.epicode.gestione_viaggi.common.CommonResponse;
import it.epicode.gestione_viaggi.dipendenti.DipendenteResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Service
@Validated
public class ViaggioService {

    @Autowired
    private ViaggioRepository viaggioRepository;

    // Find by id
    public ViaggioResponse findById(Long id) {
        Viaggio viaggio = viaggioRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Viaggio non trovato"));
        return fromEntity(viaggio);
    }

    // Find All
    public Page<ViaggioResponse> findAll(Pageable pageable) {
        return viaggioRepository.findAll(pageable)
                .map(this::fromEntity);
    }

    // update
    public ViaggioResponse update(Long id, ViaggioRequest request) {
        Viaggio viaggio = viaggioRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Viaggio non trovato"));
        BeanUtils.copyProperties(request, viaggio);

        // controlli tra Stato e Data
        if (viaggio.getData().isAfter(LocalDate.now()) && viaggio.getStato() == StatoViaggio.COMPLETATO) {
            throw new IllegalArgumentException("Non è possibile creare un viaggio con stato COMPLETATO se la data è futura.");
        }
        if (viaggio.getData().isBefore(LocalDate.now()) && viaggio.getStato() == StatoViaggio.IN_PROGRAMMA) {
            // Se la data è nel passato e lo stato è IN_PROGRAMMA, lanciamo un'eccezione
            throw new IllegalArgumentException("Non è possibile creare un viaggio con stato IN_PROGRAMMA se la data è nel passato.");
        }

        // nel caso non si imposti uno stato viene impostato automaticamente in base alla data
        if (viaggio.getData().isBefore(LocalDate.now())) {
            viaggio.setStato(StatoViaggio.COMPLETATO);
        } else {
            viaggio.setStato(StatoViaggio.IN_PROGRAMMA);
        }


viaggio = viaggioRepository.save(viaggio);
return fromEntity(viaggio);
    }


    // update dello stato
    public ViaggioResponse updateStato(Long id, StatoViaggio stato) {
        Viaggio viaggio = viaggioRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Viaggio non trovato"));
        if (viaggio.getData().isBefore(LocalDate.now()) && stato == StatoViaggio.IN_PROGRAMMA) {
            throw new IllegalArgumentException("Non è possibile impostare lo stato 'IN_PROGRAMMA' per un viaggio passato.");
        }

        // Controllo che non si imposti stato COMPLETATO se la data è nel futuro
        if (viaggio.getData().isAfter(LocalDate.now()) && stato == StatoViaggio.COMPLETATO) {
            throw new IllegalArgumentException("Non è possibile impostare lo stato 'COMPLETATO' per un viaggio futuro.");
        }



        viaggio.setStato(stato);
        viaggio = viaggioRepository.save(viaggio);
        return fromEntity(viaggio);
    }


    // create
    public CommonResponse create(@Valid ViaggioRequest request) {
        Viaggio viaggio = new Viaggio();
        BeanUtils.copyProperties(request, viaggio);

        if (viaggio.getData().isBefore(LocalDate.now()) && viaggio.getStato() == StatoViaggio.IN_PROGRAMMA) {
            // Se la data è nel passato e lo stato è IN_PROGRAMMA, lanciamo un'eccezione
            throw new IllegalArgumentException("Non è possibile creare un viaggio con stato IN_PROGRAMMA se la data è nel passato.");
        }
        if (viaggio.getData().isAfter(LocalDate.now()) && viaggio.getStato() == StatoViaggio.COMPLETATO) {
            throw new IllegalArgumentException("Non è possibile creare un viaggio con stato COMPLETATO se la data è futura.");
        }
        if (viaggio.getData().isBefore(LocalDate.now())) {
            viaggio.setStato(StatoViaggio.COMPLETATO);
        } else {
            viaggio.setStato(StatoViaggio.IN_PROGRAMMA);
        }
        viaggio = viaggioRepository.save(viaggio);
        return new CommonResponse(viaggio.getId());
    }

    // delete
    public void delete(Long id) {
        Viaggio viaggio = viaggioRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Viaggio non trovato"));
        viaggioRepository.delete(viaggio);
    }


    public ViaggioResponse fromEntity(Viaggio viaggio) {
        ViaggioResponse response = new ViaggioResponse();
        BeanUtils.copyProperties(viaggio, response);
        return response;
    }

    public List<ViaggioResponse> fromEntity(List<Viaggio> viaggi) {
        return viaggi
        .stream()
                .map(this::fromEntity)
                .toList();

    }




}
