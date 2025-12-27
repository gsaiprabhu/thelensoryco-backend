package com.myproject.thelensoryco.controller;

import com.myproject.thelensoryco.entity.Photo;
import com.myproject.thelensoryco.repository.PhotoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoRepository photoRepository;

    public PhotoController(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @GetMapping
    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    @PostMapping
    public Photo addPhoto(@Valid @RequestBody Photo photo) {
        return photoRepository.save(photo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Photo> updatePhoto(
            @PathVariable Long id,
            @Valid @RequestBody Photo updatedPhoto) {

        return photoRepository.findById(id)
                .map(existingPhoto -> {
                    existingPhoto.setTitle(updatedPhoto.getTitle());
                    existingPhoto.setDescription(updatedPhoto.getDescription());
                    existingPhoto.setImageUrl(updatedPhoto.getImageUrl());

                    Photo savedPhoto = photoRepository.save(existingPhoto);
                    return ResponseEntity.ok(savedPhoto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        if (!photoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        photoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
