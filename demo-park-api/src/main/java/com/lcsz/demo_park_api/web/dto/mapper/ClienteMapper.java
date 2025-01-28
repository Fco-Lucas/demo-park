package com.lcsz.demo_park_api.web.dto.mapper;

import com.lcsz.demo_park_api.Entity.Cliente;
import com.lcsz.demo_park_api.Entity.Usuario;
import com.lcsz.demo_park_api.web.dto.ClienteCreateDto;
import com.lcsz.demo_park_api.web.dto.ClienteResponseDto;
import com.lcsz.demo_park_api.web.dto.UsuarioResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {

    public static Cliente toCliente(ClienteCreateDto dto) {
        return new ModelMapper().map(dto, Cliente.class);
    }

    public static ClienteResponseDto toDto(Cliente cliente) {
        return new ModelMapper().map(cliente, ClienteResponseDto.class);
    }
}
