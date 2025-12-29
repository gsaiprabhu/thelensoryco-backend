package com.myproject.thelensoryco.controller;

import com.myproject.thelensoryco.entity.Photo;
import com.myproject.thelensoryco.repository.PhotoRepository;
import com.myproject.thelensoryco.service.ImageUploadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoRepository photoRepository;
    private final ImageUploadService imageUploadService;

    public PhotoController(PhotoRepository photoRepository,
                           ImageUploadService imageUploadService) {
        this.photoRepository = photoRepository;
        this.imageUploadService = imageUploadService;
    }

    @GetMapping
    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Photo> addPhoto(
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("file") MultipartFile file) {

        String imageUrl = imageUploadService.uploadImage(file);

        Photo photo = Photo.builder()
                .title(title)
                .description(description)
                .imageUrl(imageUrl)
                .build();

        Photo savedPhoto = photoRepository.save(photo);
        return new ResponseEntity<>(savedPhoto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Photo> updatePhoto(
            @PathVariable Long id,
            @Valid @RequestBody Photo updatedPhoto) {

        return photoRepository.findById(id)
                .map(existingPhoto -> {
                    existingPhoto.setTitle(updatedPhoto.getTitle());
                    existingPhoto.setDescription(updatedPhoto.getDescription());

                    Photo savedPhoto = photoRepository.save(existingPhoto);
                    return ResponseEntity.ok(savedPhoto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {

        final Photo photo = photoRepository.findById(id).orElse(null);

        if (photo == null) {
            return ResponseEntity.notFound().build();
        }

        // delete image from Cloudinary
        imageUploadService.deleteImage(photo.getImageUrl());

        // delete DB record
        photoRepository.delete(photo);

        return ResponseEntity.noContent().build();
    }
}
