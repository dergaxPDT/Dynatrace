package com.pdt.dynatrace.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@ApiModel("Rate Tabel")
public class RatesTable {
    Table table;
    String currency;
    String code;
    List<Rate> rates;
}
