package com.lcsz.demo_park_api.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClienteCreateDto {
    @NotNull
    @Size(min = 5, max = 100, message = "O nome do cliente deve conter de 5 á 100 caracteres")
    private String nome;

    @Size(min = 11, max = 11, message = "CPF com tamanho inválido")
    @CPF
    private String cpf;
}
