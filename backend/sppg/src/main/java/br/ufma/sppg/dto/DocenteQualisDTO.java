package br.ufma.sppg.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocenteQualisDTO {
    Integer id;
    String nome;
    Integer a1;
    Integer a2;
    Integer a3;
    Integer a4;
    Integer b1;
    Integer b2;
    Integer b3;
    Integer b4;
}
