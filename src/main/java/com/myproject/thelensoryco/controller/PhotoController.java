package com.myproject.thelensoryco.controller;

import com.myproject.thelensoryco.entity.Photo;
import com.myproject.thelensoryco.repository.PhotoRepository;
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
    public Photo addPhoto(@RequestBody Photo photo) {
        return photoRepository.save(photo);
    }
}
