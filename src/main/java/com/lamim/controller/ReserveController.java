package com.lamim.controller;

import com.lamim.model.Reserve;
import com.lamim.model.dto.ReserveDto;
import com.lamim.service.ReserveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reserves")
@RequiredArgsConstructor
public class ReserveController {
    private final ReserveService service;

    @GetMapping("/search")
    public ResponseEntity<List<Reserve>> searchByGuest(@RequestParam(name = "params") String params) {
        List<Reserve> reservations = service.searchByNameOrPhoneOrCpf(params);
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid Reserve reserve) {
        try {
            service.save(reserve);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar reserva: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Reserve>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/checkout")
    public ResponseEntity<List<ReserveDto>> getCheckout() {
        return ResponseEntity.ok(service.findCheckouts());
    }

    @GetMapping("/checkin")
    public ResponseEntity<List<ReserveDto>> getCheckin() {
        return ResponseEntity.ok(service.findCheckins());
    }
}

