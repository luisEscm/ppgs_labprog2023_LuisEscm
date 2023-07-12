package br.ufma.sppg.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufma.sppg.dto.OrientacaoNewProdDTO;
import br.ufma.sppg.dto.OrientacaoProdDTO;
import br.ufma.sppg.model.Orientacao;

public interface OrientacaoRepository extends JpaRepository<Orientacao, Integer> {
        @Query("SELECT o FROM Orientacao o WHERE o.id in :ids")
        Optional<List<Orientacao>> obterOrientacoes(@Param("ids") List<Integer> id);

        @Query("SELECT o FROM Orientacao o JOIN o.orientador d JOIN d.programas p where p.id = :idPrograma AND o.ano >= :anoInicio AND o.ano<= :anoFim")
        Optional<List<Orientacao>> findByPPG(@Param("idPrograma") Integer idPrograma, @Param("anoInicio") Integer anoInicio, @Param("anoFim") Integer anoFim);

        @Query("SELECT o FROM Orientacao o JOIN o.orientador d WHERE d.id = :idDocente AND o.ano >= :anoInicio AND o.ano<= :anoFim")
        Optional<List<Orientacao>> findByDocente(@Param("idDocente") Integer idDocente, @Param("anoInicio") Integer anoInicio, @Param("anoFim") Integer anoFim);

        @Query("SELECT o FROM Orientacao o " +
                        "JOIN Docente d " +
                        "JOIN Tecnica t " +
                        "WHERE d.id = :idDocente " +
                        "AND o.ano >= :anoInicio " +
                        "AND o.ano <= :anoFim " +
                        "AND t.ano >= :anoInicio " +
                        "AND t.ano <= :anoFim")
        Optional<List<Orientacao>> obterOrientacoesComTecnicaPorPeriodo(@Param("idDocente") Integer idDocente, @Param("anoInicio") Integer anoInicio,
                        @Param("anoFim") Integer anoFim);

        @Query("SELECT o FROM Orientacao o " +
                        "JOIN Docente d " +
                        "JOIN Producao p " +
                        "WHERE d.id = :idDocente " +
                        "AND o.ano >= :anoInicio " +
                        "AND o.ano <= :anoFim " +
                        "AND p.ano >= :anoInicio " +
                        "AND p.ano <= :anoFim")
        Optional<List<Orientacao>> obterOrientacoesComProducaoPorPeriodo(@Param("idDocente") Integer idDocente, @Param("anoInicio") Integer anoInicio,
                        @Param("anoFim") Integer anoFim);

        Orientacao findByTipoAndDiscenteAndTitulo(String tipo, String discente, String titulo);

        @Query("SELECT o FROM Orientacao o JOIN o.producoes p WHERE ((o.id IN :ids OR p.id = :idProducao) AND (NOT (p.id = :idProducao AND o.id IN :ids)))")
        Optional<List<Orientacao>> obterOrientacoesProd(@Param("ids") List<Integer> ids, @Param("idProducao") Integer Producao);

        @Query("SELECT o FROM Orientacao o JOIN o.tecnicas t WHERE ((o.id IN :ids OR t.id = :idTecnica) AND (NOT (t.id = :idTecnica AND o.id IN :ids)))")
        Optional<List<Orientacao>> obterOrientacoesTecn(@Param("ids") List<Integer> ids, @Param("idTecnica") Integer Tecnica);

        @Query("SELECT new br.ufma.sppg.dto.OrientacaoProdDTO(o.id, d.nome, o.titulo, false) FROM Orientacao o JOIN o.orientador d ")
        Optional<List<OrientacaoProdDTO>> obterOrientacoesProdDTO();

        @Query("SELECT new br.ufma.sppg.dto.OrientacaoNewProdDTO(o.id, o.titulo, d.nome, o.tipo, o.instituicao, o.curso, o.ano, false) FROM Orientacao o JOIN o.orientador d")
        Optional<List<OrientacaoNewProdDTO>> obterOrientacoesNewProdDTO();

        @Query("SELECT new br.ufma.sppg.dto.OrientacaoNewProdDTO(o.id, o.titulo, d.nome, o.tipo, o.instituicao, o.curso, o.ano, false) FROM Orientacao o JOIN o.orientador d JOIN o.producoes p WHERE p.id = :id")
        Optional<List<OrientacaoNewProdDTO>> obterOrientacoesNewProdDTO(@Param("id") Integer idProducao);

        @Query("SELECT new br.ufma.sppg.dto.OrientacaoNewProdDTO(o.id, o.titulo, d.nome, o.tipo, o.instituicao, o.curso, o.ano, false) FROM Orientacao o JOIN o.orientador d JOIN o.tecnicas t WHERE t.id = :id")
        Optional<List<OrientacaoNewProdDTO>> obterOrientacoesNewTecnDTO(@Param("id") Integer idTecn);
}
