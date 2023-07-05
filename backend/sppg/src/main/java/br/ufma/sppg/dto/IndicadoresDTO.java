package br.ufma.sppg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IndicadoresDTO {
    String iGeral;
    String iRestrito;
    String iNaoRestrito;
    String totalProd;

}
