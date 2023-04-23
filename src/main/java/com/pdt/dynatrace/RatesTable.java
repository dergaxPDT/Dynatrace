package com.pdt.dynatrace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@Getter
public class RatesTable {
    Table table;
    String currency;
    String code;
    List<Rate> rates;
}
