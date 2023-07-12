package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.dto.DocenteBaseDTO;
import br.ufma.sppg.dto.DocenteProdDTO;
import br.ufma.sppg.dto.IndicadoresDTO;
import br.ufma.sppg.dto.Indice;
import br.ufma.sppg.dto.InfoGraficoDTO;
import br.ufma.sppg.dto.OrientacaoDocenteDTO;
import br.ufma.sppg.dto.ProducaoDTO;
import br.ufma.sppg.dto.ProducaoDocenteDTO;
import br.ufma.sppg.dto.QualisDTO;
import br.ufma.sppg.dto.TecnicaDTO;
import br.ufma.sppg.dto.TecnicaDocenteDTO;
import br.ufma.sppg.model.*;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.service.exceptions.ServicoRuntimeException;
import jakarta.transaction.Transactional;

@Service
public class DocenteService {
    
    @Autowired
    DocenteRepository repository;

    public Indice obterIndice(Integer idDocente, Integer anoIni, Integer anoFin){ 
        verificarId(idDocente);
        verificarData(anoIni, anoFin);
        Double iRestrito = 0.0;
        Double iNRestrito = 0.0;
        Double iGeral = 0.0;
        List<Producao> producoes = new ArrayList<>();

        producoes = repository.obterProducoes(idDocente, anoIni, anoFin);

            for(Producao producao : producoes){
                    
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

        return new Indice(iRestrito, iNRestrito, iGeral);
    }

    public List<Producao> obterProducoes(Integer idDocente, Integer anoIni, Integer anoFin){
        verificarId(idDocente);
        verificarData(anoIni, anoFin);

        return repository.obterProducoes(idDocente, anoIni, anoFin);

    }

    public List<ProducaoDocenteDTO> obterProducoesDTO(Integer idDocente, Integer anoIni, Integer anoFin){
        List<ProducaoDocenteDTO> producoes = repository.obterProducoesDTO(idDocente, anoIni, anoFin).get();

        return producoes;
    }

    public List<Docente> obterDocentes(){
        return repository.findAll();
    }

    @Transactional
    public List<Orientacao> obterOrientacoes(Integer idDocente, Integer anoIni, Integer anoFin){
        verificarId(idDocente);
        verificarData(anoIni, anoFin);

        return repository.obterOrientacoes(idDocente, anoIni, anoFin);

    }

    @Transactional
    public List<OrientacaoDocenteDTO> obterOrientacoesDTO(Integer idDocente, Integer anoIni, Integer anoFin){
        List<OrientacaoDocenteDTO> orientacoes = repository.obterOrientacoesDTO(idDocente, anoIni, anoFin).get();

        return orientacoes;
    }

    public List<Tecnica> obterTecnicas(Integer idDocente, Integer anoIni, Integer anoFin){
        verificarId(idDocente);
        verificarData(anoIni, anoFin);

        return repository.obterTecnicas(idDocente, anoIni, anoFin);

    }

    public List<TecnicaDocenteDTO> obterTecnicasDTO(Integer idDocente, Integer anoIni, Integer anoFin){
        List<TecnicaDocenteDTO> tecnicas = repository.obterTecnicasDTO(idDocente, anoIni, anoFin).get();

        return tecnicas;
    }

    public List<ProducaoDTO> obterProducoesNoDate(Integer idDocente){
        verificarId(idDocente);
        List<ProducaoDTO> producoesDTO = repository.obterProducoesNoDate(idDocente).get();

        return producoesDTO;
    }

    public List<TecnicaDTO> obterTecnicasNoDate(Integer idDocente){
        verificarId(idDocente);
        List<TecnicaDTO> tecnicasDTO = repository.obterTecnicasNoDate(idDocente).get();

        return tecnicasDTO;
    }

    public IndicadoresDTO obterIndicadoresDTO(Integer idDocente, Integer anoIni, Integer anoFin){
        verificarId(idDocente);
        verificarData(anoIni, anoFin);
        Integer a1 = repository.obterQtdQualis(idDocente, anoIni, anoFin, "A1").get();
        Integer a2 = repository.obterQtdQualis(idDocente, anoIni, anoFin, "A2").get();
        Integer a3 = repository.obterQtdQualis(idDocente, anoIni, anoFin, "A3").get();
        Integer a4 = repository.obterQtdQualis(idDocente, anoIni, anoFin, "A4").get();
        Integer b1 = repository.obterQtdQualis(idDocente, anoIni, anoFin, "B1").get();
        Integer b2 = repository.obterQtdQualis(idDocente, anoIni, anoFin, "B2").get();
        Integer b3 = repository.obterQtdQualis(idDocente, anoIni, anoFin, "B3").get();
        Integer b4 = repository.obterQtdQualis(idDocente, anoIni, anoFin, "B4").get();

        Double iRestrito = Double.valueOf(a1) + Double.valueOf(a2)*Double.valueOf(0.85) + Double.valueOf(a3)*Double.valueOf(0.725) + Double.valueOf(a4)*Double.valueOf(0.625);
        Double iNRestrito = Double.valueOf(b1)*Double.valueOf(0.5) + Double.valueOf(b2)*Double.valueOf(0.25) + Double.valueOf(b3)*Double.valueOf(0.1) + Double.valueOf(b4)*Double.valueOf(0.05);
        Double iGeral = Double.valueOf(iRestrito) + Double.valueOf(iNRestrito);
        Integer totalProd = a1 + a2 + a3 + a4 + b1 + b2 + b3 + b4;
        
        return new IndicadoresDTO(iGeral + "", iRestrito + "", iNRestrito + "", totalProd + "");
    }

    public List<DocenteBaseDTO> obterDocentesBase(){
        List<DocenteBaseDTO> docentesBase = repository.obterDocentesBase().get();

        return docentesBase;
    }

    public List<DocenteProdDTO> obterDocentesProdDTO(){
        List<DocenteProdDTO> docentes = repository.obterDocentesProdDTO().get();

        return docentes;
    }

    public List<DocenteProdDTO> obterDocentesProdDTO(Integer idProd){
        List<DocenteProdDTO> docentes = repository.obterDocentesProdDTO(idProd).get();

        return docentes;
    }

    public List<DocenteProdDTO> obterDocentesTecnDTO(Integer idTecn){
        List<DocenteProdDTO> docentes = repository.obterDocentesTecnDTO(idTecn).get();

        return docentes;
    }

    public InfoGraficoDTO obterGraficoPeriodicos(Integer idPrograma, Integer anoIni, Integer anoFin){
        List<Integer> anos = new ArrayList<>();
        List<Integer> a1s = new ArrayList<>();
        List<Integer> a2s = new ArrayList<>();
        List<Integer> a3s = new ArrayList<>();
        List<Integer> a4s = new ArrayList<>();
        Integer num = 0;
        Integer index = 0;
        Integer i = anoFin;
        List<Producao> prodList = repository.obterProducoesP(idPrograma, anoIni, anoFin).get();
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

    public InfoGraficoDTO obterGraficoCongresso(Integer idPrograma, Integer anoIni, Integer anoFin){
        List<Integer> anos = new ArrayList<>();
        List<Integer> a1s = new ArrayList<>();
        List<Integer> a2s = new ArrayList<>();
        List<Integer> a3s = new ArrayList<>();
        List<Integer> a4s = new ArrayList<>();
        Integer num = 0;
        Integer index = 0;
        Integer i = anoFin;
        List<Producao> prodList = repository.obterProducoesC(idPrograma, anoIni, anoFin).get();
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

    public DocenteBaseDTO obterDocenteBase(Integer idDocente){
        verificarId(idDocente);
        DocenteBaseDTO docenteBase = repository.obterDocenteBase(idDocente).get();

        return docenteBase;
    }

    @Transactional
    public Docente salvarDocente(Docente doc){
        verificarDocente(doc);

        return repository.save(doc);
    }

    public Optional<Docente> obterDocente(Integer idDocente){
        verificarId(idDocente);

        return repository.findById(idDocente);
    }

    public List<Docente> obterDocentesNome(String nome){
        verificarPalavra(nome, "Nome inválido");

        return repository.findAllByNome(nome);
    }

    private void verificarPalavra(String nome, String mensagem){
        if(nome == null){
            throw new ServicoRuntimeException(mensagem);
        }
        if(nome.trim().equals("")){
            throw new ServicoRuntimeException(mensagem);
        }
    }

    private void verificarId(Integer idDocente){
        verificarNumero(idDocente, "Id Inválido");
        if(!repository.existsById(idDocente)){
            throw new ServicoRuntimeException("Id do Docente não está registrado");
        }
    }

    private void verificarData(Integer data1, Integer data2){
        verificarNumero(data1, "Data Inválida");
        verificarNumero(data2, "Data Inválida");
        if(data1 > data2){
            throw new ServicoRuntimeException("Data inical maior que a data final");
        }
    }

    private void verificarNumero(Integer numero, String mensagem){
        if(numero == null){
            throw new ServicoRuntimeException(mensagem);
        }

    }

    private void verificarDocente(Docente docente){
        verificarPalavra(docente.getNome(), "Nome inválido");
        verificarPalavra(docente.getLattes(), "Lattes inválido");
        verificarNumero(docente.getId(), "Id inválido");
        if(repository.existsById(docente.getId())){
            throw new ServicoRuntimeException("Id já registrado");
        }
        /*
        if(docente.getOrientacoes() == null){
            throw new ServicoRuntimeException("Lista de orientações inexistente");
        }
        if(docente.getTecnicas() == null){
            throw new ServicoRuntimeException("Lista de tecnicas inexistente");
        }
        if(docente.getProducoes() == null){
            throw new ServicoRuntimeException("Lista de produções inexistente");
        }
        if(docente.getProgramas() == null){
            throw new ServicoRuntimeException("Lista de programas inexistente");
        }
        */
    }

}