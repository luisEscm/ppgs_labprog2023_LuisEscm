package br.ufma.sppg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstatisticasDTO {
    Integer qtdGrad;
    Integer qtdMestrado;
    Integer qtdDoutorado;
    Integer idProd;
}
