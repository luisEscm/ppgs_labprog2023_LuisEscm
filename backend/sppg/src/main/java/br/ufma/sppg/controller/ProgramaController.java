package br.ufma.sppg.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufma.sppg.dto.DocenteQualisDTO;
import br.ufma.sppg.dto.IndicadoresDTO;
import br.ufma.sppg.dto.InfoGraficoDTO;
import br.ufma.sppg.model.*;
import br.ufma.sppg.service.ProgramaService;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;

@RestController
@RequestMapping("/api/programa")
public class ProgramaController {
    @Autowired
    ProgramaService servicePPG;

    @Autowired
    ProgramaService programa;

    @GetMapping("/obterPrograma")
    public ResponseEntity<?> obterPrograma(
            @RequestParam("programa") String nome){
        try{
            List <Programa> programas = programa.obterPrograma(nome);
            return new ResponseEntity<List <Programa>>(programas, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterDocentesPrograma")
    public ResponseEntity<?> obterDocentesPrograma(
        @RequestParam("idPrograma") Integer idPrograma){
        try{
            List<DocenteQualisDTO> docentes = new ArrayList<>();
            if(idPrograma == 1){
                DocenteQualisDTO docente1 = new DocenteQualisDTO(1, "Alexandre César Muniz de Oliveira", 1, 0, 1, 0, 1, 0, 1, 0);
                DocenteQualisDTO docente2 = new DocenteQualisDTO(2, "Geraldo Braz Junior", 1, 1, 1, 0, 1, 0, 1, 0);
                docentes.add(docente1);
                docentes.add(docente2);
                return new ResponseEntity<>(docentes, HttpStatus.OK);
            }else{
                DocenteQualisDTO docente1 = new DocenteQualisDTO(3, "Gonçalves César Oliveira", 1, 0, 1, 0, 1, 3, 1, 2);
                DocenteQualisDTO docente2 = new DocenteQualisDTO(4, "Junior Braz Junior", 1, 3, 1, 0, 0, 0, 0, 0);
                docentes.add(docente1);
                docentes.add(docente2);
                return new ResponseEntity<>(docentes, HttpStatus.OK);
            }
            //List <Docente> docentes = programa.obterDocentesPrograma(idPrograma);
            //return new ResponseEntity<List <Docente>>(docentes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterProducoesPrograma")
    public ResponseEntity<?> obterProducoesPrograma(
        @RequestParam("programa") Integer idPrograma, Integer anoIni, Integer anoFin){
        try{
            List <Producao> producoes = programa.obterProducoes(idPrograma, anoIni, anoFin);
            return new ResponseEntity<List <Producao>>(producoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterOrientacoesPrograma")
    public ResponseEntity<?> obterOrientacoesPorgrama(
        @RequestParam("programa") Integer idPrograma, Integer anoIni, Integer anoFin){
        try{
            List <Orientacao> orientacoes = programa.obterOrientacoes(idPrograma, anoIni, anoFin);
            return new ResponseEntity<List <Orientacao>>(orientacoes, HttpStatus.OK);
        }catch (ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterTecnicasPrograma")
    public ResponseEntity<?> obterTecnicasPrograma(
        @RequestParam("programa") Integer idPrograma, Integer anoIni, Integer anoFin){
        try{
            List <Tecnica> tecnicas = programa.obterTecnicas(idPrograma, anoIni, anoFin);
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
            if(idPrograma == 1){
                IndicadoresDTO indicadores = new IndicadoresDTO("" + 5.9, "" + 3, "" + 2.9, "" + 10);
                return new ResponseEntity<IndicadoresDTO>(indicadores, HttpStatus.OK);
            }else{
                IndicadoresDTO indicadores = new IndicadoresDTO("" + 4.425, "" + 4.425, "" + 0, "" + 5);
                return new ResponseEntity<IndicadoresDTO>(indicadores, HttpStatus.OK);
            }
            //IndicadoresDTO indicadores = servicePPG.obterIndicadores(idPrograma, anoIni, anoFin);
            //return new ResponseEntity<IndicadoresDTO>(indicadores, HttpStatus.OK);
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
            /*List<Integer> anos = new ArrayList<>();
            List<Integer> a1s = new ArrayList<>();
            List<Integer> a2s = new ArrayList<>();
            List<Integer> a3s = new ArrayList<>();
            List<Integer> a4s = new ArrayList<>();
            for(Integer i = anoIni; i > anoFin; i++){
                anos.add(i);
            }
            if(idPrograma == 1){
                for (Integer i : anos) {
                    if((i % 2) == 0){
                        a1s.add(3);
                        a2s.add(17);
                        a3s.add(21);
                        a4s.add(1);
                    }else{
                        a1s.add(17);
                        a2s.add(1);
                        a3s.add(3);
                        a4s.add(21);
                    }
                }
                InfoGraficoDTO grafico = new InfoGraficoDTO(anos, a1s, a2s, a3s, a4s);
                return new ResponseEntity<InfoGraficoDTO>(grafico, HttpStatus.OK);
            }else{
                for (Integer i : anos) {
                    if((i % 2) == 0){
                        a1s.add(2);
                        a2s.add(32);
                        a3s.add(25);
                        a4s.add(8);
                    }else{
                        a1s.add(8);
                        a2s.add(2);
                        a3s.add(32);
                        a4s.add(25);
                    }
                }
                List<Integer> anos2 = new ArrayList<>();
                anos2.add(2019);
                anos2.add(2020);
                anos2.add(2021);
                anos2.add(2022);
                anos2.add(2023);
                InfoGraficoDTO grafico = new InfoGraficoDTO(anos2,
                                                            a1s, a2s, a3s, a4s);
                return new ResponseEntity<InfoGraficoDTO>(grafico, HttpStatus.OK);
            }*/
            InfoGraficoDTO grafico = servicePPG.obterGrafico(idPrograma, anoIni, anoFin);
            return new ResponseEntity<InfoGraficoDTO>(grafico, HttpStatus.OK);
        }catch(ServicoRuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
