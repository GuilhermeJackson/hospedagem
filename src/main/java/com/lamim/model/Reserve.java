package com.lamim.model;

import com.lamim.model.dto.GuestDto;
import com.lamim.model.dto.ReserveDto;
import jakarta.persistence.*;
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
    private LocalDateTime  checkin;

    @Column(name = "checkout")
    private LocalDateTime  checkout;

    @Column(name = "garage")
    private boolean  garage;

    @ManyToOne(targetEntity = Guest.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_guest", referencedColumnName = "id")
    private Guest guest;


}
