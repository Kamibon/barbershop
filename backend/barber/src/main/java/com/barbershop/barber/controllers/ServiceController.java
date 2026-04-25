package com.barbershop.barber.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.barbershop.barber.dtos.CreateServiceRequest;
import com.barbershop.barber.models.Service;
import com.barbershop.barber.services.ServiceService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/services")
public class ServiceController {

    ServiceService serviceService;

    private static final String UPLOAD_DIR = "uploads/services/";

    @GetMapping
    public List<Service> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    public Service getServiceById(@PathVariable Long id) {
        return serviceService.getServiceById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('admin')")
    @PostMapping
    public ResponseEntity<?> addService( @ModelAttribute CreateServiceRequest serviceRequest, MultipartFile file) {
          try {
            String imagePath = null;
            if (!file.isEmpty()) {
                File dir = new File(UPLOAD_DIR);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String filename = file.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + filename);
                file.transferTo(filePath);
                imagePath = "/services/images/" + filename; 
            }

            Service service = Service.builder()
                    .name(serviceRequest.getName())
                    .duration(serviceRequest.getDuration())
                    .price(serviceRequest.getPrice())
                    .imagePath(imagePath)
                    .build();

            serviceService.addService(service);

            return ResponseEntity.ok(service);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Errore nel caricamento del file: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            Path path = Paths.get(UPLOAD_DIR + filename);
            byte[] imageBytes = Files.readAllBytes(path);
            return ResponseEntity.ok().body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

}
