package com.lamim.repository;

import com.lamim.model.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReserveRepository extends JpaRepository<Reserve, UUID> {
    @Query("""
            SELECT r FROM Reserve r JOIN r.guest g
            WHERE (LOWER(g.name) LIKE LOWER(CONCAT('%', :params, '%')) OR g.cpf = :params OR g.phone = :params)
            """)
    List<Reserve> searchByNameOrPhoneOrCpf(String params);

    @Query("SELECT r FROM Reserve r " +
            "WHERE r.guest.id = :guestId AND r.checkout < :now " +
            "ORDER BY r.checkout DESC")
    Optional<Reserve> findLastReserveByGuestIdBeforeNow(
            @Param("guestId") UUID guestId,
            @Param("now") LocalDateTime now
    );

    @Query("SELECT r FROM Reserve r WHERE r.checkout < :now")
    List<Reserve> getGuestWithCheckout(LocalDateTime now);

    @Query("SELECT r FROM Reserve r WHERE r.checkin <= :now AND r.checkout > :now")
    List<Reserve> getGuestWithCheckin(LocalDateTime now);
}
