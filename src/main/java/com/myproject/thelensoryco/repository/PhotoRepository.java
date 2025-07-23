package com.myproject.thelensoryco.repository;

import com.myproject.thelensoryco.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
