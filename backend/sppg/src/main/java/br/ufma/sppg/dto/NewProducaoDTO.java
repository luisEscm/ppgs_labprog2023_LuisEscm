package br.ufma.sppg.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewProducaoDTO {
    String titulo;
    String nomeLocal;
    String tipo;
    String Qualis;
    String issnSigla;
    Integer ano;
    Float percentilouh5;
    Integer qtdGrad;
    Integer qtdMestrado;
    Integer qtdDoutorado;
    List<Integer> idDocentes;
    List<Integer> idOrientacoes;
}
