package br.ufma.sppg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrientacaoNewProdDTO {
    Integer id;
    String titulo;
    String orientador;
    String tipo;
    String instituicao;
    String curso;
    Integer ano;
    Boolean enviar;
}
