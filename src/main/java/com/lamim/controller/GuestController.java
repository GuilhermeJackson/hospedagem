package com.lamim.controller;

import com.lamim.model.Guest;
import com.lamim.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @GetMapping
    public ResponseEntity<List<Guest>> getAll() {
        List<Guest> guests = guestService.findAll();
        return ResponseEntity.ok(guests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Guest> getById(@PathVariable UUID id) {
        return guestService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hóspede não encontrado"));
    }

    @PostMapping
    private ResponseEntity<Object> create(@RequestBody @Valid Guest guest ) {
        try {
            Guest savedGuest = guestService.save(guest);
            return new ResponseEntity<>(savedGuest, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar hóspede: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody @Valid Guest guest) {
        try {
            Guest updatedGuest = guestService.update(id, guest);
            return ResponseEntity.ok(updatedGuest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar hóspede: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if(!guestService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hóspede não encontrado");
        }
        guestService.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
