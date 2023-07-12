package br.ufma.sppg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import br.ufma.sppg.dto.AtualizaTecnicaDTO;
import br.ufma.sppg.dto.NewTecnicaDTO;
import br.ufma.sppg.dto.TecnicaDTO;
import br.ufma.sppg.dto.TecnicaProdDTO;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.repo.TecnicaRepository;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;
import jakarta.transaction.Transactional;

@Service
public class TecnicaService {

    @Autowired
    TecnicaRepository tecnicaRepo;

    @Autowired
    DocenteRepository docenteRepo;

    @Autowired
    ProgramaRepository programaRepo;

    @Autowired
    OrientacaoRepository orientacaoRepo;

    // Salva uma técnica
    @Transactional
    public Tecnica salvarTecnica(NewTecnicaDTO tecnicaDTO) {
        Tecnica tecnica = Tecnica.builder().tipo(tecnicaDTO.getTipo()).titulo(tecnicaDTO.getTitulo())
                                            .ano(tecnicaDTO.getAno()).financiadora(tecnicaDTO.getFinanciadora())
                                            .outrasInformacoes(tecnicaDTO.getOutrasInformacoes())
                                            .qtdGrad(tecnicaDTO.getQtdGrad()).qtdMestrado(tecnicaDTO.getQtdMestrado())
                                            .qtdDoutorado(tecnicaDTO.getQtdDoutorado()).build();
        validarTecnica(tecnica);
        Optional<List<Orientacao>> orientacoes = orientacaoRepo.obterOrientacoes(tecnicaDTO.getIdsOrientacoes());
        Optional<List<Docente>> docentes = docenteRepo.obterDocentes(tecnicaDTO.getIdsDocentes());
        if(docentes.isPresent()){
            tecnica.setDocentes(docentes.get());
        }
        if(orientacoes.isPresent()){
            tecnica.setOrientacoes(orientacoes.get());
        }
        return tecnicaRepo.save(tecnica);
    }

    @Transactional
    public Tecnica atualizarTecnica(AtualizaTecnicaDTO tecnicaDTO) {
        Tecnica tecnica = Tecnica.builder().id(tecnicaDTO.getId()).tipo(tecnicaDTO.getTipo()).titulo(tecnicaDTO.getTitulo())
                                            .ano(tecnicaDTO.getAno()).financiadora(tecnicaDTO.getFinanciadora())
                                            .outrasInformacoes(tecnicaDTO.getOutrasInformacoes())
                                            .qtdGrad(tecnicaDTO.getQtdGrad()).qtdMestrado(tecnicaDTO.getQtdMestrado())
                                            .qtdDoutorado(tecnicaDTO.getQtdDoutorado()).build();
        validarTecnica(tecnica);
        Optional<List<Orientacao>> orientacoes = orientacaoRepo.obterOrientacoes(tecnicaDTO.getIdsOrientacoes());
        Optional<List<Docente>> docentes = docenteRepo.obterDocentes(tecnicaDTO.getIdsDocentes());
        if(docentes.isPresent()){
            tecnica.setDocentes(docentes.get());
        }
        if(orientacoes.isPresent()){
            tecnica.setOrientacoes(orientacoes.get());
        }
        return tecnicaRepo.save(tecnica);
    }

    public NewTecnicaDTO obterTecnica(Integer idTecn){
        verificarIdTecnica(idTecn);
        List<Integer> docentes = tecnicaRepo.obterDocentesId(idTecn).get();
        List<Integer> orientacoes = tecnicaRepo.obterOrientacoesId(idTecn).get();

        Tecnica tecnica = tecnicaRepo.findById(idTecn).get();

        return new NewTecnicaDTO(tecnica.getTipo(), tecnica.getTitulo(), tecnica.getAno(), tecnica.getFinanciadora(), tecnica.getOutrasInformacoes(), tecnica.getQtdGrad(), tecnica.getQtdMestrado(), tecnica.getQtdDoutorado(), docentes, orientacoes);
    }

    public List<Tecnica> obterTodasTecnicas() {
        return tecnicaRepo.findAll();
    }

    public List<Tecnica> obterTecnicasComFiltro(Tecnica filtro) {
        if (filtro == null)
            throw new ServicoRuntimeException("O filtro está nulo");
        Example<Tecnica> example = Example.of(filtro, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(StringMatcher.CONTAINING));

        return tecnicaRepo.findAll(example);
    }

    @Transactional
    public void removerTecnica(Tecnica tecnica) {
        verificarIdTecnica(tecnica.getId());
        validarTecnica(tecnica);
        tecnicaRepo.delete(tecnica);
    }

    @Transactional
    public void removerTecnicaPorId(Integer id) {
        Optional<Tecnica> tecnica = tecnicaRepo.findById(id);
        removerTecnica(tecnica.get());
    }

    // Atualiza as estatísticas de uma técnica
    @Transactional
    public Tecnica atualizarEstatisticas(Integer idTecnica, Integer qtdGrad, Integer qtdMestrado,
            Integer qtdDoutorado) {
            verificarIdTecnica(idTecnica);
            Tecnica tecnica = tecnicaRepo.findById(idTecnica).get();
            Integer grad = tecnica.getQtdGrad() + qtdGrad;
            Integer mest = tecnica.getQtdMestrado() + qtdMestrado;
            Integer dout = tecnica.getQtdDoutorado() + qtdDoutorado;
            tecnica.setQtdGrad(grad);
            tecnica.setQtdMestrado(mest);
            tecnica.setQtdDoutorado(dout);
            return tecnicaRepo.save(tecnica);
    }

    @Transactional
    public Tecnica setOrientacoes(List<Integer> ids, Integer idTecnica){
        verificarIdTecnica(idTecnica);
        List<Orientacao> orientacoes = orientacaoRepo.obterOrientacoesTecn(ids, idTecnica).get();

        Tecnica tecnica = tecnicaRepo.findById(idTecnica).get();
        tecnica.getOrientacoes().clear();
        tecnica.getOrientacoes().addAll(orientacoes);
        return tecnicaRepo.save(tecnica);
    }

    public List<TecnicaDTO> obterTecnicasDTO(){
        List<TecnicaDTO> tecnicasDTO = tecnicaRepo.obterTecnicasDTO().get();
        
        return tecnicasDTO;
    }

    // Retorna todas as orientações de uma téncnica
    public List<Orientacao> obterOrientacoesTecnica(Integer idTecnica) {
        Optional<Tecnica> tecnica = tecnicaRepo.findById(idTecnica);

        if (tecnica.isPresent()) {
            return tecnica.get().getOrientacoes();
        }

        throw new ServicoRuntimeException("A técnica informada não existe");
    }

    // Retorna todas as técnicas de uma orientação em um período
    public Optional<List<Tecnica>> obterTecnicasOrientacaoPorPeriodo(Integer idOrientacao, Integer anoInicio,
            Integer anoFim) {
        verificarData(anoInicio, anoFim);
        Optional<Orientacao> orientacao = orientacaoRepo.findById(idOrientacao);

        // verificando se o docente existe
        if (orientacao.isPresent()) {

            if (anoInicio > anoFim) {
                Integer dataAuxiliar = anoInicio;

                anoInicio = anoFim;
                anoInicio = dataAuxiliar;
            }

            return tecnicaRepo.obterTecnicasOrientacaoPorPeriodo(idOrientacao, anoInicio, anoFim);
        }

        throw new ServicoRuntimeException("O docente informado não existe!");
    }

    // Retorna todas as técnicas de um docente
    public List<Tecnica> obterTecnicasDocente(Integer idDocente) {

        Optional<Docente> docente = docenteRepo.findById(idDocente);

        if (docente.isPresent()) {
            return docente.get().getTecnicas();
        }

        throw new ServicoRuntimeException("O docente informado não existe");
    }

    // Retorna todos os docentes de uma técnica
    public List<Docente> obterDocentesTecnica(Integer idTecnica) {

        Optional<Tecnica> tecnica = tecnicaRepo.findById(idTecnica);

        if (tecnica.isPresent()) {
            return tecnica.get().getDocentes();
        }

        throw new ServicoRuntimeException("A técnica informada não existe");
    }

    // Retorna todas as técnicas de um docente em um período
    public Optional<List<Tecnica>> obterTecnicasDocentePorPeriodo(Integer idDocente, Integer anoInicio,
            Integer anoFim) {
        verificarData(anoInicio, anoFim);
        Optional<Docente> docente = docenteRepo.findById(idDocente);

        // verificando se o docente existe
        if (docente.isPresent()) {

            if (anoInicio > anoFim) {
                Integer dataAuxiliar = anoInicio;

                anoInicio = anoFim;
                anoInicio = dataAuxiliar;
            }


            return tecnicaRepo.obterTecnicasDocentePorPeriodo(idDocente, anoInicio, anoFim);
        }

        throw new ServicoRuntimeException("O docente informado não existe!");
    }

    // Retorna todas as técnicas de um programa
    public Optional<List<Tecnica>> obterTecnicasPPG(Integer idPrograma) {

        Optional<Programa> programa = programaRepo.findById(idPrograma);

        if (programa.isPresent()) {
            return tecnicaRepo.obterTecnicasPPG(idPrograma);
        }

        throw new ServicoRuntimeException("O programa informado não existe!");
    }

    // Retorna todas as técnicas de um programa em um período
    public Optional<List<Tecnica>> obterTecnicasPPGPorPeriodo(Integer idPrograma, Integer anoInicio, Integer anoFim) {
        verificarData(anoInicio, anoFim);

        Optional<Programa> programa = programaRepo.findById(idPrograma);

        // verificando se o docente existe
        if (programa.isPresent()) {

            if (anoInicio > anoFim) {
                Integer dataAuxiliar = anoInicio;

                anoInicio = anoFim;
                anoInicio = dataAuxiliar;
            }

            return tecnicaRepo.obterTecnicasPPGPorPeriodo(idPrograma, anoInicio, anoFim);
        }
        throw new ServicoRuntimeException("O programa informado não existe!");
    }

    private void validarTecnica(Tecnica tecnica){
        if(tecnica==null)
            throw new ServicoRuntimeException("Produção deve ser Informada");
        if(tecnica.getTipo() == null || tecnica.getTipo().equals(""))
            throw new ServicoRuntimeException("O tipo da Produção deve ser informado");
        if(tecnica.getFinanciadora() == null || tecnica.getFinanciadora().equals(""))
            throw new ServicoRuntimeException("A Issn/Sigla da Produção deve ser informada");
        if(tecnica.getOutrasInformacoes() == null || tecnica.getOutrasInformacoes().equals(""))
            throw new ServicoRuntimeException("O nome local da Produção deve ser informado");
        if(tecnica.getTitulo() == null || tecnica.getTitulo().equals(""))
            throw new ServicoRuntimeException("O titulo da Produção deve ser informado");
        if(tecnica.getAno() == null)
            throw new ServicoRuntimeException("O ano da Produção deve ser Informado");
        if(tecnica.getAno() < 0)
            throw new ServicoRuntimeException("Informe um ano válido para a Produção");
        if(tecnica.getQtdGrad() == null)
            throw new ServicoRuntimeException("A quantidade de Graduandos da Produção deve ser informada");
        if(tecnica.getQtdGrad() < 0)
            throw new ServicoRuntimeException("Deve ser informado uma quantia real de Graduandos da Produção");
        if(tecnica.getQtdMestrado() == null)
            throw new ServicoRuntimeException("A quantidade de Mestrandos da Produção deve ser informada");
        if(tecnica.getQtdMestrado() < 0)
            throw new ServicoRuntimeException("Deve ser informado uma quantia real de Mestrandos da Produção");
        if(tecnica.getQtdDoutorado() == null)
            throw new ServicoRuntimeException("A quantidade de Doutorandos da Produção deve ser informada");
        if(tecnica.getQtdDoutorado() < 0)
            throw new ServicoRuntimeException("Deve ser informado uma quantia real de Doutorandos da Produção"); 
    }    
    
    private void validarEstatisticasTecnica(Tecnica tecnica) {
        if (tecnica.getQtdGrad() < 0 || tecnica.getQtdMestrado() < 0 || tecnica.getQtdDoutorado() < 0) {
            throw new ServicoRuntimeException("Estatísticas inválidas!");
        }
    }

    private void verificarIdTecnica(Integer idTecnica) {
        verificarNumero(idTecnica);
        if (!tecnicaRepo.existsById(idTecnica)) {
            throw new ServicoRuntimeException("Id da produção não está registrado");
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
