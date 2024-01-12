package br.com.gabrielferreira.contratos.api.controller;

import br.com.gabrielferreira.contratos.api.mapper.PerfilMapper;
import br.com.gabrielferreira.contratos.api.model.PerfilModel;
import br.com.gabrielferreira.contratos.domain.model.Perfil;
import br.com.gabrielferreira.contratos.domain.service.PerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/perfis")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    private final PerfilMapper perfilMapper;

    @GetMapping
    public ResponseEntity<List<PerfilModel>> buscarPerfis(){
        List<Perfil> perfis = perfilService.buscarPerfis();
        return ResponseEntity.ok().body(perfilMapper.toPerfisModels(perfis));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilModel> buscarPerfilPorId(@PathVariable Long id){
        Perfil perfil = perfilService.buscarPerfilPorId(id);
        return ResponseEntity.ok().body(perfilMapper.toPerfilModel(perfil));
    }
}
