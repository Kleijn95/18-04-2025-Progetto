package it.epicode.gestione_viaggi.prenotazioni;


import it.epicode.gestione_viaggi.common.CommonResponse;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {
    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping
    public Page<PrenotazioneResponse> findAll(@ParameterObject Pageable pageable) {
        return prenotazioneService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public PrenotazioneResponse findById(@PathVariable Long id) {
        return prenotazioneService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse create(@RequestBody @Valid  PrenotazioneRequest request) {

        return prenotazioneService.create(request);

    }

    @PutMapping("/{id}")
    public PrenotazioneResponse update(@PathVariable Long id, @RequestBody PrenotazioneRequest request) {
        return prenotazioneService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        prenotazioneService.delete(id);
    }
}

