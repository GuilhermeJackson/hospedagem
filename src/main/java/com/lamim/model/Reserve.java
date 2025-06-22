package com.lamim.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reserve {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "checkin")
    @NotEmpty(message = "Checkin não pode ser vazio")
    private LocalDateTime  checkin;

    @NotEmpty(message = "Checkout não pode ser vazio")
    @Column(name = "checkout")
    private LocalDateTime  checkout;

    @NotEmpty(message = "Garage não pode ser vazio")
    @Column(name = "garage")
    private boolean  garage;

    @NotEmpty(message = "Guest não pode ser vazio")
    @ManyToOne(targetEntity = Guest.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_guest", referencedColumnName = "id")
    private Guest guest;
}
