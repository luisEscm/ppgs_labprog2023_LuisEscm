package br.ufma.sppg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TecnicaDocenteDTO {
    String titulo;
    String tipo;
    Integer ano;
}
