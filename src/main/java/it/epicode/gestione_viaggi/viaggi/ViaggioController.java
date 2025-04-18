package it.epicode.gestione_viaggi.viaggi;

import it.epicode.gestione_viaggi.common.CommonResponse;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {
    @Autowired
    private ViaggioService viaggioService;

    @GetMapping
    public Page<ViaggioResponse> findAll(@ParameterObject Pageable pageable){
        return viaggioService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ViaggioResponse findById(@PathVariable Long id){
        return viaggioService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse create(@RequestBody @Valid ViaggioRequest request){

        return viaggioService.create(request);
    }

    @PutMapping("/{id}")
    public ViaggioResponse update(@PathVariable Long id, @RequestBody ViaggioRequest request){
        return viaggioService.update(id, request);
    }

    @PatchMapping("/{id}/stato")
    public ViaggioResponse updateStato(@PathVariable Long id, @RequestBody StatoViaggio stato) {
        return viaggioService.updateStato(id, stato);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        viaggioService.delete(id);
    }
}
