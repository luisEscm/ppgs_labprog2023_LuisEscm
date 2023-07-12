package br.ufma.sppg.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrientacaoProdDTO {
    Integer id;
    String nome;
    String titulo;
    Boolean enviar;
}
