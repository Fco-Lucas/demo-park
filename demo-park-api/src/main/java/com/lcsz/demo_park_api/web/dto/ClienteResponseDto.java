package com.lcsz.demo_park_api.web.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteResponseDto {

    private Long id;
    private String nome;
    private String cpf;
}
