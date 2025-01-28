package com.lcsz.demo_park_api.web.controller;

import com.lcsz.demo_park_api.Entity.Cliente;
import com.lcsz.demo_park_api.Repository.projection.ClienteProjection;
import com.lcsz.demo_park_api.Service.ClienteService;
import com.lcsz.demo_park_api.Service.UsuarioService;
import com.lcsz.demo_park_api.jwt.JwtUserDetails;
import com.lcsz.demo_park_api.web.dto.ClienteCreateDto;
import com.lcsz.demo_park_api.web.dto.ClienteResponseDto;
import com.lcsz.demo_park_api.web.dto.PageableDto;
import com.lcsz.demo_park_api.web.dto.mapper.ClienteMapper;
import com.lcsz.demo_park_api.web.dto.mapper.PagebleMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    @PostMapping()
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> create(@RequestBody @Valid ClienteCreateDto dto, @AuthenticationPrincipal JwtUserDetails userDetails) {
        Cliente cliente = ClienteMapper.toCliente(dto);

        cliente.setUsuario(usuarioService.buscarPorId(userDetails.getId()));

        clienteService.salvar(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteMapper.toDto(cliente));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClienteResponseDto> getById(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);

        return ResponseEntity.ok().body(ClienteMapper.toDto(cliente));
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAll(Pageable pageable) {
        Page<ClienteProjection> clientes = clienteService.buscarTodos(pageable);

        return ResponseEntity.ok(PagebleMapper.toDto(clientes));
    }

    @GetMapping("/detalhes")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> getDetalhes(@AuthenticationPrincipal JwtUserDetails userDetails) {
        Cliente cliente = clienteService.buscarPorUsuarioId(userDetails.getId());

        return ResponseEntity.ok(ClienteMapper.toDto(cliente));
    }
}
