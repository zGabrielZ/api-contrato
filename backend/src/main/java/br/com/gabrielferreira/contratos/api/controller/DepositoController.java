package br.com.gabrielferreira.contratos.api.controller;

import br.com.gabrielferreira.contratos.api.mapper.SaldoMapper;
import br.com.gabrielferreira.contratos.api.model.SaldoModel;
import br.com.gabrielferreira.contratos.api.model.input.SaldoInputModel;
import br.com.gabrielferreira.contratos.api.model.params.SaldoParamsModel;
import br.com.gabrielferreira.contratos.domain.model.Saldo;
import br.com.gabrielferreira.contratos.domain.repository.filter.SaldoFilterModel;
import br.com.gabrielferreira.contratos.domain.service.DepositoService;
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
@RequestMapping("/usuarios/{idUsuario}/depositos")
@RequiredArgsConstructor
public class DepositoController {

    private final DepositoService depositoService;

    private final SaldoMapper saldoMapper;

    @PostMapping
    public ResponseEntity<SaldoModel> depositar(@PathVariable Long idUsuario, @Valid @RequestBody SaldoInputModel input){
        Saldo saldo = saldoMapper.toSaldo(input);
        Saldo depositoCadastrado = depositoService.depositar(idUsuario, saldo);
        SaldoModel saldoModel = saldoMapper.toSaldoModel(depositoCadastrado);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(saldoModel.getId()).toUri();
        return ResponseEntity.created(uri).body(saldoModel);
    }

    @GetMapping
    public ResponseEntity<Page<SaldoModel>> buscarDepositosPorUsuario(@PathVariable Long idUsuario, @PageableDefault(size = 5) Pageable pageable, @Valid SaldoParamsModel params){
        SaldoFilterModel saldoFilterModel = saldoMapper.toSaldoFilterModel(params);
        Page<Saldo> depositos = depositoService.buscarDepositosPaginados(idUsuario, saldoFilterModel, pageable);
        Page<SaldoModel> saldoModels = saldoMapper.toSaldosModels(depositos);

        return ResponseEntity.ok().body(saldoModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaldoModel> buscarDepositoPorUsuarioId(@PathVariable Long idUsuario, @PathVariable Long id){
        Saldo saldo = depositoService.buscarDepositoPorId(idUsuario, id);
        SaldoModel saldoModel = saldoMapper.toSaldoModel(saldo);

        return ResponseEntity.ok().body(saldoModel);
    }
}
