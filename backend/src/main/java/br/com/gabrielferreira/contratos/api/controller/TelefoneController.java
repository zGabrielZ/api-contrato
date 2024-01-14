package br.com.gabrielferreira.contratos.api.controller;

import br.com.gabrielferreira.contratos.api.mapper.TelefoneMapper;
import br.com.gabrielferreira.contratos.api.model.TelefoneModel;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
