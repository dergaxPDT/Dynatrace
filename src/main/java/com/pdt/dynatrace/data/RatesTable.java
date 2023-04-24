package com.pdt.dynatrace.data;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor
@Getter
@ApiModel("Personal data of Student")
public class RatesTable {
    Table table;
    String currency;
    String code;
    List<Rate> rates;
}
