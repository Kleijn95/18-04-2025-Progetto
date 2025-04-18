package it.epicode.gestione_viaggi.dipendenti;

import it.epicode.gestione_viaggi.cloudinary.CloudinaryService;
import it.epicode.gestione_viaggi.common.CommonResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Validated
public class DipendenteService {

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    // upload Avatar
    public void uploadFotoProfilo(Long id, MultipartFile file) {
        Dipendente dipendente = dipendenteRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dipendente non trovato"));

        String url = cloudinaryService.uploadImage(file);

        dipendente.setFotoProfilo(url);
        dipendenteRepository.save(dipendente);

    }



    // Find by id
    public DipendenteResponse findyById(Long id){
        Dipendente dipendente = dipendenteRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dipendente non trovato"));

        return fromEntity(dipendente);
    }

    // Find All
    public Page<DipendenteResponse> findAll(Pageable pageable) {
        return dipendenteRepository.findAll(pageable)
                .map(this::fromEntity);
    }

    // update
    public DipendenteResponse update(Long id, DipendenteRequest request) {
        Dipendente dipendente = dipendenteRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dipendente non trovato"));
        BeanUtils.copyProperties(request, dipendente);
        dipendente = dipendenteRepository.save(dipendente);
        return fromEntity(dipendente);
    }

    // create
    public CommonResponse create(@Valid DipendenteRequest request){
        Dipendente dipendente = new Dipendente();
        BeanUtils.copyProperties(request, dipendente);
        dipendente = dipendenteRepository.save(dipendente);
        return new CommonResponse(dipendente.getId());
    }

    // delete
    public void delete(Long id) {
        Dipendente dipendente = dipendenteRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dipendente non trovato"));
        dipendenteRepository.delete(dipendente);
    }


    public DipendenteResponse fromEntity(Dipendente dipendente){
        DipendenteResponse response = new DipendenteResponse();
        BeanUtils.copyProperties(dipendente, response);
        return response;
    }

    public List<DipendenteResponse> fromEntity(List<Dipendente> dipendenti){
        return dipendenti
                .stream()
                .map(this::fromEntity)
                .toList();
    }


}
