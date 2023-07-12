package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.dto.DocenteQualisDTO;
import br.ufma.sppg.dto.FiltroPPGDTO;
import br.ufma.sppg.dto.IndicadoresDTO;
import br.ufma.sppg.dto.Indice;
import br.ufma.sppg.dto.InfoGraficoDTO;
import br.ufma.sppg.dto.QualisProducoesDTO;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;
import jakarta.transaction.Transactional;

@Service
public class ProgramaService {

    @Autowired
    ProgramaRepository repository;

    public List<Programa> obterPrograma(String nome) {
        verificarNome(nome);
        return repository.findAllByNome(nome).get();
    }

    public List<Docente> obterDocentesPrograma(Integer idPrograma) {
        verificarId(idPrograma);
        return repository.obterDocentes(idPrograma).get();
    }

    public Indice obterProducaoIndices(Integer idPrograma, Integer anoIni, Integer anoFin) {
        verificarId(idPrograma);
        verificarData(anoIni, anoFin);
        Double iRestrito = Double.valueOf(0.0);
        Double iNRestrito = Double.valueOf(0.0);
        Double iGeral = Double.valueOf(0.0);
        List<Producao> producoes = repository.obterProducoes(idPrograma, anoIni, anoFin).get();

        for (Producao producao : producoes) {

            switch (producao.getQualis()) {
                case "A1":
                    iRestrito += Double.valueOf(1.0);
                    break;

                case "A2":
                    iRestrito += Double.valueOf(0.85);
                    break;

                case "A3":
                    iRestrito += Double.valueOf(0.725);
                    break;

                case "A4":
                    iRestrito += Double.valueOf(0.625);
                    break;

                case "B1":
                    iNRestrito += Double.valueOf(0.5);
                    break;

                case "B2":
                    iNRestrito += Double.valueOf(0.25);
                    break;

                case "B3":
                    iNRestrito += Double.valueOf(0.1);
                    break;

                case "B4":
                    iNRestrito += Double.valueOf(0.05);
                    break;

                default:
                    throw new ServicoRuntimeException("Uma das produções possui o Qualis inválido");
            }
        }
        iGeral = iRestrito + iNRestrito;

        return new Indice(iRestrito, iNRestrito, iGeral);
    }

    // devolve uma List<Orientacao> de um dado programa dentro de um periodo
    @Transactional
    public List<Orientacao> obterOrientacoes(Integer idPrograma, Integer anoIni, Integer anoFin) {
        verificarId(idPrograma);
        verificarData(anoIni, anoFin);
        List<Orientacao> orientacoes = repository.obterOrientacoes(idPrograma, anoIni, anoFin).get();
        //List<Docente> docentes = repository.obterDocentes(idPrograma);
        //List<Orientacao> orientacoesDoc = new ArrayList<>();
        //ArrayList<Integer> idOrientacoes = new ArrayList<>();

        // verifica as Orientacões de cada docente e filtra as repetidas para não
        // adicona-las mais de uma vez
        // for (Docente docente : docentes) {

        //     orientacoesDoc = docente.getOrientacoes();
        //     for (Orientacao orientacao : orientacoesDoc) {

        //         if (orientacao.getAno() >= anoIni && orientacao.getAno() <= anoFin
        //                 && !idOrientacoes.contains(orientacao.getId())) {

        //             idOrientacoes.add(orientacao.getId());
        //             orientacoes.add(orientacao);
        //         }
        //     }
        // }

        return orientacoes;
    }

    // devolve uma List<Producao> de um dado programa dentro de um periodo
    public List<Producao> obterProducoes2(Integer idPrograma, Integer anoIni, Integer anoFin) {
        verificarId(idPrograma);
        verificarData(anoIni, anoFin);
        List<Producao> producoes = new ArrayList<>();
        List<Docente> docentes = repository.obterDocentes(idPrograma).get();
        List<Producao> producoesDoc = new ArrayList<>();
        ArrayList<Integer> idProducoes = new ArrayList<>();

        // verifica as producões de cada docente e filtra as repetidas para não
        // adicona-las mais de uma vez
        for (Docente docente : docentes) {

            producoesDoc = docente.getProducoes();
            for (Producao producao : producoesDoc) {

                if (producao.getAno() >= anoIni && producao.getAno() <= anoFin
                        && !idProducoes.contains(producao.getId())) {

                    idProducoes.add(producao.getId());
                    producoes.add(producao);
                }
            }
        }

        return producoes;
    }

    public List<Producao> obterProducoes(Integer idPrograma, Integer anoIni, Integer anoFin){
        verificarId(idPrograma);
        verificarData(anoIni, anoFin);
        List<Producao> producoes = repository.obterProducoes(idPrograma, anoIni, anoFin).get();

        return producoes;
    }

    // devolve uma List<Tecnica> de um dado programa dentro de um periodo
    public List<Tecnica> obterTecnicas2(Integer idPrograma, Integer anoIni, Integer anoFin) {
        verificarId(idPrograma);
        verificarData(anoIni, anoFin);
        List<Tecnica> tecnicas = new ArrayList<>();
        List<Docente> docentes = repository.obterDocentes(idPrograma).get();
        List<Tecnica> tecnicasDoc = new ArrayList<>();
        ArrayList<Integer> idTecnicas = new ArrayList<>();

        // verifica as tecnicas de cada docente e filtra as repetidas para não
        // adicona-las mais de uma vez
        for (Docente docente : docentes) {

            tecnicasDoc = docente.getTecnicas();
            for (Tecnica tecnica : tecnicasDoc) {

                if (tecnica.getAno() >= anoIni && tecnica.getAno() <= anoFin && !idTecnicas.contains(tecnica.getId())) {

                    idTecnicas.add(tecnica.getId());
                    tecnicas.add(tecnica);
                }
            }
        }

        return tecnicas;
    }

    public List<Tecnica> obterTecnicas(Integer idPrograma, Integer anoIni, Integer anoFin){
        verificarId(idPrograma);
        verificarData(anoIni, anoFin);
        List<Tecnica> tecnicas = repository.obterTecnicas(idPrograma, anoIni, anoFin).get();

        return tecnicas;
    }

    // conta quantas orientações possuem ao menos 1 producao e devolve quantas
    // cumprem essa meta
    //TODO alterar quantitativo
    public Integer quantitativoOrientacaoProducao(Integer idPrograma, Integer anoIni, Integer anoFin) {
        verificarId(idPrograma);
        verificarData(anoIni, anoFin);
        List<Docente> docentes = repository.obterDocentes(idPrograma).get();
        List<Orientacao> orientacoesDoc = new ArrayList<>();
        ArrayList<Integer> idOrientacoes = new ArrayList<>();

        // verifica as orientações de cada docente que pertence ao programa
        for (Docente docente : docentes) {

            orientacoesDoc = docente.getOrientacoes();
            for (Orientacao orientacao : orientacoesDoc) {

                if (orientacao.getAno() >= anoIni && orientacao.getAno() <= anoFin
                        && !idOrientacoes.contains(orientacao.getId()) && !orientacao.getProducoes().isEmpty()) {

                    idOrientacoes.add(orientacao.getId());
                }
            }
        }

        return idOrientacoes.size();
    }

    // conta quantas orientações possuem ao menos 1 tecnica e devolve quantas
    // cumprem essa meta
    // TODO alterar quantitativo
    public Integer quantitativoOrientacaoTecnica(Integer idPrograma, Integer anoIni, Integer anoFin) {
        verificarId(idPrograma);
        verificarData(anoIni, anoFin);
        List<Docente> docentes = repository.obterDocentes(idPrograma).get();
        List<Orientacao> orientacoesDoc = new ArrayList<>();
        ArrayList<Integer> idOrientacoes = new ArrayList<>();

        // verifica as orientações de cada docente que pertence ao programa
        for (Docente docente : docentes) {

            orientacoesDoc = docente.getOrientacoes();
            for (Orientacao orientacao : orientacoesDoc) {

                if (orientacao.getAno() >= anoIni && orientacao.getAno() <= anoFin
                        && !idOrientacoes.contains(orientacao.getId()) && !orientacao.getTecnicas().isEmpty()) {

                    idOrientacoes.add(orientacao.getId());
                }
            }
        }

        return idOrientacoes.size();
    }

    // obtem os indiadores do programa escolhido dentro do limite de tempo escolhido
    public IndicadoresDTO obterIndicadores(Integer idPPG, Integer anoIni, Integer anoFin){
        verificarId(idPPG);
        verificarData(anoIni, anoFin);
        Double iRestrito = 0.0;
        Double iNRestrito = 0.0;
        Double iGeral = 0.0;
        List<Producao> producoes = repository.obterProducoes(idPPG, anoIni, anoFin).get();

        for (Producao producao : producoes) {

            switch (producao.getQualis()) {
                case "A1":
                    iRestrito += 1.0f;
                    break;

                case "A2":
                    iRestrito += 0.85;
                    break;

                case "A3":
                    iRestrito += 0.725;
                    break;

                case "A4":
                    iRestrito += 0.625;
                    break;

                case "B1":
                    iNRestrito += 0.5;
                    break;

                case "B2":
                    iNRestrito += 0.25;
                    break;

                case "B3":
                    iNRestrito += 0.1;
                    break;

                case "B4":
                    iNRestrito += 0.05;
                    break;

                default:
                    throw new ServicoRuntimeException("Uma das produções possui o Qualis inválido");
            }
        }
        iGeral = iRestrito + iNRestrito;

        return new IndicadoresDTO("" + iGeral, "" + iRestrito, "" + iNRestrito, "" + producoes.size());
    }

    public InfoGraficoDTO obterGrafico(Integer idPrograma, Integer anoIni, Integer anoFin){
        List<Integer> anos = new ArrayList<>();
        List<Integer> a1s = new ArrayList<>();
        List<Integer> a2s = new ArrayList<>();
        List<Integer> a3s = new ArrayList<>();
        List<Integer> a4s = new ArrayList<>();
        Integer num = 0;
        Integer index = 0;
        Integer i = anoFin;
        List<Producao> prodList = repository.obterProducoes(idPrograma, anoIni, anoFin).get();
        while(i >= anoIni){
            anos.add(i);
            a1s.add(0);
            a2s.add(0);
            a3s.add(0);
            a4s.add(0);
            i -= 1;
        }
        for(Producao prod : prodList){
            index = anoFin - prod.getAno();
            if(index >= anos.size()){
                continue;
            }
            if(prod.getQualis().equals("A1")){
                num = a1s.get(index) + 1;
                a1s.set(index, num);
            }else{
                if(prod.getQualis().equals("A2")){
                    num = a2s.get(index) + 1;
                    a2s.set(index, num);
                }else{
                    if(prod.getQualis().equals("A3")){
                        num = a3s.get(index) + 1;
                        a3s.set(index, num);
                    }else{
                        if(prod.getQualis().equals("A4")){
                            num = a4s.get(index) + 1;
                            a4s.set(index, num);
                        }
                    }
                }
            }
        }

        return new InfoGraficoDTO(anos, a1s, a2s, a3s, a4s);
        
    }

    public List<FiltroPPGDTO> obterFiltroPPG(){
        List<FiltroPPGDTO> filtro = repository.obterFiltroPPG().get();

        return filtro;
    }

    public List<DocenteQualisDTO> obterDocentesQualis(Integer idPrograma){
        verificarId(idPrograma);
        List<Docente> docentes = repository.obterDocentes(idPrograma).get();
        List<DocenteQualisDTO> listQualisDTO = new ArrayList<>();
        for(Docente docente : docentes){
            QualisProducoesDTO dto = obterQualisProducoes(docente.getProducoes());
            DocenteQualisDTO qualisDTO = new DocenteQualisDTO(docente.getId(), docente.getNome(), dto.getA1(), dto.getA2(), dto.getA3(), dto.getA4(), dto.getB1(), dto.getB2(), dto.getB3(), dto.getB4());
            listQualisDTO.add(qualisDTO);
        }

        return listQualisDTO;

    }

    private QualisProducoesDTO obterQualisProducoes(List<Producao> prodList){
        Integer a1 = 0;
        Integer a2 = 0;
        Integer a3 = 0;
        Integer a4 = 0;
        Integer b1 = 0;
        Integer b2 = 0;
        Integer b3 = 0;
        Integer b4 = 0;
        
        for(Producao prod : prodList){
            switch (prod.getQualis()) {
                case "A1":
                    a1 += 1;
                    break;

                case "A2":
                    a2 += 1;
                    break;

                case "A3":
                    a3 += 1;
                    break;

                case "A4":
                    a4 += 1;
                    break;

                case "B1":
                    b1 += 1;
                    break;

                case "B2":
                    b2 += 1;
                    break;

                case "B3":
                    b3 += 1;
                    break;

                case "B4":
                    b4 += 1;
                    break;

                default:
                    throw new ServicoRuntimeException("Uma das produções possui o Qualis inválido");
            }
        }

        return new QualisProducoesDTO(a1, a2, a3, a4, b1, b2, b3, b4);
    }

    private void verificarNome(String nome) {
        if (nome == null) {
            throw new ServicoRuntimeException("Nome do Programa inválido");
        }
        if (nome.trim().equals("")) {
            throw new ServicoRuntimeException("Nome do Programa inválido");
        }
    }

    private void verificarId(Integer idPrograma) {
        verificarNumero(idPrograma);
        if (!repository.existsById(idPrograma)) {
            throw new ServicoRuntimeException("Id do Programa não está registrado");
        }
    }

    private void verificarData(Integer data1, Integer data2) {
        verificarNumero(data1);
        verificarNumero(data2);
        if (data1 > data2) {
            throw new ServicoRuntimeException("Data inical maior que a data final");
        }
    }

    private void verificarNumero(Integer numero) {
        if (numero == null) {
            throw new ServicoRuntimeException("Número Inválido");
        }

    }

}

/*
 * spring.datasource.url=jdbc:postgresql://horton.db.elephantsql.com:5432/hckvzauf
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.username=hckvzauf
spring.datasource.password=Q44izx1iP5Q4pW4dv5UGBU1lIQpKYtrE
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.defer-datasource-initialization=true
 */