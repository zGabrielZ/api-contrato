package br.com.gabrielferreira.contratos.api.controller;

import br.com.gabrielferreira.contratos.api.mapper.HistoricoSaldoMapper;
import br.com.gabrielferreira.contratos.api.model.HistoricoSaldoModel;
import br.com.gabrielferreira.contratos.api.model.params.HistoricoSaldoParamsModel;
import br.com.gabrielferreira.contratos.domain.model.HistoricoSaldo;
import br.com.gabrielferreira.contratos.domain.repository.filter.HistoricoSaldoFilterModel;
import br.com.gabrielferreira.contratos.domain.service.HistoricoSaldoService;
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
@RequestMapping("/usuarios/{idUsuario}/historicos/saldos")
@RequiredArgsConstructor
public class HistoricoSaldoController {

    private final HistoricoSaldoService historicoSaldoService;

    private final HistoricoSaldoMapper historicoSaldoMapper;

    @GetMapping
    public ResponseEntity<Page<HistoricoSaldoModel>> buscarHistoricoPorUsuario(@PathVariable Long idUsuario, @PageableDefault(size = 5) Pageable pageable, @Valid HistoricoSaldoParamsModel params){
        HistoricoSaldoFilterModel historicoSaldoFilterModel = historicoSaldoMapper.toHistoricoSaldoFilterModel(params);
        Page<HistoricoSaldo> historicos = historicoSaldoService.buscarHistoricoPorUsuario(idUsuario, historicoSaldoFilterModel, pageable);
        Page<HistoricoSaldoModel> historicoSaldoModels = historicoSaldoMapper.toHistoricoSaldoModels(historicos);

        return ResponseEntity.ok().body(historicoSaldoModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoSaldoModel> buscarHistoricoPorUsuarioId(@PathVariable Long idUsuario, @PathVariable Long id){
        HistoricoSaldo historicoSaldo = historicoSaldoService.buscarHistoricoSaldoPorId(idUsuario, id);
        HistoricoSaldoModel historicoSaldoModel = historicoSaldoMapper.toHistoricoSaldoModel(historicoSaldo);

        return ResponseEntity.ok().body(historicoSaldoModel);
    }
}
