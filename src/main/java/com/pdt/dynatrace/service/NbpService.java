package com.pdt.dynatrace.service;

import com.pdt.dynatrace.exception.RestTemplateResponseErrorHandler;
import com.pdt.dynatrace.exception.TooMuchQuantityException;
import com.pdt.dynatrace.model.MinMaxRate;
import com.pdt.dynatrace.model.Rate;
import com.pdt.dynatrace.model.RatesTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class NbpService {

    private final RestTemplate      restTemplate = new RestTemplateBuilder()
                .errorHandler(new RestTemplateResponseErrorHandler())
            .build();;

    @Value("${api.url}")
    private String url;

    public RatesTable getRatesTable(String code, String date) {
        String resourceUrl = url + code + "/" + date + "/";
        return restTemplate
                .getForObject(resourceUrl, RatesTable.class);
    }

    public MinMaxRate getMinAndMaxRate(String code, int topCount) {
        if (topCount > 255) throw new TooMuchQuantityException(topCount);
        String resourceUrl = url + code + "/last/" + topCount + "/?format=json";
        RatesTable ratesTable = restTemplate.getForObject(resourceUrl, RatesTable.class);
        assert ratesTable != null;
        double minRate = Collections
                .min(ratesTable.getRates()
                        .stream().map(Rate::getMid)
                        .toList());
        double maxRate = Collections
                .max(ratesTable.getRates()
                        .stream().map(Rate::getMid)
                        .toList());
        return new MinMaxRate(minRate, maxRate);
    }

    public double getDifferenceRate(String code, int topCount) {
        MinMaxRate minMaxRate = getMinAndMaxRate(code, topCount);
        return minMaxRate.getMaxRate() - minMaxRate.getMinRate();
    }
}
