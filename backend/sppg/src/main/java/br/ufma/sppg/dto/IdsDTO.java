package br.ufma.sppg.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IdsDTO {
    List<Integer> ids;
    Integer idProd;
}
