package com.lamim.service;

import com.lamim.model.Guest;
import com.lamim.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GuestService {
    @Autowired
    private GuestRepository repository;

    public List<Guest> findAll() {
        return repository.findAll();
    }

    public Optional<Guest> findById(UUID uuid){
        return repository.findById(uuid);
    }

    public Guest save(Guest guest) {
        return repository.save(guest);
    }

    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
