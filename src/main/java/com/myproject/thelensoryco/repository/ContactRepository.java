package com.myproject.thelensoryco.repository;

import com.myproject.thelensoryco.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
