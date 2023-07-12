package br.ufma.sppg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TecnicaDTO {
    Integer id;
    Integer ano;
    String docente;
    String titulo;
    String financiadora;
    String orientacao;
    String gmd;
}
