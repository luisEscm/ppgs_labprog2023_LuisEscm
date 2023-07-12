package br.ufma.sppg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TecnicaProdDTO {
    Integer id;
    String titulo;
    String tipo;
    String financiadora;
    Integer ano;
    Boolean enviar;
}
