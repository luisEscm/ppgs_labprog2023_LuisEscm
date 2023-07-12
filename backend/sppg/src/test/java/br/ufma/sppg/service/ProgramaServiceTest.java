package br.ufma.sppg.service;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.dto.FiltroPPGDTO;
import br.ufma.sppg.dto.IndicadoresDTO;
import br.ufma.sppg.dto.Indice;
import br.ufma.sppg.dto.InfoGraficoDTO;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.repo.TecnicaRepository;

@SpringBootTest
public class ProgramaServiceTest {

    @Autowired
    ProgramaService service;

    @Autowired
    ProgramaRepository repo;

    @Autowired
    DocenteRepository docRepo;

    @Autowired
    ProducaoRepository prodRepo;

    @Autowired
    OrientacaoRepository oriRepo;

    @Autowired
    TecnicaRepository tecRepo;

    @Test
    public void deveInformarIntervaloDeTempo() {
        // Cenário
        Integer ini;
        Integer fim;

        // Ação
        ini = 2020;
        fim = 2023;

        // Teste
        Assertions.assertNotNull(ini);
        Assertions.assertNotNull(fim);
        Assertions.assertInstanceOf(Integer.class, ini);
        Assertions.assertInstanceOf(Integer.class, fim);
    }

    @Test
    public void deveRecuperaraProgramaPeloNome() {
        // Cenário
        Programa teste = Programa.builder().nome("qualquer").build();
        Programa programaSalvo = repo.save(teste);

        // Ação
        List<Programa> programaEncontrado = service.obterPrograma(teste.getNome());

        // Teste
        Assertions.assertNotNull(programaEncontrado);
        Assertions.assertTrue(programaEncontrado.size() > 0);
        // Assertions.assertTrue(programaEncontrado.contains(programaSalvo));

        Integer flag = 0;
        for (Programa ppg : programaEncontrado) {
            if (ppg.getNome().equals(programaSalvo.getNome()))
                flag++;
        }
        Assertions.assertTrue(flag > 0);

        repo.delete(programaSalvo);
    }

    @Test
    public void deveRecuperarDocentesDoPrograma() {
        // Cenário
        Programa teste = Programa.builder().nome("qualquer").build();
        Programa programaSalvo = repo.save(teste);

        Docente docTeste = Docente.builder().nome("jhon doe").build();
        Docente docSalvo = docRepo.save(docTeste);

        programaSalvo.setDocentes(Arrays.asList(docSalvo));
        repo.save(programaSalvo);

        // Ação
        List<Docente> docentes = service.obterDocentesPrograma(programaSalvo.getId());
        // Teste
        Assertions.assertNotNull(docentes);
        Assertions.assertTrue(docentes.size() > 0);

        Integer flag = 0;
        for (Docente dc : docentes) {
            if (dc.getNome().equals(docSalvo.getNome()))
                flag++;
        }
        Assertions.assertTrue(flag > 0);

        repo.delete(programaSalvo);
        docRepo.delete(docTeste);
    }

    /*
     * dado um docente com produções e este docente num programa,
     * deve recuperar o *programa* com a lista correta de produções
     * num intervalo de tempo
     */
    @Test
    public void deveRecuperarOProgramaComProducoesNumIntervaloDeTempo() {

        // Cenário
        Programa teste = Programa.builder().nome("qualquer").build();
        Programa programaSalvo = repo.save(teste);

        Docente docTeste = Docente.builder().nome("jhon doe").build();
        Docente docTeste2 = Docente.builder().nome("jhon doe").build();
        Docente docSalvo = docRepo.save(docTeste);
        Docente docSalvo2 = docRepo.save(docTeste2);

        Producao prodTeste = Producao.builder().titulo("titulo teste").ano(2021).tipo("C").qualis("A1").docentes(Arrays.asList(docSalvo, docSalvo2)).build();
        Producao prodTeste2 = Producao.builder().titulo("titulo teste").ano(2019).tipo("C").qualis("A1").docentes(Arrays.asList(docSalvo)).build();
        Producao prodTeste3 = Producao.builder().titulo("titulo teste").ano(2021).tipo("C").qualis("A1").build();
        Producao prodSalvo = prodRepo.save(prodTeste);
        Producao prodSalvo2 = prodRepo.save(prodTeste2);
        Producao prodSalvo3 = prodRepo.save(prodTeste3);

        docSalvo.setProducoes(Arrays.asList(prodSalvo, prodSalvo2));
        docSalvo2.setProducoes(Arrays.asList(prodSalvo));
        Docente docAtual = docRepo.save(docSalvo);
        Docente docAtual2 = docRepo.save(docSalvo2);

        programaSalvo.setDocentes(Arrays.asList(docAtual, docAtual2));
        repo.save(programaSalvo);

        // Ação
        List<Producao> ppg = service.obterProducoes(programaSalvo.getId(), 2020, 2023);

        // Teste
        Assertions.assertNotNull(ppg);
        Assertions.assertEquals(1, ppg.size());

        Integer flag = 0;
        for (Producao prod : ppg) {
            if (prod.getTitulo().equals(prodSalvo.getTitulo()))
                flag++;
        }
        Assertions.assertTrue(flag > 0);

        repo.delete(programaSalvo);
        //docRepo.delete(novoDoc);
        prodRepo.delete(prodSalvo);
    }

    @Test
    public void deveRecuperarOProgramaComOrientacoesNumIntervaloDeTempo() {

        // Cenário
        Programa teste = Programa.builder().nome("qualquer").build();
        Programa programaSalvo = repo.save(teste);

        Docente docTeste = Docente.builder().nome("jhon doe").build();
        Docente docSalvo = docRepo.save(docTeste);

        Orientacao oriTeste = Orientacao.builder().titulo("titulo teste").ano(2021).orientador(docSalvo).build();
        Orientacao oriTeste2 = Orientacao.builder().titulo("titulo teste").ano(2019).orientador(docSalvo).build();
        Orientacao oriTeste3 = Orientacao.builder().titulo("titulo teste").ano(2021).build();
        Orientacao oriSalvo = oriRepo.save(oriTeste);
        Orientacao oriSalvo2 = oriRepo.save(oriTeste2);
        Orientacao oriSalvo3 = oriRepo.save(oriTeste3);

        programaSalvo.setDocentes(Arrays.asList(docSalvo));
        repo.save(programaSalvo);

        // Ação
        List<Orientacao> ppg = service.obterOrientacoes(programaSalvo.getId(), 2020, 2023);

        // Teste
        Assertions.assertNotNull(ppg);
        Assertions.assertEquals(1, ppg.size());

        Integer flag = 0;
        for (Orientacao ori : ppg) {
            if (ori.getTitulo().equals(oriSalvo.getTitulo()))
                flag++;
        }
        Assertions.assertTrue(flag > 0);

        repo.delete(programaSalvo);
        //docRepo.delete(docSalvo);
        oriRepo.delete(oriSalvo);
    }

    @Test
    public void deveRecuperarOProgramaComTecnicasNumIntervaloDeTempo() {

        // Cenário
        Programa teste = Programa.builder().nome("qualquer").build();
        Programa programaSalvo = repo.save(teste);

        Docente docTeste = Docente.builder().nome("jhon doe").build();
        Docente docSalvo = docRepo.save(docTeste);

        Tecnica tecTeste = Tecnica.builder().titulo("titulo teste").ano(2021).build();
        Tecnica tecSalvo = tecRepo.save(tecTeste);

        docSalvo.setTecnicas(Arrays.asList(tecSalvo));
        Docente novoDoc = docRepo.save(docSalvo);

        programaSalvo.setDocentes(Arrays.asList(novoDoc));
        repo.save(programaSalvo);

        // Ação
        List<Tecnica> ppg = service.obterTecnicas(programaSalvo.getId(), 2020, 2023);

        // Teste
        Assertions.assertNotNull(ppg);
        Assertions.assertTrue(ppg.size() > 0);

        Integer flag = 0;
        for (Tecnica tec : ppg) {
            if (tec.getTitulo().equals(tecSalvo.getTitulo()))
                flag++;
        }
        Assertions.assertTrue(flag > 0);

        repo.delete(programaSalvo);
        docRepo.delete(novoDoc);
        tecRepo.delete(tecSalvo);
    }

    @Test
    public void deveCalcularIndiceDeAcordoComOEsperadoParaProgramaNumIntervaloDeTempo() {
        // Cenário
        Programa teste = Programa.builder().nome("qualquer").build();
        Programa programaSalvo = repo.save(teste);

        Docente docTeste = Docente.builder().nome("jhon doe").build();
        Docente docSalvo = docRepo.save(docTeste);

        docSalvo.setProducoes(todasProducoes());
        Docente novoDoc = docRepo.save(docSalvo);

        programaSalvo.setDocentes(Arrays.asList(novoDoc));
        repo.save(programaSalvo);

        // Ação
        Indice indice = service.obterProducaoIndices(programaSalvo.getId(), 2020, 2023);

        // Teste
        Assertions.assertNotNull(indice);
        Assertions.assertEquals(Double.valueOf(4.1), indice.getIndiceGeral());

        // dica:
        // caso a aplicação não passe no teste
        // o problema pode ser o fato de no serviço
        // estar sendo utilizado double como ponto flutuante
        // e não com objeto, por isso a falta de precisão
        // nos calculos
    }

    private List<Producao> todasProducoes() {
        // uma forma mais facil de fazer isso
        // seria utilizando um ENUM
        Producao prodA1Teste = Producao.builder().tipo("C").qualis("A1").ano(2021).build();
        Producao prodA1Salva = prodRepo.save(prodA1Teste);

        Producao prodA2Teste = Producao.builder().tipo("C").qualis("A2").ano(2021).build();
        Producao prodA2Salva = prodRepo.save(prodA2Teste);

        Producao prodA3Teste = Producao.builder().tipo("C").tipo("C").qualis("A3").ano(2021).build();
        Producao prodA3Salva = prodRepo.save(prodA3Teste);

        Producao prodA4Teste = Producao.builder().tipo("C").qualis("A4").ano(2021).build();
        Producao prodA4Salva = prodRepo.save(prodA4Teste);

        Producao prodB1Teste = Producao.builder().tipo("C").qualis("B1").ano(2021).build();
        Producao prodB1Salva = prodRepo.save(prodB1Teste);

        Producao prodB2Teste = Producao.builder().tipo("C").qualis("B2").ano(2021).build();
        Producao prodB2Salva = prodRepo.save(prodB2Teste);

        Producao prodB3Teste = Producao.builder().tipo("C").qualis("B3").ano(2021).build();
        Producao prodB3Salva = prodRepo.save(prodB3Teste);

        Producao prodB4Teste = Producao.builder().tipo("C").qualis("B4").ano(2021).build();
        Producao prodB4Salva = prodRepo.save(prodB4Teste);

        return Arrays.asList(prodA1Salva, prodA2Salva, prodA3Salva, prodA4Salva, prodB1Salva, prodB2Salva, prodB3Salva,
                prodB4Salva);
    }

}
