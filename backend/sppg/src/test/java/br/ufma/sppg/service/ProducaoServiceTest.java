package br.ufma.sppg.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.dto.ProducaoDTO;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
//@Transactional
public class ProducaoServiceTest {
    @Autowired
    ProducaoService service;

    @Autowired
    ProducaoRepository producaoRepo;

    @Autowired 
    DocenteRepository docenteRepo;

    @Autowired
    ProgramaRepository programaRepo;

    @Autowired
    OrientacaoRepository orientacaoRepo;

    @Test
    public void deveInformarIntervaloDeTempoNull() {
        // Cenário
        Integer anoInicial = null;
        Integer anoFinal = null;
        Producao producaoSalvar = Producao.builder().tipo("teste").ano(2020).qualis("A1").build();
        List<Producao> producoes = new ArrayList<Producao>();
        producoes.add(producaoSalvar);
        Docente docenteSalvar = Docente.builder().nome("João").producoes(producoes).build();

        // Ação
        producaoRepo.save(producaoSalvar);
        docenteRepo.save(docenteSalvar);
        Docente docenteRecuperado = docenteRepo.findById(docenteSalvar.getId()).get();
        // List<Producao> producoesObtidas = service.obterProducoesDocente(docenteRecuperado.getId(), anoInicial, anoFinal);
        Assertions.assertThrows(NullPointerException.class, ()-> service.obterProducoesDocente(docenteRecuperado.getId(), anoInicial, anoFinal));
    }

    @Test
    public void deveInformarIntervaloDeTempoValido() {
        // Cenário
        Integer anoInicial = 2019;
        Integer anoFinal = 2022;
        Producao producaoSalvar = Producao.builder().tipo("teste").ano(2020).qualis("A1").build();
        List<Producao> producoes = new ArrayList<Producao>();
        producoes.add(producaoSalvar);
        Docente docenteSalvar = Docente.builder().nome("João").producoes(producoes).build();

        // Ação
        Producao producaoSalva = producaoRepo.save(producaoSalvar);
        docenteRepo.save(docenteSalvar);
        Docente docenteRecuperado = docenteRepo.findById(docenteSalvar.getId()).get();
        List<Producao> producoesObtidas = service.obterProducoesDocente(docenteRecuperado.getId(), anoInicial, anoFinal);
        
        Assertions.assertEquals(producaoSalva, producoesObtidas.get(0));
    }
    @Test
    public void deveRetornarProducaoPorDocente() {
        // Cenário
        Producao producaoSalvar = Producao.builder().tipo("teste").ano(2020).qualis("A1").build();
        List<Producao> producoes = new ArrayList<Producao>();
        producoes.add(producaoSalvar);
        Docente docenteSalvar = Docente.builder().nome("João").producoes(producoes).build();

        // Ação
        producaoRepo.save(producaoSalvar);
        docenteRepo.save(docenteSalvar);
        Docente docenteRecuperado = docenteRepo.findById(docenteSalvar.getId()).get();
        Producao producaoRecuperada = docenteRecuperado.getProducoes().get(0);
        List<Producao> producoesObtidas = service.obterProducoesDocente(docenteRecuperado.getId(), 2019, 2021);

        // Teste
        Assertions.assertEquals(producaoRecuperada, producoesObtidas.get(0));
        
    }

    @Test
    public void deveRetornarProducaoPorPrograma(){
        // mesmo teste de cima, mas com programa no lugar de docente
        Producao producaoSalvar = Producao.builder().tipo("teste").ano(2020).qualis("A1").build();
        Producao producaoSalvar2 = Producao.builder().tipo("teste").ano(2020).qualis("A1").build();
        Producao producaoSalvar3 = Producao.builder().tipo("teste").ano(2022).qualis("A1").build();
        List<Producao> producoes = new ArrayList<Producao>();
        producoes.add(producaoSalvar);
        producoes.add(producaoSalvar3);
        Docente docenteSalvar = Docente.builder().nome("João").producoes(producoes).build();
        List<Docente> docentes = new ArrayList<Docente>();
        docentes.add(docenteSalvar);
        Programa programaSalvar = Programa.builder().nome("João").docentes(docentes).build();

        producaoRepo.save(producaoSalvar);
        producaoRepo.save(producaoSalvar2);
        producaoRepo.save(producaoSalvar3);
        docenteRepo.save(docenteSalvar);
        programaRepo.save(programaSalvar);
        Programa programaRecuperado = programaRepo.findById(programaSalvar.getId()).get();
        Docente docenteRecuperado = docenteRepo.findById(docenteSalvar.getId()).get();
        Producao producaoRecuperada = docenteRecuperado.getProducoes().get(0);
        List<Producao> producoesObtidas = service.obterProducoesPPG(programaRecuperado.getId(), 2019, 2021);

        Assertions.assertEquals(producaoRecuperada, producoesObtidas.get(0));
        Assertions.assertEquals(1, producoesObtidas.size());
    }

    @Test
    public void deveRetornarOrientacoesPorProducao(){
        Orientacao orientacaoSalvar = Orientacao.builder().tipo("teste").ano(2020).build();
        List<Orientacao> orientacoes = new ArrayList<>();
        orientacoes.add(orientacaoSalvar);
        Producao producaoSalvar = Producao.builder().tipo("teste").ano(2020).qualis("A1").orientacoes(orientacoes).build();

        Orientacao orientacaoSalva = orientacaoRepo.save(orientacaoSalvar);
        Producao producaoSalva = producaoRepo.save(producaoSalvar);
        

        Orientacao orientacaoRecuperada = orientacaoRepo.findById(orientacaoSalva.getId()).get();
        Producao producaoRecuperada = producaoRepo.findById(producaoSalva.getId()).get();
        List<Orientacao> orientacoesObtidas = service.obterOrientacaoProducao(producaoRecuperada.getId());

        Assertions.assertEquals(orientacaoRecuperada, orientacoesObtidas.get(0));
        
    }

    @Test
    public void deveObterProducoesDTO(){
        List<ProducaoDTO> prodDTO = service.obterProducoesDTO();

        Assertions.assertEquals(84,prodDTO.size());
        System.out.println(prodDTO.get(0).getId());
    }

    @Test
    public void deveAdicionarOrientacoes(){
        List<Integer> orientacoesid = new ArrayList<>();
        orientacoesid.add(1);
        orientacoesid.add(2);
        orientacoesid.add(7);
        

        Producao prod = service.setOrientacoes(orientacoesid, 17);
        // for(Orientacao orientacao: orientacoes){
        //     orientacao.getProducoes().add(prod);
        //     orientacaoRepo.save(orientacao);
        // }

        Producao prodpego = producaoRepo.findById(prod.getId()).get();

        Assertions.assertNotNull(prod);
        Assertions.assertEquals(3 ,prodpego.getOrientacoes().size());
    }

    @Test
    public void deveSeAdicionouOrientacoes(){
        Producao producaoSalvar3 = Producao.builder().id(40).tipo("teste").ano(2022).qualis("A1").build();
        producaoRepo.save(producaoSalvar3);
        Producao prod = producaoRepo.findById(17).get();

        Assertions.assertNotNull(prod);
        Assertions.assertEquals(0, prod.getOrientacoes().size());
    }
}