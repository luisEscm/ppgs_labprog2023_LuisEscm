package br.ufma.sppg.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufma.sppg.dto.DocenteBaseDTO;
import br.ufma.sppg.dto.DocenteProdDTO;
import br.ufma.sppg.dto.OrientacaoDocenteDTO;
import br.ufma.sppg.dto.ProducaoDTO;
import br.ufma.sppg.dto.ProducaoDocenteDTO;
import br.ufma.sppg.dto.TecnicaDTO;
import br.ufma.sppg.dto.TecnicaDocenteDTO;
import br.ufma.sppg.model.*;

public interface DocenteRepository 
    extends JpaRepository<Docente, Integer>{

    @Query("select d from Docente d where d.nome= :nome")
    List<Docente> findAllByNome(@Param("nome") String nome);

    Optional<Docente> findByNome(String nome);
    
    Optional<Docente> findById(Integer idDocente);

    boolean existsById(Integer idDocente);

    @Query("SELECT d FROM Docente d WHERE d.id in :ids")
    Optional<List<Docente>> obterDocentes(@Param("ids") List<Integer> idDocentes);

    @Query("SELECT new br.ufma.sppg.dto.ProducaoDTO(p.id, p.ano, d.nome, p.titulo, p.nomeLocal, CASE WHEN p.orientacoes IS EMPTY THEN 'Não' ELSE 'Sim' END, concat(STR(p.qtdGrad), 'G|', STR(p.qtdMestrado), 'M|', STR(p.qtdDoutorado), 'D')) FROM Docente d JOIN d.producoes p WHERE (d.id = :idDocente)")
    Optional<List<ProducaoDTO>> obterProducoesNoDate(@Param("idDocente") Integer idDocente);

    @Query("SELECT new br.ufma.sppg.dto.TecnicaDTO(t.id, t.ano, d.nome, t.titulo, t.financiadora, CASE WHEN t.orientacoes IS EMPTY THEN 'Não' ELSE 'Sim' END, concat(STR(t.qtdGrad), 'G|', STR(t.qtdMestrado), 'M|', STR(t.qtdDoutorado), 'D')) FROM Docente d JOIN d.tecnicas t WHERE (d.id = :idDocente)")
    Optional<List<TecnicaDTO>> obterTecnicasNoDate(@Param("idDocente") Integer idDocente);

    @Query("SELECT p FROM Docente d JOIN d.producoes p WHERE (d.id = :idDocente AND p.ano >= :anoIni AND p.ano <= :anoFin)")
    List<Producao> obterProducoes(@Param("idDocente") Integer idDocente, @Param("anoIni") Integer anoIni, @Param("anoFin") Integer anoFin);

    @Query("SELECT p FROM Docente d JOIN d.producoes p WHERE (d.id = :idDocente AND p.ano >= :anoIni AND p.ano <= :anoFin AND p.tipo = 'P')")
    Optional<List<Producao>> obterProducoesP(@Param("idDocente") Integer idDocente, @Param("anoIni") Integer anoIni, @Param("anoFin") Integer anoFin);

    @Query("SELECT p FROM Docente d JOIN d.producoes p WHERE (d.id = :idDocente AND p.ano >= :anoIni AND p.ano <= :anoFin AND p.tipo = 'C')")
    Optional<List<Producao>> obterProducoesC(@Param("idDocente") Integer idDocente, @Param("anoIni") Integer anoIni, @Param("anoFin") Integer anoFin);

    @Query("SELECT o FROM Docente d JOIN d.orientacoes o WHERE (d.id = :idDocente AND o.ano >= :anoIni AND o.ano <= :anoFin)")
    List<Orientacao> obterOrientacoes(@Param("idDocente") Integer idDocente, @Param("anoIni") Integer anoIni, @Param("anoFin") Integer anoFin);

    @Query("SELECT new br.ufma.sppg.dto.OrientacaoDocenteDTO(o.discente, o.titulo, o.tipo, o.ano) FROM Docente d JOIN d.orientacoes o WHERE (d.id = :idDocente AND o.ano >= :anoIni AND o.ano <= :anoFin)")
    Optional<List<OrientacaoDocenteDTO>> obterOrientacoesDTO(@Param("idDocente") Integer idDocente, @Param("anoIni") Integer anoIni, @Param("anoFin") Integer anoFin);

    @Query("SELECT new br.ufma.sppg.dto.TecnicaDocenteDTO(t.titulo, t.tipo, t.ano) FROM Docente d JOIN d.tecnicas t WHERE (d.id = :idDocente AND t.ano >= :anoIni AND t.ano <= :anoFin)")
    Optional<List<TecnicaDocenteDTO>> obterTecnicasDTO(@Param("idDocente") Integer idDocente, @Param("anoIni") Integer anoIni, @Param("anoFin") Integer anoFin);

    @Query("SELECT new br.ufma.sppg.dto.ProducaoDocenteDTO(p.titulo, p.nomeLocal,CASE WHEN p.tipo = 'C' THEN 'Congresso' ELSE 'Periódico' END, p.qualis, p.ano) FROM Docente d JOIN d.producoes p WHERE (d.id = :idDocente AND p.ano >= :anoIni AND p.ano <= :anoFin)")
    Optional<List<ProducaoDocenteDTO>> obterProducoesDTO(@Param("idDocente") Integer idDocente, @Param("anoIni") Integer anoIni, @Param("anoFin") Integer anoFin);

    @Query("SELECT t FROM Docente d JOIN d.tecnicas t WHERE (d.id = :idDocente AND t.ano >= :anoIni AND t.ano <= :anoFin)")
    List<Tecnica> obterTecnicas(@Param("idDocente") Integer idDocente, @Param("anoIni") Integer anoIni, @Param("anoFin") Integer anoFin);

    @Query("SELECT new br.ufma.sppg.dto.DocenteBaseDTO(d.id, d.lattes, d.nome) FROM Docente d")
    Optional<List<DocenteBaseDTO>> obterDocentesBase();

    @Query("SELECT new br.ufma.sppg.dto.DocenteBaseDTO(d.id, d.lattes, d.nome) FROM Docente d WHERE d.id = :idDocente")
    Optional<DocenteBaseDTO> obterDocenteBase(@Param("idDocente") Integer idDocente);

    @Query("SELECT new br.ufma.sppg.dto.DocenteProdDTO(d.id, d.lattes, d.nome, false) FROM Docente d")
    Optional<List<DocenteProdDTO>> obterDocentesProdDTO();

    @Query("SELECT new br.ufma.sppg.dto.DocenteProdDTO(d.id, d.lattes, d.nome, false) FROM Docente d JOIN d.producoes p WHERE p.id = :id")
    Optional<List<DocenteProdDTO>> obterDocentesProdDTO(@Param("id") Integer idProd);

    @Query("SELECT new br.ufma.sppg.dto.DocenteProdDTO(d.id, d.lattes, d.nome, CASE WHEN t.id = :id THEN true ELSE false END) FROM Docente d JOIN d.tecnicas t")
    Optional<List<DocenteProdDTO>> obterDocentesTecnDTO(@Param("id") Integer idTecn);

    @Query("SELECT count(p) FROM Docente d JOIN d.producoes p WHERE (d.id = :idDocente AND p.ano >= :anoIni AND p.ano <= :anoFin AND p.qualis = :qualis)")
    Optional<Integer> obterQtdQualis(@Param("idDocente") Integer idDocente, @Param("anoIni") Integer anoIni, @Param("anoFin") Integer anoFin, @Param("qualis") String qualis);
}
