package br.ufma.sppg.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProducaoTest {

    @Autowired
    ProducaoRepository prod;

    @Autowired
    OrientacaoRepository orie;

    @Test
    public void deveSalvarProducao() throws ParseException {
        // cenário

        Producao novaProducao = Producao.builder().tipo("A").ano(2022).issnOuSigla("ABC")
                .nomeLocal("Ufma").titulo("Classificação de Cancer de mama")
                .qualis("qualisemxlm").percentileOuH5(Float.valueOf(2)).qtdGrad(10)
                .qtdMestrado(20).qtdDoutorado(10).build();
        // acao
        Producao prodSalvo = prod.save(novaProducao);

        // teste
        Assertions.assertNotNull(prodSalvo);
        Assertions.assertEquals(novaProducao, prodSalvo);
        Assertions.assertEquals(novaProducao.getId(), prodSalvo.getId());
        Assertions.assertEquals(novaProducao.getAno(), prodSalvo.getAno());
        Assertions.assertEquals(novaProducao.getPercentileOuH5(), prodSalvo.getPercentileOuH5());
        Assertions.assertEquals(novaProducao.getNomeLocal(), prodSalvo.getNomeLocal());
        Assertions.assertEquals(novaProducao.getQualis(), prodSalvo.getQualis());
        Assertions.assertEquals(novaProducao.getQtdDoutorado(), prodSalvo.getQtdDoutorado());
        Assertions.assertEquals(novaProducao.getQtdGrad(), prodSalvo.getQtdGrad());
        Assertions.assertEquals(novaProducao.getQtdMestrado(), prodSalvo.getQtdMestrado());
        Assertions.assertEquals(novaProducao.getTipo(), prodSalvo.getTipo());
        Assertions.assertEquals(novaProducao.getTitulo(), prodSalvo.getTitulo());
        Assertions.assertEquals(novaProducao.getIssnOuSigla(), prodSalvo.getIssnOuSigla());

    }

    @Test
    public void deveAtualizarEstatisticasProducao() throws ParseException {
        // cenário
        Producao novaProducao = Producao.builder().tipo("A").issnOuSigla("ABC")
                .nomeLocal("Ufma").titulo("Classificação de Cancer de mama").ano(2001)
                .qualis("qualisemxlm").percentileOuH5(Float.valueOf(2)).qtdGrad(10)
                .qtdMestrado(20).qtdDoutorado(10).build();

        // acao
        // original
        Producao prodSalvo = prod.save(novaProducao);
        // verificando se salvou
        Assertions.assertNotNull(prodSalvo.getId());
        Assertions.assertNotNull(prodSalvo);

        // atualizando componentes da producao
        prodSalvo.setAno(2020);
        prodSalvo.setTipo("A");
        prodSalvo.setQtdDoutorado(10);
        prodSalvo.setQualis("F");

        // Salvar a producao atualizada
        prod.save(prodSalvo);

        // verificacao
        // procurando a producao pelo id no repositório
        Integer id = prodSalvo.getId();
        Optional<Producao> temp = prod.findById(id);
        Assertions.assertTrue(temp.isPresent());

        // atribuindo a producao encontrada a uma nova variável
        Producao prodAtualizado = temp.get();

        Assertions.assertNotEquals(novaProducao.getAno(), prodAtualizado.getAno());
        Assertions.assertNotEquals(novaProducao.getTipo(), prodAtualizado.getTipo());
        Assertions.assertNotEquals(novaProducao.getTitulo(), prodAtualizado.getTitulo());
        Assertions.assertNotEquals(novaProducao.getQtdDoutorado(), prodAtualizado.getQtdDoutorado());
        Assertions.assertNotEquals(novaProducao.getQualis(), prodAtualizado.getQualis());

    }

    @Test
    public void deveSalvarProducaoComOrientacao() throws ParseException {
        // cenário
        // Orientacao orientacao = Orientacao.builder().tipo("TCC").ano(2023).discente("Gabriel").titulo("TCC")
        // .modalidade("Presencial").instituicao("UFMA").curso("Ciência da Computação").status("Ativo").build();
        Orientacao orientacaoSalvo = orie.findById(1).get();
        Orientacao orientacaoSalvo2 = orie.findById(3).get();

        Producao novaProducao = Producao.builder().tipo("A").ano(2022).issnOuSigla("ABC")
                .nomeLocal("Ufma").titulo("Classificação de Cancer de mama")
                .qualis("qualisemxlm").percentileOuH5(Float.valueOf(2)).qtdGrad(10)
                .qtdMestrado(20).qtdDoutorado(10).build();
        
        Producao outraProd = prod.findById(17).get();
        // acao
        Producao prodSalvo = prod.save(novaProducao);
        //Orientacao orientacaoSalvo = orie.save(orientacao);
        List<Orientacao> orientacoes = new ArrayList<>();
        orientacoes.add(orientacaoSalvo);
        orientacoes.add(orientacaoSalvo2);

        prodSalvo.setOrientacoes(Arrays.asList(orientacaoSalvo));
        outraProd.setOrientacoes(orientacoes);

        Producao prodSalvo2 = prod.save(prodSalvo);
        Producao outraPr = prod.save(outraProd);

        // teste
        Assertions.assertNotNull(prodSalvo);
        Assertions.assertEquals(1, prodSalvo2.getOrientacoes().size());
        Assertions.assertEquals(novaProducao.getId(), prodSalvo2.getId());
        Assertions.assertEquals(novaProducao.getAno(), prodSalvo2.getAno());
        Assertions.assertEquals(novaProducao.getPercentileOuH5(), prodSalvo2.getPercentileOuH5());
        Assertions.assertEquals(novaProducao.getNomeLocal(), prodSalvo2.getNomeLocal());
        Assertions.assertEquals(novaProducao.getQualis(), prodSalvo2.getQualis());
        Assertions.assertEquals(novaProducao.getQtdDoutorado(), prodSalvo2.getQtdDoutorado());
        Assertions.assertEquals(novaProducao.getQtdGrad(), prodSalvo2.getQtdGrad());
        Assertions.assertEquals(novaProducao.getQtdMestrado(), prodSalvo2.getQtdMestrado());
        Assertions.assertEquals(novaProducao.getTipo(), prodSalvo2.getTipo());
        Assertions.assertEquals(novaProducao.getTitulo(), prodSalvo2.getTitulo());
        Assertions.assertEquals(novaProducao.getIssnOuSigla(), prodSalvo2.getIssnOuSigla());

    }
}
