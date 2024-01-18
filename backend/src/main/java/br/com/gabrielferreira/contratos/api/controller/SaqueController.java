package br.com.gabrielferreira.contratos.api.controller;

import br.com.gabrielferreira.contratos.api.mapper.SaldoMapper;
import br.com.gabrielferreira.contratos.api.model.SaldoModel;
import br.com.gabrielferreira.contratos.api.model.input.SaldoInputModel;
import br.com.gabrielferreira.contratos.api.model.params.SaldoParamsModel;
import br.com.gabrielferreira.contratos.domain.model.Saldo;
import br.com.gabrielferreira.contratos.domain.repository.filter.SaldoFilterModel;
import br.com.gabrielferreira.contratos.domain.service.SaqueService;
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
@RequestMapping("/usuarios/{idUsuario}/saques")
@RequiredArgsConstructor
public class SaqueController {

    private final SaqueService saqueService;

    private final SaldoMapper saldoMapper;

    @PostMapping
    public ResponseEntity<SaldoModel> sacar(@PathVariable Long idUsuario, @Valid @RequestBody SaldoInputModel input){
        Saldo saldo = saldoMapper.toSaldo(input);
        Saldo saqueCadastrado = saqueService.saque(idUsuario, saldo);
        SaldoModel saldoModel = saldoMapper.toSaldoModel(saqueCadastrado);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(saldoModel.getId()).toUri();
        return ResponseEntity.created(uri).body(saldoModel);
    }

    @GetMapping
    public ResponseEntity<Page<SaldoModel>> buscarSaquesPorUsuario(@PathVariable Long idUsuario, @PageableDefault(size = 5) Pageable pageable, @Valid SaldoParamsModel params){
        SaldoFilterModel saldoFilterModel = saldoMapper.toSaldoFilterModel(params);
        Page<Saldo> saques = saqueService.buscarSaquesPaginados(idUsuario, saldoFilterModel, pageable);
        Page<SaldoModel> saldoModels = saldoMapper.toSaldosModels(saques);

        return ResponseEntity.ok().body(saldoModels);
    }
}
