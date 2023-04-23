package com.pdt.dynatrace;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "nbp API")
public class NbpController {

    private final NbpService nbpService;

    @GetMapping
    @Operation(description = "Get average exchange rate from date - choose rate code(ex. 'eur')")
    public double getAverageExchangeRate(@RequestParam String code, @RequestParam String date){
        return nbpService.getRatesTable(code, date).rates.get(0).mid;
    }
}
