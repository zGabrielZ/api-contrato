package br.com.gabrielferreira.contratos.api.controller;

import br.com.gabrielferreira.contratos.api.mapper.PerfilMapper;
import br.com.gabrielferreira.contratos.api.model.PerfilModel;
import br.com.gabrielferreira.contratos.api.model.params.PerfilParamsModel;
import br.com.gabrielferreira.contratos.domain.model.Perfil;
import br.com.gabrielferreira.contratos.domain.repository.filter.PerfilFilterModel;
import br.com.gabrielferreira.contratos.domain.service.UsuarioPerfilService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios/{idUsuario}/perfis")
@RequiredArgsConstructor
public class UsuarioPerfilController {

    private final UsuarioPerfilService usuarioPerfilService;

    private final PerfilMapper perfilMapper;

    @GetMapping
    public ResponseEntity<Page<PerfilModel>> buscarPerfisPorUsuario(@PathVariable Long idUsuario, @PageableDefault(size = 5) Pageable pageable, @Valid PerfilParamsModel params){
        PerfilFilterModel perfilFilterModel = perfilMapper.toPerfilFilterModel(params);
        Page<Perfil> perfis = usuarioPerfilService.buscarPerfisPorUsuario(idUsuario, pageable, perfilFilterModel);
        Page<PerfilModel> perfilModels = perfilMapper.toPerfisModels(perfis);

        return ResponseEntity.ok().body(perfilModels);
    }
}
