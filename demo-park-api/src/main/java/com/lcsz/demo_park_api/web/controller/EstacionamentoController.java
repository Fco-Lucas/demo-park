package com.lcsz.demo_park_api.web.controller;

import com.lcsz.demo_park_api.Entity.ClienteVaga;
import com.lcsz.demo_park_api.Repository.projection.ClienteVagaProjection;
import com.lcsz.demo_park_api.Service.ClienteVagaService;
import com.lcsz.demo_park_api.Service.EstacionamentoService;
import com.lcsz.demo_park_api.jwt.JwtUserDetails;
import com.lcsz.demo_park_api.web.dto.EstacionamentoCreateDto;
import com.lcsz.demo_park_api.web.dto.EstacionamentoResponseDto;
import com.lcsz.demo_park_api.web.dto.PageableDto;
import com.lcsz.demo_park_api.web.dto.mapper.ClienteVagaMapper;
import com.lcsz.demo_park_api.web.dto.mapper.PagebleMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/estacionamentos")
public class EstacionamentoController {
    private final EstacionamentoService estacionamentoService;
    private final ClienteVagaService clienteVagaService;

    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDto> checkin(@RequestBody @Valid EstacionamentoCreateDto dto) {
        ClienteVaga clienteVaga = ClienteVagaMapper.toClienteVaga(dto);

        estacionamentoService.checkIn(clienteVaga);

        EstacionamentoResponseDto responseDto = ClienteVagaMapper.toDto(clienteVaga);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{recibo}")
                .buildAndExpand(clienteVaga.getRecibo())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping("/check-in/{recibo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    public ResponseEntity<EstacionamentoResponseDto> getByRecibo(@PathVariable String recibo) {
        ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);

        EstacionamentoResponseDto dto = ClienteVagaMapper.toDto(clienteVaga);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/check-out/{recibo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDto> checkout(@PathVariable String recibo) {
        ClienteVaga clienteVaga = estacionamentoService.checkOut(recibo);

        EstacionamentoResponseDto dto = ClienteVagaMapper.toDto(clienteVaga);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/cpf/{cpf}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAllEstacionamentosPorCpf(@PathVariable String cpf,
                                                                   @PageableDefault(size = 5, sort = "dataEntrada", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ClienteVagaProjection> projection = clienteVagaService.buscarTodosPorClienteCpf(cpf, pageable);

        PageableDto dto = PagebleMapper.toDto(projection);

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<PageableDto> getAllEstacionamentosDoCliente(@AuthenticationPrincipal JwtUserDetails user,
                                                                   @PageableDefault(size = 5, sort = "dataEntrada", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ClienteVagaProjection> projection = clienteVagaService.buscarTodosPorUsuarioId(user.getId(), pageable);

        PageableDto dto = PagebleMapper.toDto(projection);

        return ResponseEntity.ok(dto);
    }
}
