package com.lcsz.demo_park_api.Service;

import com.lcsz.demo_park_api.Entity.Usuario;
import com.lcsz.demo_park_api.Exception.EntityNotFoundException;
import com.lcsz.demo_park_api.Exception.UsernameUniqueViolationException;
import com.lcsz.demo_park_api.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            return usuarioRepository.save(usuario);
        } catch (DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(String.format("Username {%s} já cadastrado", usuario.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com id: %s não encontrado", id))
        );
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if(!novaSenha.equals(confirmaSenha)) throw new RuntimeException("Nova senha e confirmação da senha distintos");

        Usuario user = buscarPorId(id);

        if(!user.getPassword().equals(senhaAtual)) throw new RuntimeException("Senha atual inválida");

        user.setPassword(novaSenha);

        return user;
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }
}
