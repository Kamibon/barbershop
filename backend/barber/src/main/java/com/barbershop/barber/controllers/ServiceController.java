package com.barbershop.barber.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.barbershop.barber.dtos.CreateServiceRequest;
import com.barbershop.barber.dtos.ServiceResponse;
import com.barbershop.barber.services.ServiceService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor 
@RequestMapping(path = "/services")
public class ServiceController {

    private ServiceService serviceService;

    private static final String UPLOAD_DIR = "uploads/services/";

    @GetMapping
    public ResponseEntity<Page<ServiceResponse>> getAllServices(@RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        return ResponseEntity.ok(serviceService.getAllServices(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getServiceById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(serviceService.getServiceById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ServiceResponse> addService(
            @ModelAttribute @Valid CreateServiceRequest serviceRequest,
            @RequestParam(value = "file", required = false) MultipartFile file)
            throws IOException {

        String imagePath = null;

        if (file != null && !file.isEmpty()) {
            Path uploadDir = Paths.get(UPLOAD_DIR)
                    .toAbsolutePath()
                    .normalize();

            Files.createDirectories(uploadDir);

            String extension = StringUtils.getFilenameExtension(
                    file.getOriginalFilename());

            String filename = UUID.randomUUID()
                    + (extension != null ? "." + extension : "");

            Path filePath = uploadDir.resolve(filename).normalize();

            if (!filePath.startsWith(uploadDir)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Nome file non valido");
            }

            file.transferTo(filePath);
            imagePath = "/services/images/" + filename;
            serviceRequest.setImagePath(imagePath);
        }

        ServiceResponse created = serviceService.addService(serviceRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> deleteService(@PathVariable @Positive Long id) {
        serviceService.deleteService(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable String filename) throws IOException {

        Path uploadDir = Paths.get(UPLOAD_DIR)
                .toAbsolutePath()
                .normalize();

        Path filePath = uploadDir.resolve(filename).normalize();

        // Verifica che il percorso rimanga nella directory autorizzata - anti path
        // traversal
        if (!filePath.startsWith(uploadDir)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Immagine non trovata");
        }

        if (!Files.isRegularFile(filePath)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Immagine non trovata");
        }

        byte[] imageBytes = Files.readAllBytes(filePath);

        // Determina il Content-Type
        String contentType = Files.probeContentType(filePath);

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ResponseStatusException(
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    "Formato immagine non supportato");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(imageBytes);
    }

}
