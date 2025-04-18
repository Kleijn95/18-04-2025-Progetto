package it.epicode.gestione_viaggi.dipendenti;


import it.epicode.gestione_viaggi.common.CommonResponse;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {
    @Autowired
    private DipendenteService dipendenteService;

    // patch fotoProfilo

    @PatchMapping(value="/{id}/fotoProfilo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFotoProfilo(@PathVariable Long id, @RequestPart MultipartFile file) throws Exception {
        dipendenteService.uploadFotoProfilo(id, file);
    }


    @GetMapping
    public Page<DipendenteResponse> findAll(@ParameterObject Pageable pageable){
        return dipendenteService.findAll(pageable);

    }

    @GetMapping("/{id}")
    public DipendenteResponse findById(@PathVariable Long id){
        return dipendenteService.findyById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse create(@RequestBody @Valid DipendenteRequest request){
        return dipendenteService.create(request);
    }

    @PutMapping("/{id}")
    public DipendenteResponse update(@PathVariable Long id, @RequestBody DipendenteRequest request){
        return dipendenteService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        dipendenteService.delete(id);
    }

}
