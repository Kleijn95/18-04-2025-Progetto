package it.epicode.gestione_viaggi.cloudinary;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class CloudinaryController {
    private final CloudinaryService cloudinaryService;

    @PostMapping(path="/uploadme", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("file") MultipartFile file) {
        return cloudinaryService.uploadImage(file);
    }
}
