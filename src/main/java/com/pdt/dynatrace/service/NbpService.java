package com.pdt.dynatrace.service;

import com.pdt.dynatrace.data.MinMaxRate;
import com.pdt.dynatrace.data.Rate;
import com.pdt.dynatrace.data.RatesTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class NbpService {

    private RestTemplate restTemplate;

    @Autowired
    public NbpService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
//                .errorHandler(new RestTemplateResponseErrorHandler()) //TODO
                .build();
    }

    @Value("${api.url}")
    private String url;

    public RatesTable getRatesTable(String code, String date) {
        String resourceUrl = url + code + "/" + date + "/";
        return restTemplate
                .getForObject(resourceUrl, RatesTable.class);
    }

    public MinMaxRate getMinAndMaxRate(String code, int topCount) {
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
