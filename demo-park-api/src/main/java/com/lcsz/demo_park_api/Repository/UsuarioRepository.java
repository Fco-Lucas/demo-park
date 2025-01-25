package com.lcsz.demo_park_api.Repository;

import com.lcsz.demo_park_api.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
