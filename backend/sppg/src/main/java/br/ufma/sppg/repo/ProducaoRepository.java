package br.ufma.sppg.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufma.sppg.dto.ProducaoDTO;
import br.ufma.sppg.model.Producao;

public interface ProducaoRepository 
    extends JpaRepository<Producao,Integer> {
    
        Producao findByTituloAndNomeLocal(String titulo, String nomeLocal);
        Producao findByTitulo(String titulo);

    @Query("select new br.ufma.sppg.dto.ProducaoDTO(p.id, p.ano, d.nome, p.titulo, p.nomeLocal, CASE WHEN p.orientacoes IS EMPTY THEN 'Não' ELSE 'Sim' END, concat(STR(p.qtdGrad), 'G|', STR(p.qtdMestrado), 'M|', STR(p.qtdDoutorado), 'D')) from Producao p join p.docentes d")
    Optional<List<ProducaoDTO>> obterProducoesDTO();

    @Query("SELECT d.id FROM Producao p JOIN p.docentes d WHERE p.id = :idProducao")
    Optional<List<Integer>> obterDocentesId(@Param("idProducao") Integer id);

    @Query("SELECT o.id FROM Producao p JOIN p.orientacoes o WHERE p.id = :idProducao")
    Optional<List<Integer>> obterOrientacoesId(@Param("idProducao") Integer id);
}
