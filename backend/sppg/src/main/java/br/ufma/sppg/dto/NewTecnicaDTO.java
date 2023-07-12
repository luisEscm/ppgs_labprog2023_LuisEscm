package br.ufma.sppg.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewTecnicaDTO {
    String tipo;
    String titulo;
    Integer ano;
    String financiadora;
    String outrasInformacoes;
    Integer qtdGrad;
    Integer qtdMestrado;
    Integer qtdDoutorado;
    List<Integer> idsDocentes;
    List<Integer> idsOrientacoes;
}
