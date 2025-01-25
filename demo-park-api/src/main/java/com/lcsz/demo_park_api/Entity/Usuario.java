package com.lcsz.demo_park_api.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "usuarios")
@Table(name = "usuarios")
public class Usuario implements Serializable { // Quando se trabalhamos com JPA é uma boa prática se implementar o Serializable

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 25)
    private Role role = Role.ROLE_CLIENTE;

    @Column(name = "dataCriacao")
    private LocalDateTime dataCriacao;

    @Column(name = "dataUpdate")
    private LocalDateTime dataUpdate;

    @Column(name = "criadoPor")
    private String criadoPor;

    @Column(name = "modificadoPor")
    private String modificadoPor;

    public enum Role {
        ROLE_ADMIN, ROLE_CLIENTE
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                '}';
    }
}
