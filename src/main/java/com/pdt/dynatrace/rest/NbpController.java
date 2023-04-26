package com.pdt.dynatrace.rest;

import com.pdt.dynatrace.data.MinMaxRate;
import com.pdt.dynatrace.exception.TooMuchQuantityException;
import com.pdt.dynatrace.service.NbpService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nbp/rate")
public class NbpController {

    private final NbpService nbpService;

    @GetMapping("/average")
    @ApiOperation(value = "1 - Find mid rate by code and date", notes = "code is shortcut for exchange rate (ex. eur, gbp). Date format RRRR-MM-DD")
    public double getAverageExchangeRate(@RequestParam String code, @RequestParam String date){
        return nbpService.getRatesTable(code, date).getRates().get(0).getMid();
    }

    @GetMapping("/maxAndMin")
    @ApiOperation(value = "2 - the max and min average value exchange rate", notes = "code is shortcut for exchange rate (ex. eur, gbp). topCount - the number of last quotations ")
    public MinMaxRate getMaxAndMinRate(@RequestParam String code, @RequestParam int topCount){
        if (topCount > 255) throw new TooMuchQuantityException(topCount);
        return nbpService.getMinAndMaxRate(code, topCount);
    }

    @GetMapping("/difference ")
    @ApiOperation(value = "3 - the difference between max and min average value exchange rate", notes = "code is shortcut for exchange rate (ex. eur, gbp). topCount - the number of last quotations ")
    public double getDifferenceRate(@RequestParam String code, @RequestParam int topCount){
        return nbpService.getDifferenceRate(code, topCount);
    }
}
