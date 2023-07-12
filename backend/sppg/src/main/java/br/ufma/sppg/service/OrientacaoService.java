package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.dto.OrientacaoNewProdDTO;
import br.ufma.sppg.dto.OrientacaoProdDTO;
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
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;
import jakarta.transaction.Transactional;

@Service
public class OrientacaoService {

    @Autowired
    private OrientacaoRepository orientacaoRepository;

    @Autowired
    private ProgramaRepository programaRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private ProducaoRepository producaoRepository;

    @Autowired
    private TecnicaRepository tecnicaRepository;

    public Optional<List<Orientacao>> obterOrientacoesComTecnicaPorPeriodo(Integer idDocente, Integer anoInicio,
            Integer anoFim) {
        verificarData(anoInicio, anoFim);
        verificarIdDocente(idDocente);
        return orientacaoRepository.obterOrientacoesComTecnicaPorPeriodo(idDocente, anoInicio, anoFim);
    }

    public Optional<List<Orientacao>> obterOrientacoesComProducaoPorPeriodo(Integer idDocente, Integer anoInicio,
            Integer anoFim) {
        verificarData(anoInicio, anoFim);
        verificarIdDocente(idDocente);
        return orientacaoRepository.obterOrientacoesComProducaoPorPeriodo(idDocente, anoInicio, anoFim);
    }

    public List<Orientacao> obterOrientacaoPPG(Integer id, Integer anoIni, Integer anoFim) {
        verificarData(anoIni, anoFim);
        validarOrientacoesPpg(id, anoIni, anoFim);
        List<Orientacao> orientacoes = orientacaoRepository.findByPPG(id, anoIni, anoFim).get();

        return orientacoes;
    }

    public List<Orientacao> obterOrientacaoDocente(Integer id, Integer anoIni, Integer anoFim) {
        verificarData(anoIni, anoFim);
        validarOrientacoesDoc(id, anoIni, anoFim);
        List<Orientacao> orientacoes = orientacaoRepository.findByDocente(id, anoIni, anoFim).get();

        return orientacoes;
    }

    @Transactional
    public List<Orientacao> obterOrientacoesProd(List<Integer> ids, Integer idProd){
        for(Integer id : ids){
            verificarId(id);
        }
        verificarNumero(idProd);
        List<Orientacao> orientacoes = orientacaoRepository.obterOrientacoesProd(ids, idProd).get();

        return orientacoes;
    }

    @Transactional
    public Orientacao associarOrientacaoProducao(Integer idOri, Integer idProd) {
        validarOriProd(idOri, idProd);

        Orientacao orientacao = orientacaoRepository.findById(idOri).get();
        Producao prod = producaoRepository.findById(idProd).get();

        if (orientacao.getProducoes() == null) {
            List<Producao> producoes = new ArrayList<>();
            orientacao.setProducoes(producoes);
        }

        if (!orientacao.getProducoes().contains(prod)) {
            orientacao.getProducoes().add(prod); 
        } else {
            throw new ServicoRuntimeException("Produção já associada.");
        }
        
        return orientacaoRepository.save(orientacao);
    }

    @Transactional
    public Orientacao associarOrientacaoTecnica(Integer idOri, Integer idTec) {
        validarOriTec(idOri, idTec);

        Orientacao orientacao = orientacaoRepository.findById(idOri).get();
        Tecnica tec = tecnicaRepository.findById(idTec).get();

        if (orientacao.getTecnicas() == null) {
            List<Tecnica> tecnicas = new ArrayList<>();
            orientacao.setTecnicas(tecnicas);
        }
            
        if (!orientacao.getTecnicas().contains(tec)) {
            orientacao.getTecnicas().add(tec);
        } else {
            throw new ServicoRuntimeException("Técnica já associada.");
        }

        return orientacaoRepository.save(orientacao);
    }

    public List<OrientacaoNewProdDTO> obterOrientacoesNewProdDTO(){
        List<OrientacaoNewProdDTO> orientacoes = orientacaoRepository.obterOrientacoesNewProdDTO().get();

        return orientacoes;
    }

    public List<OrientacaoNewProdDTO> obterOrientacoesNewProdDTO(Integer idProd){
        List<OrientacaoNewProdDTO> orientacoes = orientacaoRepository.obterOrientacoesNewProdDTO(idProd).get();

        return orientacoes;
    }

    public List<OrientacaoNewProdDTO> obterOrientacoesNewTecnDTO(Integer idTecn){
        List<OrientacaoNewProdDTO> orientacoes = orientacaoRepository.obterOrientacoesNewTecnDTO(idTecn).get();

        return orientacoes;
    }

    public List<OrientacaoProdDTO> obterOrientacoesProdDTO(){
        return orientacaoRepository.obterOrientacoesProdDTO().get();
    }

    private void validarOrientacoesPpg(Integer idPrograma, Integer anoIni, Integer anoFim) {
        Optional<Programa> programa = programaRepository.findById(idPrograma);

        Optional<List<Orientacao>> orientacoes = orientacaoRepository.findByPPG(idPrograma, anoIni, anoFim);

        if (programa.isEmpty())
            throw new RuntimeException("Não foram encontrados programas com este Id.");
        if (orientacoes.isEmpty())
            throw new RuntimeException("Não foram encontradas orientações para este docente.");
    }

    private void validarOrientacoesDoc(Integer idDocente, Integer anoIni, Integer anoFim) {

        Optional<Docente> docente = docenteRepository.findById(idDocente);

        Optional<List<Orientacao>> orientacoes = orientacaoRepository.findByDocente(idDocente, anoIni, anoFim);

        if (docente.isEmpty())
            throw new RuntimeException("Não foram encontrados  programas com este Id.");
        if (orientacoes.isEmpty())
            throw new RuntimeException("Não foram encontradas orientações para este docente.");
    }

    private void validarOriProd(Integer idOri, Integer idProd) {

        Optional<Producao> prod = producaoRepository.findById(idProd);

        Optional<Orientacao> orientacao = orientacaoRepository.findById(idOri);

        if (prod.isEmpty())
            throw new RuntimeException("Não foram existe produção.");
        if (orientacao.isEmpty())
            throw new RuntimeException("Não foram existe orientação.");
    }

    private void validarOriTec(Integer idOri, Integer idTec) {

        Optional<Tecnica> tec = tecnicaRepository.findById(idTec);

        Optional<Orientacao> orientacao = orientacaoRepository.findById(idOri);

        if (tec.isEmpty())
            throw new RuntimeException("Não foram existe tecnica.");
        if (orientacao.isEmpty())
            throw new RuntimeException("Não foram existe orientação.");
    }

    private void verificarId(Integer idOrientacao) {
        verificarNumero(idOrientacao);
        if (!orientacaoRepository.existsById(idOrientacao)) {
            throw new ServicoRuntimeException("Id da orientacao não está registrado");
        }
    }

    private void verificarIdDocente(Integer idDocente) {
        verificarNumero(idDocente);
        if (!docenteRepository.existsById(idDocente)) {
            throw new ServicoRuntimeException("Id do docente não está registrado");
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