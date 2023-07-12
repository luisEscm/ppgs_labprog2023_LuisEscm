package br.ufma.sppg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProducaoDocenteDTO {
    String titulo;
    String local;
    String tipo;
    String qualis;
    Integer ano;
}
