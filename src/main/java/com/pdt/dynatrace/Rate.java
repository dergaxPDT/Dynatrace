package com.pdt.dynatrace;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Rate {
    String no;

    String effectiveDate;//TODO na date pewnie zmienic
    double mid;
}
