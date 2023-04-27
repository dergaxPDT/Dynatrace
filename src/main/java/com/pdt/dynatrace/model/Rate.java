package com.pdt.dynatrace.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Rate {
    String no;

    String effectiveDate;
    double mid;
}
