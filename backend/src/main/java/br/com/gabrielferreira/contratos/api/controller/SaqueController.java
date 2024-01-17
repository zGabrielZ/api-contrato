package br.com.gabrielferreira.contratos.api.controller;

import br.com.gabrielferreira.contratos.api.mapper.SaldoMapper;
import br.com.gabrielferreira.contratos.api.model.SaldoModel;
import br.com.gabrielferreira.contratos.api.model.input.SaldoInputModel;
import br.com.gabrielferreira.contratos.domain.model.Saldo;
import br.com.gabrielferreira.contratos.domain.service.SaqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}
