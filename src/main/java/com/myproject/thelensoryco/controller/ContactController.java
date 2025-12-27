package com.myproject.thelensoryco.controller;

import com.myproject.thelensoryco.entity.Contact;
import com.myproject.thelensoryco.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // =========================
    // POST /api/contact
    // =========================
    @PostMapping
    public ResponseEntity<Contact> submitContact(
            @Valid @RequestBody Contact contact) {

        Contact savedContact = contactRepository.save(contact);
        return new ResponseEntity<>(savedContact, HttpStatus.CREATED);
    }
}
