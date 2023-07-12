package br.ufma.sppg.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufma.sppg.dto.DocenteQualisDTO;
import br.ufma.sppg.dto.FiltroPPGDTO;
import br.ufma.sppg.model.*;

public interface ProgramaRepository 
    extends JpaRepository<Programa, Integer> {

        @Query("select p from Programa p where p.nome = :nomePPG")
        Optional<List<Programa>> findAllByNome(@Param("nomePPG") String nomePPG);

        Optional<Programa> findByNome(String nomePPG);

        @Query("select p.docentes from Programa p where p.id = :idPPG")
        Optional<List<Docente>> obterDocentes(@Param("idPPG") Integer idPPG);

        @Query("select new br.ufma.sppg.dto.FiltroPPGDTO(p.id, p.nome) from Programa p")
        Optional<List<FiltroPPGDTO>> obterFiltroPPG();

        @Query("SELECT p FROM Programa p JOIN p.docentes d JOIN d.orientacoes o where p.id = :idPrograma AND o.ano >= :anoInicio AND o.ano<= :anoFim")
        Optional<List<Orientacao>> obterOrientacoes(Integer idPrograma, Integer anoInicio, Integer anoFim);

        @Query("SELECT pr FROM Programa p JOIN p.docentes d JOIN d.producoes pr where p.id = :idPrograma AND pr.ano >= :anoInicio AND pr.ano<= :anoFim")
        Optional<List<Producao>> obterProducoes(Integer idPrograma, Integer anoInicio, Integer anoFim);

        @Query("SELECT p FROM Programa p JOIN p.docentes d JOIN d.tecnicas t where p.id = :idPrograma AND t.ano >= :anoInicio AND t.ano<= :anoFim")
        Optional<List<Tecnica>> obterTecnicas(Integer idPrograma, Integer anoInicio, Integer anoFim);

        /*@Query("select count(o) from Orientacao o join o.orientador d join d.programas p" + 
               " where (p.id = :idPrograma and o.ano >= :anoIni and o.ano <= :anoFin and o.producoes is not empty)")
        Integer quantitatioOrientacaoProducao(@Param("idPrograma") Integer idPrograma, @Param("anoIni") Integer anoIni, @Param("anoFin") Integer anoFin);

        @Query("select count(o) from Orientacao o join o.orientador d join d.programas p" + 
        " where (p.id = :idPrograma and o.ano >= :anoIni and o.ano <= :anoFin and o.tecnicas is not empty)")
        Integer quantitatioOrientacaoTecnica(@Param("idPrograma") Integer idPrograma, @Param("anoIni") Integer anoIni, @Param("anoFin") Integer anoFin);

        @Query("select p from Programa pr join pr.docentes d join d.producoes p" + 
        " where (pr.id = :idPrograma)")
        List<Producao> quantitatioProducao(@Param("idPrograma") Integer idPrograma, @Param("anoIni") Integer anoIni, @Param("anoFin") Integer anoFin);*/
}
/*
spring.datasource.url=jdbc:postgresql://hckvzauf:Q44izx1iP5Q4pW4dv5UGBU1lIQpKYtrE@horton.db.elephantsql.com/hckvzauf
spring.datasource.username=hckvzauf
spring.datasource.password=Q44izx1iP5Q4pW4dv5UGBU1lIQpKYtrE
spring.datasource.driver-class-name=org.postgresql.Driverspring.datasource.url=jdbc:postgresql://hckvzauf:Q44izx1iP5Q4pW4dv5UGBU1lIQpKYtrE@horton.db.elephantsql.com/hckvzauf
spring.datasource.username=hckvzauf
spring.datasource.password=Q44izx1iP5Q4pW4dv5UGBU1lIQpKYtrE
spring.datasource.driver-class-name=org.postgresql.Driver*/
