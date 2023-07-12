package br.ufma.sppg.controller;

//import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.dto.*;
import br.ufma.sppg.model.*;
import br.ufma.sppg.service.ProgramaService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;

@RestController
@RequestMapping("/api/programa")
public class ProgramaController {
    @Autowired
    ProgramaService servicePPG;

    @GetMapping("/obterPrograma")
    public ResponseEntity<?> obterPrograma(
            @RequestParam("programa") String nome){
        try{
            List <Programa> programas = servicePPG.obterPrograma(nome);
            return new ResponseEntity<List <Programa>>(programas, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/filtroPPG")
    public ResponseEntity<?> obterFiltroPPG(){
        try{
            List<FiltroPPGDTO> filtro = servicePPG.obterFiltroPPG();
            return new ResponseEntity<List<FiltroPPGDTO>>(filtro, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterDocentesPrograma")
    public ResponseEntity<?> obterDocentesPrograma(
        @RequestParam("idPrograma") Integer idPrograma){
        try{
            List <DocenteQualisDTO> docentes = servicePPG.obterDocentesQualis(idPrograma);
            return new ResponseEntity<List <DocenteQualisDTO>>(docentes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterProducoesPrograma")
    public ResponseEntity<?> obterProducoesPrograma(
        @RequestParam("programa") Integer idPrograma, Integer anoIni, Integer anoFin){
        try{
            List <Producao> producoes = servicePPG.obterProducoes(idPrograma, anoIni, anoFin);
            return new ResponseEntity<List <Producao>>(producoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterOrientacoesPrograma")
    public ResponseEntity<?> obterOrientacoesPorgrama(
        @RequestParam("programa") Integer idPrograma, Integer anoIni, Integer anoFin){
        try{
            List <Orientacao> orientacoes = servicePPG.obterOrientacoes(idPrograma, anoIni, anoFin);
            return new ResponseEntity<List <Orientacao>>(orientacoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterTecnicasPrograma")
    public ResponseEntity<?> obterTecnicasPrograma(
        @RequestParam("programa") Integer idPrograma, Integer anoIni, Integer anoFin){
        try{
            List <Tecnica> tecnicas = servicePPG.obterTecnicas(idPrograma, anoIni, anoFin);
            return new ResponseEntity<List<Tecnica>>(tecnicas, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    ////////////////

    @GetMapping("/qtv_orientacoes_producao") // QTV = quantitativo
    public ResponseEntity<?> ObterQuantitativoOrientacaoProducao(
                        @RequestParam("idPrograma") Integer idPrograma,
                        @RequestParam("anoInicial") Integer anoIni,
                        @RequestParam("anoFinal") Integer anoFin){

        try{
            Integer quantitativo = servicePPG.quantitativoOrientacaoProducao(idPrograma, anoIni, anoFin);
            return new ResponseEntity<Integer>(quantitativo, HttpStatus.OK);

        }catch(ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/qtv_orientacoes_tecnica") // QTV = quantitativo
    public ResponseEntity<?> ObterQuantitativoOrientacaoTecnica(
                        @RequestParam("idPrograma") Integer idPrograma,
                        @RequestParam("anoInicial") Integer anoIni,
                        @RequestParam("anoFinal") Integer anoFin){

        try{
            Integer quantitativo = servicePPG.quantitativoOrientacaoTecnica(idPrograma, anoIni, anoFin);
            return new ResponseEntity<Integer>(quantitativo, HttpStatus.OK);
            
        }catch(ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/indicadores")
    public ResponseEntity<?> ObterIndicadores(
                        @RequestParam("idPrograma") Integer idPrograma,
                        @RequestParam("anoInicial") Integer anoIni,
                        @RequestParam("anoFinal") Integer anoFin){
        
        try{
            IndicadoresDTO indicadores = servicePPG.obterIndicadores(idPrograma, anoIni, anoFin);
            return new ResponseEntity<IndicadoresDTO>(indicadores, HttpStatus.OK);
        }catch(ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/grafico")
    public ResponseEntity<?> ObterGrafico(
                             @RequestParam("idPrograma") Integer idPrograma,
                             @RequestParam("anoInicial") Integer anoIni,
                             @RequestParam("anoFinal") Integer anoFin){

        try{
            InfoGraficoDTO grafico = servicePPG.obterGrafico(idPrograma, anoIni, anoFin);
            return new ResponseEntity<InfoGraficoDTO>(grafico, HttpStatus.OK);
        }catch(ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}


// spring.datasource.url=jdbc:postgresql://horton.db.elephantsql.com:5432/hckvzauf
// spring.datasource.driverClassName=org.postgresql.Driver
// spring.datasource.username=hckvzauf
// spring.datasource.password=Q44izx1iP5Q4pW4dv5UGBU1lIQpKYtrE
// spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
// spring.jpa.defer-datasource-initialization=true