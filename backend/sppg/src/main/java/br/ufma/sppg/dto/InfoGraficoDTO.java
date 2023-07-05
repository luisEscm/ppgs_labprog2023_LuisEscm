package br.ufma.sppg.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InfoGraficoDTO {
    List<Integer> anos;
    List<Integer> qtvA1;
    List<Integer> qtvA2;
    List<Integer> qtvA3;
    List<Integer> qtvA4;

}