package br.ufma.sppg.repo;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufma.sppg.dto.DocenteProdDTO;
import br.ufma.sppg.dto.ProducaoDTO;
import br.ufma.sppg.dto.TecnicaDTO;
import br.ufma.sppg.dto.TecnicaProdDTO;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Tecnica;

public interface TecnicaRepository extends JpaRepository<Tecnica, Integer> {
    Optional<Tecnica> findById(Integer idTecnica);
   
    boolean existsById(Integer idTecnica);
    
    // Obter todas as técnicas de um docente em um período
    @Query("SELECT t FROM Tecnica t " +
            "JOIN Docente d " +
            "WHERE d.id = :idDocente " +
            "AND t.ano >= :anoInicio " +
            "AND t.ano <= :anoFim")
    Optional<List<Tecnica>> obterTecnicasDocentePorPeriodo(Integer idDocente, Integer anoInicio, Integer anoFim);

    // Obter todas as técnicas de uma orientação em um período
    @Query("SELECT t FROM Tecnica t " +
            "JOIN Orientacao o " +
            "WHERE o.id = :idOrientacao " +
            "AND t.ano >= :anoInicio " +
            "AND t.ano <= :anoFim")
    Optional<List<Tecnica>> obterTecnicasOrientacaoPorPeriodo(Integer idOrientacao, Integer anoInicio,
            Integer anoFim);

    @Query("SELECT t FROM Tecnica t " +
            "JOIN Docente d " +
            "JOIN Programa p " +
            "WHERE p.id = :idPrograma")
    Optional<List<Tecnica>> obterTecnicasPPG(Integer idPrograma);

    @Query("SELECT t FROM Tecnica t " +
            "JOIN Docente d " +
            "JOIN Programa p " +
            "WHERE p.id = :idPrograma " +
            "AND t.ano >= :anoInicio " +
            "AND t.ano <= :anoFim")
    Optional<List<Tecnica>> obterTecnicasPPGPorPeriodo(Integer idPrograma, Integer anoInicio, Integer anoFim);

    Tecnica findByTitulo(String titulo);

        @Query("SELECT new br.ufma.sppg.dto.TecnicaDTO(t.id, t.ano, d.nome, t.titulo, t.financiadora, CASE WHEN t.orientacoes IS EMPTY THEN 'Não' ELSE 'Sim' END, concat(STR(t.qtdGrad), 'G|', STR(t.qtdMestrado), 'M|', STR(t.qtdDoutorado), 'D')) FROM Tecnica t JOIN t.docentes d")
        Optional<List<TecnicaDTO>> obterTecnicasDTO();

        @Query("SELECT d.id FROM Tecnica t JOIN t.docentes d WHERE t.id = :idTecnica")
        Optional<List<Integer>> obterDocentesId(@Param("idTecnica") Integer id);

        @Query("SELECT o.id FROM Tecnica t JOIN t.orientacoes o WHERE t.id = :idTecnica")
        Optional<List<Integer>> obterOrientacoesId(@Param("idTecnica") Integer id);
}
