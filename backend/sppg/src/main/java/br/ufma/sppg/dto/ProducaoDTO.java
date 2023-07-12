package br.ufma.sppg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProducaoDTO {
    Integer id;
    Integer ano;
    String docente;
    String titulo;
    String local;
    String orientacao;
    String gmd;
}
