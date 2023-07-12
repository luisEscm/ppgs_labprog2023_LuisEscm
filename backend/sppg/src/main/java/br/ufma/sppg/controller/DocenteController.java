package br.ufma.sppg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.dto.AtualizaProducaoDTO;
import br.ufma.sppg.dto.AtualizaTecnicaDTO;
import br.ufma.sppg.dto.DocenteBaseDTO;
import br.ufma.sppg.dto.DocenteProdDTO;
import br.ufma.sppg.dto.EstatisticasDTO;
import br.ufma.sppg.dto.IdsDTO;
import br.ufma.sppg.dto.IndicadoresDTO;
import br.ufma.sppg.dto.InfoGraficoDTO;
import br.ufma.sppg.dto.NewProducaoDTO;
import br.ufma.sppg.dto.NewTecnicaDTO;
import br.ufma.sppg.dto.OrientacaoDocenteDTO;
import br.ufma.sppg.dto.OrientacaoNewProdDTO;
import br.ufma.sppg.dto.OrientacaoProdDTO;
import br.ufma.sppg.dto.ProducaoDTO;
import br.ufma.sppg.dto.ProducaoDocenteDTO;
import br.ufma.sppg.dto.TecnicaDTO;
import br.ufma.sppg.dto.TecnicaDocenteDTO;
import br.ufma.sppg.dto.TecnicaProdDTO;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.service.DocenteService;
import br.ufma.sppg.service.OrientacaoService;
import br.ufma.sppg.service.ProducaoService;
import br.ufma.sppg.service.TecnicaService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;


@RequestMapping("/api/docente")
@RestController
public class DocenteController{
    @Autowired
    TecnicaService tecnicaService;

    @Autowired
    ProducaoService producaoService;

    @Autowired
    OrientacaoService orientacaoService;

    @Autowired
    DocenteService docenteService;

    @GetMapping("/obter_producoes/{id}/{data1}/{data2}")
    public ResponseEntity<?> obterProducoesDeDocente(@PathVariable(value = "id", required = true) Integer idDocente,
    @PathVariable(value = "data1", required = true)  Integer data1,
    @PathVariable(value = "data2", required = true)  Integer data2){

        try{
            List<Producao> producaoDocente = producaoService.obterProducoesDocente(idDocente, data1, data2);
            return ResponseEntity.ok(producaoDocente);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obter_orientacoes/{id}")
    public ResponseEntity<?> obterOrientacoesDeDocente(@PathVariable(value = "id", required = true) Integer idDocente,
    @PathVariable(value = "data1", required = true)  Integer data1,
    @PathVariable(value = "data2", required = true)  Integer data2){

        try{
            List<Orientacao> orientacaoDocente = orientacaoService.obterOrientacaoDocente(idDocente, data1, data2);
            return ResponseEntity.ok(orientacaoDocente);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obter_tecnicas/{id}")
    public ResponseEntity<?> obterTecnicasDeDocente(@PathVariable(value = "id", required = true) Integer idDocente){

        try{
            List<Tecnica> tecnicaDocente = tecnicaService.obterTecnicasDocente(idDocente); 
            return ResponseEntity.ok(tecnicaDocente);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/producoes/{id}")
    public ResponseEntity<?> obterProducoesDocente(@PathVariable(value = "id", required = true) Integer idDocente){

        try{
            List<ProducaoDTO> producoes;
            if(idDocente == 0){
                producoes = producaoService.obterProducoesDTO();
            }else{
                producoes = docenteService.obterProducoesNoDate(idDocente);
            }
            return new ResponseEntity<List<ProducaoDTO>>(producoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tecnicas/{id}")
    public ResponseEntity<?> obtertecnicasDocente(@PathVariable(value = "id", required = true) Integer idDocente){

        try{
            List<TecnicaDTO> tecnicas;
            if(idDocente == 0){
                tecnicas = tecnicaService.obterTecnicasDTO();
            }else{
                tecnicas = docenteService.obterTecnicasNoDate(idDocente);
            }
            return new ResponseEntity<List<TecnicaDTO>>(tecnicas, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/docentesFiltro")
    public ResponseEntity<?> obterDocentesFiltro(){
        try{
            List<DocenteBaseDTO> docentes = docenteService.obterDocentesBase();
            return new ResponseEntity<List<DocenteBaseDTO>>(docentes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/docentesProducaoFiltro")
    public ResponseEntity<?> obterDocentesFiltroProducao(){
        try{
            List<DocenteBaseDTO> docentes = docenteService.obterDocentesBase();
            docentes.add(0, new DocenteBaseDTO(0, "Selecione", "Selecione"));
            return new ResponseEntity<List<DocenteBaseDTO>>(docentes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/indicadores/{id}/{anoIni}/{anoFin}")
    public ResponseEntity<?> obterIndicadores(@PathVariable(value = "id", required = true) Integer idDocente,
    @PathVariable(value = "anoIni", required = true)  Integer anoIni,
    @PathVariable(value = "anoFin", required = true)  Integer anoFin){
        try{
            IndicadoresDTO indicadores = docenteService.obterIndicadoresDTO(idDocente, anoIni, anoFin);
            return new ResponseEntity<IndicadoresDTO>(indicadores, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/docenteBase/{id}")
    public ResponseEntity<?> obterDocenteBase(@PathVariable(value = "id", required = true) Integer idDocente){
        try{
            DocenteBaseDTO docente = docenteService.obterDocenteBase(idDocente);
            return new ResponseEntity<DocenteBaseDTO>(docente, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/graficoPeriodico/{id}/{anoIni}/{anoFin}")
    public ResponseEntity<?> obterGraficoPeriodico(@PathVariable(value = "id", required = true) Integer idDocente,
    @PathVariable(value = "anoIni", required = true)  Integer anoIni,
    @PathVariable(value = "anoFin", required = true)  Integer anoFin){
        
        try{
            InfoGraficoDTO grafico = docenteService.obterGraficoPeriodicos(idDocente, anoIni, anoFin);
            return new ResponseEntity<InfoGraficoDTO>(grafico, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/graficoCongresso/{id}/{anoIni}/{anoFin}")
    public ResponseEntity<?> obterGraficoCongresso(@PathVariable(value = "id", required = true) Integer idDocente,
    @PathVariable(value = "anoIni", required = true)  Integer anoIni,
    @PathVariable(value = "anoFin", required = true)  Integer anoFin){
                
        try{
            InfoGraficoDTO grafico = docenteService.obterGraficoCongresso(idDocente, anoIni, anoFin);
            return new ResponseEntity<InfoGraficoDTO>(grafico, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/producoesDocente/{id}/{anoIni}/{anoFin}")
    public ResponseEntity<?> obterProducoesDocenteDTO(@PathVariable(value = "id", required = true) Integer idDocente,
    @PathVariable(value = "anoIni", required = true)  Integer anoIni,
    @PathVariable(value = "anoFin", required = true)  Integer anoFin){
                
        try{
            List<ProducaoDocenteDTO> producoes = docenteService.obterProducoesDTO(idDocente, anoIni, anoFin);
            return new ResponseEntity<List<ProducaoDocenteDTO>>(producoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tecnicasDocente/{id}/{anoIni}/{anoFin}")
    public ResponseEntity<?> obterTecnicasDocenteDTO(@PathVariable(value = "id", required = true) Integer idDocente,
    @PathVariable(value = "anoIni", required = true)  Integer anoIni,
    @PathVariable(value = "anoFin", required = true)  Integer anoFin){
                
        try{
            List<TecnicaDocenteDTO> tecnicas = docenteService.obterTecnicasDTO(idDocente, anoIni, anoFin);
            return new ResponseEntity<List<TecnicaDocenteDTO>>(tecnicas, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/orientacoesDocente/{id}/{anoIni}/{anoFin}")
    public ResponseEntity<?> obterOrientacoesDocenteDTO(@PathVariable(value = "id", required = true) Integer idDocente,
    @PathVariable(value = "anoIni", required = true)  Integer anoIni,
    @PathVariable(value = "anoFin", required = true)  Integer anoFin){
                
        try{
            List<OrientacaoDocenteDTO> orientacoes = docenteService.obterOrientacoesDTO(idDocente, anoIni, anoFin);
            return new ResponseEntity<List<OrientacaoDocenteDTO>>(orientacoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/addOrientacoesProd")
    public ResponseEntity<?> adicionarOrientacoesProd(@RequestBody IdsDTO idsDTO){
                
        try{
            Producao producao = producaoService.setOrientacoes(idsDTO.getIds(), idsDTO.getIdProd());
            return new ResponseEntity<Producao>(producao, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/addEstatisticasProd")
    public ResponseEntity<?> adicionarEstatisticasProd(@RequestBody EstatisticasDTO estatisticasDTO){
        
        try{
            Producao producao = producaoService.addEstatisticasProd(estatisticasDTO.getQtdGrad(), estatisticasDTO.getQtdMestrado(), estatisticasDTO.getQtdDoutorado(), estatisticasDTO.getIdProd());
            return new ResponseEntity<Producao>(producao, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/addOrientacoesTecn")
    public ResponseEntity<?> adicionarOrientacoesTecn(@RequestBody IdsDTO idsDTO){
                
        try{
            Tecnica tecnica = tecnicaService.setOrientacoes(idsDTO.getIds(), idsDTO.getIdProd());
            return new ResponseEntity<Tecnica>(tecnica, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/addEstatisticasTecn")
    public ResponseEntity<?> adicionarEstatisticasTecn(@RequestBody EstatisticasDTO estatisticasDTO){
        
        try{
            Tecnica tecnica = tecnicaService.atualizarEstatisticas(estatisticasDTO.getQtdGrad(), estatisticasDTO.getQtdMestrado(), estatisticasDTO.getQtdDoutorado(), estatisticasDTO.getIdProd());
            return new ResponseEntity<Tecnica>(tecnica, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/orientacoesProducao")
    public ResponseEntity<?> obterOrientacoesDocenteDTO(){
                
        try{
            List<OrientacaoProdDTO> orientacoes = orientacaoService.obterOrientacoesProdDTO();
            return new ResponseEntity<List<OrientacaoProdDTO>>(orientacoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/docentesNewProd")
    public ResponseEntity<?> obterDocentesProdDTO(){
        
        try{
            List<DocenteProdDTO> docentes = docenteService.obterDocentesProdDTO();
            return new ResponseEntity<List<DocenteProdDTO>>(docentes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/orientacoesNewProd")
    public ResponseEntity<?> obterOrientacoesProdDTO(){
        
        try{
            List<OrientacaoNewProdDTO> orientacoes = orientacaoService.obterOrientacoesNewProdDTO();
            return new ResponseEntity<List<OrientacaoNewProdDTO>>(orientacoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

        @GetMapping("/docentesProdNew/{id}")
    public ResponseEntity<?> obterDocentesProdNewDTO(@PathVariable(value = "id", required = true) Integer idProducao){
        
        try{
            List<DocenteProdDTO> docentes = docenteService.obterDocentesProdDTO(idProducao);
            return new ResponseEntity<List<DocenteProdDTO>>(docentes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/orientacoesProdNew/{id}")
    public ResponseEntity<?> obterOrientacoesProdNewDTO(@PathVariable(value = "id", required = true) Integer idProducao){
        
        try{
            List<OrientacaoNewProdDTO> orientacoes = orientacaoService.obterOrientacoesNewProdDTO(idProducao);
            return new ResponseEntity<List<OrientacaoNewProdDTO>>(orientacoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

        @GetMapping("/docentesNewTecn/{id}")
    public ResponseEntity<?> obterDocentesTecnNewDTO(@PathVariable(value = "id", required = true) Integer idTecn){
        
        try{
            List<DocenteProdDTO> docentes = docenteService.obterDocentesTecnDTO(idTecn);
            return new ResponseEntity<List<DocenteProdDTO>>(docentes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/orientacoesNewTecn/{id}")
    public ResponseEntity<?> obterOrientacoesTecnNewDTO(@PathVariable(value = "id", required = true) Integer idTecn){
        
        try{
            List<OrientacaoNewProdDTO> orientacoes = orientacaoService.obterOrientacoesNewTecnDTO(idTecn);
            return new ResponseEntity<List<OrientacaoNewProdDTO>>(orientacoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @GetMapping("/getProd/{id}")
    public ResponseEntity<?> obterProducao(@PathVariable(value = "id", required = true) Integer idProducao){
        
        try{
            NewProducaoDTO producao = producaoService.obterProducao(idProducao);
            return new ResponseEntity<NewProducaoDTO>(producao, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getTecn/{id}")
    public ResponseEntity<?> obterTecnica(@PathVariable(value = "id", required = true) Integer idTecnica){
        
        try{
            NewTecnicaDTO tecnica = tecnicaService.obterTecnica(idTecnica);
            return new ResponseEntity<NewTecnicaDTO>(tecnica, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/newProducao")
    public ResponseEntity<?> addProducao(@RequestBody NewProducaoDTO newProd){
        
        try{
            Producao producao = producaoService.addProd(newProd);
            return new ResponseEntity<Producao>(producao, HttpStatus.CREATED);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/newTecnica")
    public ResponseEntity<?> addTecnica(@RequestBody NewTecnicaDTO newTecnica){
        
        try{
            Tecnica tecnica = tecnicaService.salvarTecnica(newTecnica);
            return new ResponseEntity<Tecnica>(tecnica, HttpStatus.CREATED);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizaTecn")
    public ResponseEntity<?> atualizarTecnica(@RequestBody AtualizaTecnicaDTO newTecnica){
        try{
            Tecnica tecnica = tecnicaService.atualizarTecnica(newTecnica);
            return new ResponseEntity<Tecnica>(tecnica, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizaProd")
    public ResponseEntity<?> atualizarProducao(@RequestBody AtualizaProducaoDTO newProducao){
        try{
            Producao producao = producaoService.atualizarProd(newProducao);
            return new ResponseEntity<Producao>(producao, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}