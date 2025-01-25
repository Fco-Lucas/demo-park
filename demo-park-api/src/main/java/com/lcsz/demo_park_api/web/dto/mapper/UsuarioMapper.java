package com.lcsz.demo_park_api.web.dto.mapper;

import com.lcsz.demo_park_api.Entity.Usuario;
import com.lcsz.demo_park_api.web.dto.UsuarioCreateDto;
import com.lcsz.demo_park_api.web.dto.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDto createDto) {
        return new ModelMapper().map(createDto, Usuario.class);
    }

    public static UsuarioResponseDto toDto(Usuario usuario){
        // Pega o valor de role
        String role = usuario.getRole().name().substring("ROLE_".length());

        // Adiciona a role do usuário no DTO
        PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };

        ModelMapper mapper = new ModelMapper();

        mapper.addMappings(props);

        return mapper.map(usuario, UsuarioResponseDto.class);
    }

    public static List<UsuarioResponseDto> toListDto(List<Usuario> usuarios) {
        return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}
