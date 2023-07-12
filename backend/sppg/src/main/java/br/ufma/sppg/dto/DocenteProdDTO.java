package br.ufma.sppg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocenteProdDTO {
    Integer id;
    String lattes;
    String nome;
    Boolean enviar;
}