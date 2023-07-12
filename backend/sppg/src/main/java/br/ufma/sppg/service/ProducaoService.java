package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.dto.AtualizaProducaoDTO;
import br.ufma.sppg.dto.NewProducaoDTO;
import br.ufma.sppg.dto.ProducaoDTO;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.model.Tecnica;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;
import jakarta.transaction.Transactional;


/*
 * A QUANTIDADE DE A1,A2,... POR ANO
 * SORT()
 */
@Service
public class ProducaoService {
    
    @Autowired
    ProducaoRepository prodRepo;

    @Autowired
    DocenteRepository docRepo;

    @Autowired
    ProgramaRepository progRepo;

    @Autowired
    OrientacaoRepository oriRepo;

    //TODO: checar tempo de processamento
    public List<Producao>obterProducoesPPG(Integer idPrograma, Integer data1, Integer data2){


        //É Presumido que o usuário coloque em data1 o valor mais baixo e em data2 o valor mais alto como por exemplo
        //data1=2016, data2=2023. Esta função verifica se a ordem esperada foi trocada e ajusta para que não ocorra erros
        if (data1 >= data2){
            Integer data = data2;
            data2 = data1;
            data1 = data;
        }

        Optional<Programa> programa = progRepo.findById(idPrograma);
        if(programa.isPresent()){

            //Verificando se o Programa possui Docentes cadastrados
            if(progRepo.getReferenceById(idPrograma).getDocentes() == null 
            || progRepo.getReferenceById(idPrograma).getDocentes().isEmpty())
                throw new ServicoRuntimeException("O programa não possui Docentes cadastrados");

            ArrayList<Producao> producoes = new ArrayList<>();

            for(int i = 0; i < progRepo.getReferenceById(idPrograma).getDocentes().size(); i++){
                //Verificando se o Docente do laço possui Array de Produções
                if(progRepo.getReferenceById(idPrograma).getDocentes().get(i).getProducoes() != null
                && !progRepo.getReferenceById(idPrograma).getDocentes().get(i).getProducoes().isEmpty())
                {
                    for(int j = 0; j< progRepo.getReferenceById(idPrograma).getDocentes().get(i).getProducoes().size(); j++){
                        if(progRepo.getReferenceById(idPrograma).getDocentes().get(i).getProducoes().get(j).getAno() >= data1
                        && progRepo.getReferenceById(idPrograma).getDocentes().get(i).getProducoes().get(j).getAno() <= data2){
                            producoes.add(progRepo.getReferenceById(idPrograma).getDocentes().get(i).getProducoes().get(j));
                        }
                    }
                }
            }
            if(producoes.isEmpty())
                throw new ServicoRuntimeException("O Programa não possui Docentes com Produções no periodo especificado");

            return producoes;
        }
        throw new ServicoRuntimeException("Programa Inexistente");

    };

    //TODO: atualizar função
    public List<Producao>obterProducoesDocente(Integer idDocente, Integer data1, Integer data2){

        verificarData(data1, data2);

        Optional<Docente> docente = docRepo.findById(idDocente);
        if(docente.isPresent()){

            if(docRepo.getReferenceById(idDocente).getProducoes() == null 
            || docRepo.getReferenceById(idDocente).getProducoes().isEmpty())
                throw new ServicoRuntimeException("O Docente não possui nenhuma Produção Registrada");

            ArrayList<Producao> producoes = new ArrayList<>();

            for(int i = 0; i < docRepo.getReferenceById(idDocente).getProducoes().size(); i++){
                if(docRepo.getReferenceById(idDocente).getProducoes().get(i).getAno() >= data1
                && docRepo.getReferenceById(idDocente).getProducoes().get(i).getAno() <= data2){
                    producoes.add(docRepo.getReferenceById(idDocente).getProducoes().get(i));
                }
            }
            if(producoes.isEmpty())
                throw new ServicoRuntimeException("O Docente não possui nenhuma Produção no periodo especificado");

            return producoes;
        }
        throw new ServicoRuntimeException("Docente Inexistente");
    }

    public NewProducaoDTO obterProducao(Integer idProd){
        verificarIdProducao(idProd);
        List<Integer> docentes = prodRepo.obterDocentesId(idProd).get();
        List<Integer> orientacoes = prodRepo.obterOrientacoesId(idProd).get();

        Producao prod = prodRepo.findById(idProd).get();

        return new NewProducaoDTO(prod.getTitulo(), prod.getNomeLocal(), prod.getTipo(), prod.getQualis(), prod.getIssnOuSigla(), prod.getAno(), prod.getPercentileOuH5(), prod.getQtdGrad(), prod.getQtdMestrado(), prod.getQtdDoutorado(), docentes, orientacoes);

    }

    public List<ProducaoDTO> obterProducoesDTO(){
        List<ProducaoDTO> producoesDTO = prodRepo.obterProducoesDTO().get();
        // for(Producao prod : producoes){
        //     String gmd = prod.getQtdGrad() + "G|" + prod.getQtdMestrado() + "M|" + prod.getQtdDoutorado() + "D";
        //     String docente = "Indefinido";
            
        //     if(!(prod.getDocentes() == null)){
        //         if(!prod.getDocentes().isEmpty()){
        //             docente = prod.getDocentes().get(0).getNome();
        //         }
        //     }

        //     ProducaoDTO dto;
        //     if(prod.getOrientacoes() == null){
        //         dto = new ProducaoDTO(prod.getId(), prod.getAno(), docente, prod.getTitulo(), prod.getNomeLocal(), "Não", gmd);
        //         producoesDTO.add(dto);
        //         continue;
        //     }
        //     if(prod.getOrientacoes().isEmpty()){
        //         dto = new ProducaoDTO(prod.getId(), prod.getAno(), docente, prod.getTitulo(), prod.getNomeLocal(), "Não", gmd);
        //     }else{
        //         dto = new ProducaoDTO(prod.getId(), prod.getAno(), docente, prod.getTitulo(), prod.getNomeLocal(), "Sim", gmd);
        //     }
        //     producoesDTO.add(dto);
        // }

        return producoesDTO;
    }

    private void verificarProducao(Producao producao){
        if(producao==null)
            throw new ServicoRuntimeException("Produção deve ser Informada");
        if(producao.getTipo() == null || producao.getTipo().equals(""))
            throw new ServicoRuntimeException("O tipo da Produção deve ser informado");
        if(producao.getIssnOuSigla() == null || producao.getIssnOuSigla().equals(""))
            throw new ServicoRuntimeException("A Issn/Sigla da Produção deve ser informada");
        if(producao.getNomeLocal() == null || producao.getNomeLocal().equals(""))
            throw new ServicoRuntimeException("O nome local da Produção deve ser informado");
        if(producao.getTitulo() == null || producao.getTitulo().equals(""))
            throw new ServicoRuntimeException("O titulo da Produção deve ser informado");
        if(producao.getAno() == null)
            throw new ServicoRuntimeException("O ano da Produção deve ser Informado");
        if(producao.getAno() < 0)
            throw new ServicoRuntimeException("Informe um ano válido para a Produção");
        if(producao.getQualis() == null || producao.getQualis().equals(""))
            throw new ServicoRuntimeException("A qualis da Produção deve ser informada");
        Float percentileOuH5 = producao.getPercentileOuH5();
        if(percentileOuH5 == null || percentileOuH5 < 0)
            throw new ServicoRuntimeException("O percentile/h5 da Produção deve ser informado");
        if(producao.getQtdGrad() == null)
            throw new ServicoRuntimeException("A quantidade de Graduandos da Produção deve ser informada");
        if(producao.getQtdGrad() < 0)
            throw new ServicoRuntimeException("Deve ser informado uma quantia real de Graduandos da Produção");
        if(producao.getQtdMestrado() == null)
            throw new ServicoRuntimeException("A quantidade de Mestrandos da Produção deve ser informada");
        if(producao.getQtdMestrado() < 0)
            throw new ServicoRuntimeException("Deve ser informado uma quantia real de Mestrandos da Produção");
        if(producao.getQtdDoutorado() == null)
            throw new ServicoRuntimeException("A quantidade de Doutorandos da Produção deve ser informada");
        if(producao.getQtdDoutorado() < 0)
            throw new ServicoRuntimeException("Deve ser informado uma quantia real de Doutorandos da Produção");
    }

    @Transactional
    public Producao informarEstatisticasProducao(Producao producao){
        verificarProducao(producao);
        return prodRepo.save(producao);
    }

    @Transactional
    public Producao setOrientacoes(List<Integer> orientacoesIds, Integer idProducao){
        verificarIdProducao(idProducao);
        Producao producao = prodRepo.findById(idProducao).get();
        List<Orientacao> orientacoes = oriRepo.obterOrientacoesProd(orientacoesIds, idProducao).get();
        producao.getOrientacoes().clear();
        producao.getOrientacoes().addAll(orientacoes);
        return prodRepo.save(producao);
    }

    public List<Orientacao> obterOrientacaoProducao(Integer idProducao){
        Optional<Producao> producao = prodRepo.findById(idProducao);
        if(producao.isPresent()){
            if(prodRepo.getReferenceById(idProducao).getOrientacoes() != null){
                return prodRepo.getReferenceById(idProducao).getOrientacoes();
            }
            return new ArrayList<>();
        }
        throw new ServicoRuntimeException("A Producao não existe");
    }

    @Transactional
    public Producao addEstatisticasProd(Integer qtdGrad, Integer qtdMestrado, Integer qtdDoutorado, Integer idProducao){
        verificarIdProducao(idProducao);
        Producao prod = prodRepo.findById(idProducao).get();
        Integer grad = prod.getQtdGrad() + qtdGrad;
        Integer mest = prod.getQtdMestrado() + qtdMestrado;
        Integer dout = prod.getQtdDoutorado() + qtdDoutorado;
        prod.setQtdGrad(grad);
        prod.setQtdMestrado(mest);
        prod.setQtdDoutorado(dout);
        return prodRepo.save(prod);
    }

    @Transactional
    public Producao addProd(NewProducaoDTO newProdDTO){
        Producao prod = Producao.builder().titulo(newProdDTO.getTitulo()).nomeLocal(newProdDTO.getNomeLocal())
                                        .tipo(newProdDTO.getTipo()).qualis(newProdDTO.getQualis()).issnOuSigla(newProdDTO.getIssnSigla())
                                        .ano(newProdDTO.getAno()).percentileOuH5(newProdDTO.getPercentilouh5())
                                        .qtdGrad(newProdDTO.getQtdGrad()).qtdMestrado(newProdDTO.getQtdMestrado())
                                        .qtdDoutorado(newProdDTO.getQtdDoutorado()).build();
        verificarProducao(prod);
        Optional<List<Docente>> docentes = docRepo.obterDocentes(newProdDTO.getIdDocentes());
        Optional<List<Orientacao>> orientacoes = oriRepo.obterOrientacoes(newProdDTO.getIdOrientacoes());
        if(docentes.isPresent()){
            prod.setDocentes(docentes.get());
        }
        if(orientacoes.isPresent()){
            prod.setOrientacoes(orientacoes.get());
        }

        return prodRepo.save(prod);
    }

    @Transactional
    public Producao atualizarProd(AtualizaProducaoDTO newProdDTO){
        Producao prod = Producao.builder().id(newProdDTO.getId()).titulo(newProdDTO.getTitulo()).nomeLocal(newProdDTO.getNomeLocal())
                                        .tipo(newProdDTO.getTipo()).qualis(newProdDTO.getQualis()).issnOuSigla(newProdDTO.getIssnSigla())
                                        .ano(newProdDTO.getAno()).percentileOuH5(newProdDTO.getPercentilouh5())
                                        .qtdGrad(newProdDTO.getQtdGrad()).qtdMestrado(newProdDTO.getQtdMestrado())
                                        .qtdDoutorado(newProdDTO.getQtdDoutorado()).build();
        verificarProducao(prod);
        Optional<List<Docente>> docentes = docRepo.obterDocentes(newProdDTO.getIdDocentes());
        Optional<List<Orientacao>> orientacoes = oriRepo.obterOrientacoes(newProdDTO.getIdOrientacoes());
        if(docentes.isPresent()){
            prod.setDocentes(docentes.get());
        }
        if(orientacoes.isPresent()){
            prod.setOrientacoes(orientacoes.get());
        }

        return prodRepo.save(prod);
    }
    
    private void verificarIdProducao(Integer idProducao) {
        verificarNumero(idProducao);
        if (!prodRepo.existsById(idProducao)) {
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


    /*
    public boolean excluirProducao(Integer idProducao){
        Optional<Producao> producao = prodRepo.findById(idProducao);
        if(producao.isPresent()){
            removerDocentesProducao(idProducao);
            removerOrientacoesProducao(idProducao);
            prodRepo.delete(prodRepo.getReferenceById(idProducao));
            return true;
        }
        if(prodRepo.existsById(idProducao))
            throw new RegrasRunTime("Erro ao Excluir Produção");
        throw new RegrasRunTime("Produção Inexistente");
    }

    public boolean retirarProducaoDocente(Integer idProducao, Integer idDocente){
        Optional<Producao> opProducao = prodRepo.findById(idProducao);
        if(opProducao.isPresent()){
            Producao producao = prodRepo.getReferenceById(idProducao);
            if(docRepo.existsById(idDocente)){
                Docente docente = docRepo.getReferenceById(idDocente);

                if(docente.getProducoes().remove(producao))
                    producao.getDocentes().remove(docente);
                else
                    throw new RegrasRunTime("Producao e Docente não possuem Relação");
                
                docRepo.save(docente);
                prodRepo.save(producao);
                return true;
            }else{
                throw new RegrasRunTime("Docente Inexistente");
            }
        }
        throw new RegrasRunTime("Producao Inexistente");
    }


    public boolean retirarProducaoOrientacao(Integer idProducao, Integer idOrientacao){
        Optional<Producao> opProducao = prodRepo.findById(idProducao);
        if(opProducao.isPresent()){
            Producao producao = prodRepo.getReferenceById(idProducao);
            if(oriRepo.existsById(idOrientacao)){
                Orientacao orientacao = oriRepo.getReferenceById(idOrientacao);

                if(orientacao.getProducoes().remove(producao))
                    producao.getOrientacoes().remove(orientacao);
                else
                    throw new RegrasRunTime("Producao e Orientacao não possuem Relação");
                
                oriRepo.save(orientacao);
                prodRepo.save(producao);
                return true;
            }else{
                throw new RegrasRunTime("Orientacao Inexistente");
            }
        }
        throw new RegrasRunTime("Producao Inexistente");
    }

    public boolean removerOrientacoesProducao(Integer idProducao){
        Optional<Producao> producao = prodRepo.findById(idProducao);
        if(producao.isPresent()){
            if(prodRepo.getReferenceById(idProducao).getOrientacoes() != null
            && !prodRepo.getReferenceById(idProducao).getOrientacoes().isEmpty()){
                for(int i = 0; i < prodRepo.getReferenceById(idProducao).getOrientacoes().size(); i++){
                    retirarProducaoOrientacao(idProducao, prodRepo.getReferenceById(idProducao).getOrientacoes().get(i).getId());
                }
                return true;
            }
            throw new RegrasRunTime("Não Existem Orientações na Produção");

        }
        throw new RegrasRunTime("Producao Inexistente");
    }

    public boolean removerDocentesProducao(Integer idProducao){
        Optional<Producao> producao = prodRepo.findById(idProducao);
        if(producao.isPresent()){
            if(prodRepo.getReferenceById(idProducao).getDocentes() != null
            && !prodRepo.getReferenceById(idProducao).getDocentes().isEmpty()){
                for(int i = 0; i < prodRepo.getReferenceById(idProducao).getDocentes().size(); i++){
                    retirarProducaoDocente(idProducao, prodRepo.getReferenceById(idProducao).getDocentes().get(i).getId());
                }
                return true;
            }
            throw new RegrasRunTime("Não Existem Docentes na Produção");

        }
        throw new RegrasRunTime("Producao Inexistente");
    }
    */
}
