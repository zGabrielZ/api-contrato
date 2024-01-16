package br.com.gabrielferreira.contratos.api.controller;

import br.com.gabrielferreira.contratos.api.mapper.SaldoMapper;
import br.com.gabrielferreira.contratos.api.model.DepositoModel;
import br.com.gabrielferreira.contratos.api.model.input.SaldoInputModel;
import br.com.gabrielferreira.contratos.domain.model.Saldo;
import br.com.gabrielferreira.contratos.domain.service.DepositoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<DepositoModel> depositar(@PathVariable Long idUsuario, @Valid @RequestBody SaldoInputModel input){
        Saldo saldo = saldoMapper.toSaldo(input);
        Saldo depositoCadastrado = depositoService.depositar(idUsuario, saldo);
        DepositoModel depositoModel = saldoMapper.toDepositoModel(depositoCadastrado);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(depositoModel.getId()).toUri();
        return ResponseEntity.created(uri).body(depositoModel);
    }
}
