package br.com.gabrielferreira.contratos.api.controller;

import br.com.gabrielferreira.contratos.api.mapper.TelefoneMapper;
import br.com.gabrielferreira.contratos.api.model.QuantidadeTelefoneModel;
import br.com.gabrielferreira.contratos.api.model.TelefoneModel;
import br.com.gabrielferreira.contratos.api.model.input.TelefoneInputModel;
import br.com.gabrielferreira.contratos.api.model.params.TelefoneParamsModel;
import br.com.gabrielferreira.contratos.domain.model.Telefone;
import br.com.gabrielferreira.contratos.domain.repository.filter.TelefoneFilterModel;
import br.com.gabrielferreira.contratos.domain.service.TelefoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios/{idUsuario}/telefones")
@RequiredArgsConstructor
public class TelefoneController {

    private final TelefoneService telefoneService;

    private final TelefoneMapper telefoneMapper;

    @GetMapping
    public ResponseEntity<Page<TelefoneModel>> buscarTelefonesPorUsuario(@PathVariable Long idUsuario, @PageableDefault(size = 5) Pageable pageable, @Valid TelefoneParamsModel params){
        TelefoneFilterModel telefoneFilterModel = telefoneMapper.toTelefoneFilterModel(params);
        Page<Telefone> telefones = telefoneService.buscarTelefones(idUsuario, pageable, telefoneFilterModel);
        Page<TelefoneModel> telefoneModels = telefoneMapper.toTelefonesModels(telefones);

        return ResponseEntity.ok().body(telefoneModels);
    }

    @GetMapping("/{idTelefone}")
    public ResponseEntity<TelefoneModel> buscarTelefonePorId(@PathVariable Long idUsuario, @PathVariable Long idTelefone){
        Telefone telefone = telefoneService.buscarTelefonePorId(idUsuario, idTelefone);
        TelefoneModel telefoneModel = telefoneMapper.toTelefoneModel(telefone);

        return ResponseEntity.ok().body(telefoneModel);
    }

    @GetMapping("/quantidade")
    public ResponseEntity<QuantidadeTelefoneModel> buscarQuantidadeTelefonesPorUsuario(@PathVariable Long idUsuario){
        Long quantidadeTelefone = telefoneService.buscarQuantidadeTelefonePorUsuario(idUsuario);
        QuantidadeTelefoneModel quantidadeTelefoneModel = telefoneMapper.toQuantidadeTelefoneModel(quantidadeTelefone);

        return ResponseEntity.ok().body(quantidadeTelefoneModel);
    }

    @PostMapping
    public ResponseEntity<TelefoneModel> cadastrarTelefone(@PathVariable Long idUsuario, @Valid @RequestBody TelefoneInputModel input){
        Telefone telefone = telefoneMapper.toTelefone(input);
        Telefone telefoneCadastrado = telefoneService.cadastrarTelefone(idUsuario, telefone);
        TelefoneModel telefoneModel = telefoneMapper.toTelefoneModel(telefoneCadastrado);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(telefoneModel.getId()).toUri();
        return ResponseEntity.created(uri).body(telefoneModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TelefoneModel> atualizarTelefonePorId(@PathVariable Long idUsuario, @PathVariable Long id, @Valid @RequestBody TelefoneInputModel input){
        Telefone telefone = telefoneMapper.toTelefone(input);
        Telefone telefoneAtualizado = telefoneService.atualizarTelefone(idUsuario, id, telefone);
        TelefoneModel usuarioModel = telefoneMapper.toTelefoneModel(telefoneAtualizado);

        return ResponseEntity.ok().body(usuarioModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTelefonePorId(@PathVariable Long idUsuario, @PathVariable Long id){
        telefoneService.deletarTelefonePorId(idUsuario, id);
        return ResponseEntity.noContent().build();
    }
}
