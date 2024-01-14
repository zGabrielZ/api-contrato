package br.com.gabrielferreira.contratos.api.controller;

import br.com.gabrielferreira.contratos.api.mapper.UsuarioMapper;
import br.com.gabrielferreira.contratos.api.model.UsuarioModel;
import br.com.gabrielferreira.contratos.api.model.input.UsuarioInputModel;
import br.com.gabrielferreira.contratos.domain.model.Usuario;
import br.com.gabrielferreira.contratos.domain.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final UsuarioMapper usuarioMapper;

    @PostMapping
    public ResponseEntity<UsuarioModel> cadastrarUsuario(@Valid @RequestBody UsuarioInputModel input){
        Usuario usuario = usuarioMapper.toUsuario(input);
        Usuario usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);
        UsuarioModel usuarioModel = usuarioMapper.toUsuarioModel(usuarioCadastrado);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(usuarioModel.getId()).toUri();
        return ResponseEntity.created(uri).body(usuarioModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> buscarUsuarioPorId(@PathVariable Long id){
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        UsuarioModel usuarioModel = usuarioMapper.toUsuarioSemTelefoneModel(usuario);

        return ResponseEntity.ok().body(usuarioModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModel> atualizarUsuarioPorId(@PathVariable Long id, @Valid @RequestBody UsuarioInputModel input){
        Usuario usuario = usuarioMapper.toUsuario(input);
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);
        UsuarioModel usuarioModel = usuarioMapper.toUsuarioModel(usuarioAtualizado);

        return ResponseEntity.ok().body(usuarioModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuarioPorId(@PathVariable Long id){
        usuarioService.deletarUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }
}
