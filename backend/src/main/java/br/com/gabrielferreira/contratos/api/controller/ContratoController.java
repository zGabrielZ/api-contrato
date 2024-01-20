package br.com.gabrielferreira.contratos.api.controller;

import br.com.gabrielferreira.contratos.api.mapper.ContratoMapper;
import br.com.gabrielferreira.contratos.api.model.ContratoModel;
import br.com.gabrielferreira.contratos.api.model.input.ContratoInputModel;
import br.com.gabrielferreira.contratos.domain.model.Contrato;
import br.com.gabrielferreira.contratos.domain.service.ContratoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios/{idUsuario}/contratos")
@RequiredArgsConstructor
public class ContratoController {

    private final ContratoService contratoService;

    private final ContratoMapper contratoMapper;

    @PostMapping
    public ResponseEntity<ContratoModel> cadastrarContrato(@PathVariable Long idUsuario, @Valid @RequestBody ContratoInputModel input){
        Contrato contrato = contratoMapper.toContrato(input);
        Contrato contratoCadastrado = contratoService.cadastrarContrato(idUsuario, contrato);
        ContratoModel contratoModel = contratoMapper.toContratoModel(contratoCadastrado);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(contratoModel.getId()).toUri();
        return ResponseEntity.created(uri).body(contratoModel);
    }
}
