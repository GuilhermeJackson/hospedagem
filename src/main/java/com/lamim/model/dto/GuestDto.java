package com.lamim.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class GuestDto {
    @NotEmpty(message = "Nome não pode ser vazio")
    private String name;

    @NotEmpty(message = "CPF não pode ser vazio")
    private String cpf;

    @NotEmpty(message = "Telefone não pode ser vazio")
    private String phone;
}
