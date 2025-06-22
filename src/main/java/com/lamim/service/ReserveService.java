package com.lamim.service;

import com.lamim.model.Guest;
import com.lamim.model.Reserve;
import com.lamim.model.dto.ReserveDto;
import com.lamim.repository.ReserveRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReserveService {
    @Autowired
    ReserveRepository repository;

    public List<Reserve> searchByNameOrPhoneOrCpf(String params) {
        return repository.searchByNameOrPhoneOrCpf(params);
    }

    public Reserve save(@Valid Reserve reserve) {
        return repository.save(reserve);
    }

    public List<Reserve> findAll() {
        return repository.findAll();
    }

    public List<ReserveDto> findCheckouts() {
        LocalDateTime now = LocalDateTime.now();
        List<Reserve> allReserves = repository.getGuestWithCheckout(now);
        Map<Guest, List<Reserve>> reservesByGuest = allReserves.stream()
                .collect(Collectors.groupingBy(Reserve::getGuest));

        return reservesByGuest.entrySet().stream()
                .map(entry -> {
                    Guest guest = entry.getKey();
                    List<Reserve> guestReserves = entry.getValue();

                    BigDecimal totalValue = guestReserves.stream()
                            .map(this::calculateReservationTotalCost)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    Reserve lastReserve = guestReserves.stream()
                            .max(Comparator.comparing(Reserve::getCheckout))
                            .orElseThrow(() -> new EntityNotFoundException("Nenhuma reserva encontrada"));

                    BigDecimal lastTotalValue = calculateTotalValue(lastReserve);

                    return ReserveDto.builder()
                            .guestDto(guest.convertDto())
                            .checkin(lastReserve.getCheckin())
                            .checkout(lastReserve.getCheckout())
                            .garage(lastReserve.isGarage())
                            .total(totalValue)
                            .valueLastReserve(lastTotalValue)
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<ReserveDto> findCheckins() {
        LocalDateTime now = LocalDateTime.now();
        List<Reserve> reserves = repository.getGuestWithCheckin(now);
        return reserves.stream().map(reserve -> {
            BigDecimal totalValue = calculateReservationTotalCost(reserve);

            Reserve lastReserve = repository.findLastReserveByGuestIdBeforeNow(reserve.getGuest().getId(), now)
                    .orElse(null);

            BigDecimal lastTotalValue = lastReserve != null ? calculateTotalValue(lastReserve) : BigDecimal.ZERO;

            return ReserveDto.builder()
                    .guestDto(reserve.getGuest().convertDto())
                    .checkin(reserve.getCheckin())
                    .checkout(reserve.getCheckout())
                    .garage(reserve.isGarage())
                    .total(totalValue)
                    .valueLastReserve(lastTotalValue)
                    .build();
        }).collect(Collectors.toList());
    }

    private BigDecimal calculateTotalValue(Reserve reserve) {
        int weekdays = 0;
        int weekends = 0;

        LocalDate currentDate = reserve.getCheckin().toLocalDate();
        LocalDate endDate = reserve.getCheckout().toLocalDate();

        while (!currentDate.isAfter(endDate)) {
            if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                weekends++;
            } else {
                weekdays++;
            }
            currentDate = currentDate.plusDays(1);
        }

        if(reserve.getCheckout().toLocalTime().isAfter(LocalTime.of(16, 30))) {
            if(reserve.getCheckout().getDayOfWeek() == DayOfWeek.SATURDAY ||
                    reserve.getCheckout().getDayOfWeek() == DayOfWeek.SUNDAY) {
                weekends++;
            } else {
                weekdays++;
            }
        }
        return calculeDays(weekdays, weekends, reserve.isGarage());
    }

    private BigDecimal calculateReservationTotalCost(Reserve reserve) {
        boolean isCheckedOut = reserve.getCheckout().isBefore(LocalDateTime.now());
        LocalDate endDate = isCheckedOut ? reserve.getCheckout().toLocalDate() : LocalDate.now();

        int weekdays = 0;
        int weekends = 0;
        LocalDate currentDate = reserve.getCheckin().toLocalDate();

        while (!currentDate.isAfter(endDate)) {
            if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                weekends++;
            } else {
                weekdays++;
            }
            currentDate = currentDate.plusDays(1);
        }

        if (isCheckedOut && reserve.getCheckout().toLocalTime().isAfter(LocalTime.of(16, 30))) {
            if (endDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    endDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                weekends++;
            } else {
                weekdays++;
            }
        }

        return calculeDays(weekdays, weekends, reserve.isGarage());
    }

    private BigDecimal calculeDays(int weekdays, int weekends, boolean isGarage) {
        int valueWeekdays = weekdays * 120;
        int valueWeekends = weekends * 150;
        int valueGarageWeekdays = isGarage ? weekdays * 15 : 0;
        int valueGarageWeekends = isGarage ? weekends * 20 : 0;

        return BigDecimal.valueOf(valueWeekdays)
                .add(BigDecimal.valueOf(valueWeekends))
                .add(BigDecimal.valueOf(valueGarageWeekdays))
                .add(BigDecimal.valueOf(valueGarageWeekends));
    }
}
