package br.ufma.sppg.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrientacaoDocenteDTO {
    String discente;
    String titulo;
    String tipo;
    Integer ano;
}
