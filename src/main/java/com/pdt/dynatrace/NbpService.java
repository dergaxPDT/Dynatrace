package com.pdt.dynatrace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NbpService {

    private RestTemplate restTemplate;

    @Autowired
    public NbpService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
//                .errorHandler(new RestTemplateResponseErrorHandler()) //TODO
                .build();
    }

    public RatesTable getRatesTable(String code, String date) {
        String resourceUrl = "http://api.nbp.pl/api/exchangerates/rates/a/" + code + "/" + date + "/";
        return restTemplate
                .getForObject(resourceUrl, RatesTable.class);
    }
}
