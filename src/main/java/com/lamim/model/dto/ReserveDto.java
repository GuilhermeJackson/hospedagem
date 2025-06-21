package com.lamim.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ReserveDto {
    @NotNull(message = "Hospede não pode ser vazio")
    private GuestDto guestDto;
    @NotNull(message = "Checkin não pode ser vazio")
    private LocalDateTime checkin;
    @NotNull(message = "Checkout não pode ser vazio")
    private LocalDateTime checkout;
    @NotNull(message = "Garage não pode ser vazio")
    private boolean garage;
    private BigDecimal total;
    private BigDecimal valueLastReserve;
}
