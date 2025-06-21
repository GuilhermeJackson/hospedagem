package com.lamim.model;

import com.lamim.model.dto.GuestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Guest {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name")
    @NotEmpty(message = "Nome não pode ser vazio")
    private String name;

    @Column(name = "cpf")
    @NotEmpty(message = "CPF não pode ser vazio")
    private String cpf;

    @Column(name = "phone")
    @NotEmpty(message = "Telefone não pode ser vazio")
    private String phone;

    public GuestDto convertDto() {
        return GuestDto.builder()
                .name(this.name)
                .cpf(this.cpf)
                .phone(this.phone)
                .build();
    }
}

