package br.ufma.sppg.repo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.dto.Indice;
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
public class ProgramaRespositoryTeste {
    
    @Autowired
    ProgramaRepository repo;
    
    @Autowired
    DocenteRepository docRepo;

    @Autowired
    ProducaoRepository prodRepo;

    @Test
    public void deveDevolverQuantitativodasProducoes(){
        //Cenário
        Docente docente1 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();

        Docente docente2 = Docente.builder().nome("docente1")
                                            .dataAtualizacao(new Date())
                                            .lattes("lattes1")
                                            .build();
        
        Producao producao1 = Producao.builder().tipo("tipo1")
                                                .issnOuSigla("sigla1")
                                                .ano(2001)
                                                .nomeLocal("Local1")
                                                .titulo("titulo1")
                                                .qualis("A1")
                                                .percentileOuH5(1f)
                                                .qtdGrad(1)
                                                .qtdMestrado(2)
                                                .qtdDoutorado(3)
                                                .build();

        Producao producao2 = Producao.builder().tipo("tipo2")
                                                .issnOuSigla("sigla2")
                                                .ano(2007)
                                                .nomeLocal("Local2")
                                                .titulo("titulo2")
                                                .qualis("A2")
                                                .percentileOuH5(7f)
                                                .qtdGrad(1)
                                                .qtdMestrado(3)
                                                .qtdDoutorado(3)
                                                .build();

        Producao producao3 = Producao.builder().tipo("tipo3")
                                                .issnOuSigla("sigla3")
                                                .ano(2003)
                                                .nomeLocal("Local3")
                                                .titulo("titulo3")
                                                .qualis("A3")
                                                .percentileOuH5(2f)
                                                .qtdGrad(2)
                                                .qtdMestrado(2)
                                                .qtdDoutorado(5)
                                                .build();
        
        Programa programa = Programa.builder()
                                    .nome("Programa1")
                                    .build();

        Producao prodSalvo1 = prodRepo.save(producao1);
        Producao prodSalvo2 = prodRepo.save(producao2);
        Producao prodSalvo3 = prodRepo.save(producao3);

        List<Producao> lista1 = new ArrayList<>();
        List<Producao> lista2 = new ArrayList<>();
        
        lista1.add(prodSalvo1);
        lista1.add(prodSalvo2);
        lista2.add(prodSalvo1);
        lista2.add(prodSalvo3);

        Programa salvo = repo.save(programa);
        List<Programa> listProg = new ArrayList<>();

        listProg.add(salvo);


        docente1.setProducoes(lista1);
        docente2.setProducoes(lista2);

        docente1.setProgramas(listProg);
        docente2.setProgramas(listProg);

        Docente salvo1 = docRepo.save(docente1);
        docRepo.save(docente2);

        //Ação

        //Integer quantProd = repo.quantitativoDocentes(salvo.getId());

        //Verificação
        //Assertions.assertNotNull(quantProd);
        //Assertions.assertEquals(2, quantProd);
    }
}
