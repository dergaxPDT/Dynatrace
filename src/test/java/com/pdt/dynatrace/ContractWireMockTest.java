package com.pdt.dynatrace;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.pdt.dynatrace.data.MinMaxRate;
import com.pdt.dynatrace.data.Rate;
import com.pdt.dynatrace.data.RatesTable;
import com.pdt.dynatrace.data.Table;
import com.pdt.dynatrace.service.NbpService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.RequestEntity.get;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@SpringBootTest
class ContractWireMockTest {

    WireMockServer wireMockServer = new WireMockServer(8021);

    @Autowired
    private NbpService service;

    @Test
    void shouldGetRatesTable() throws IOException {
        wireMockServer.start();
        configureFor("localhost", 8021);
        ObjectMapper objectMapper = new ObjectMapper();
        stubFor(get(urlEqualTo("/gbp/2022-11-11/")).willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                .withBody(
                        objectMapper.writeValueAsString(new RatesTable(Table.A, "1", "GBP", Collections.emptyList()))
                )));
        RatesTable differenceRate = service.getRatesTable("gbp", "2022-11-11");
        Assertions.assertEquals(Table.A, differenceRate.getTable());
        Assertions.assertEquals("1", differenceRate.getCurrency());
        Assertions.assertEquals(Collections.emptyList(), differenceRate.getRates());
        Assertions.assertEquals("GBP", differenceRate.getCode());
        wireMockServer.stop();
    }

    @Test
    void shouldGetMinAndMaxRate() throws IOException {
        wireMockServer.start();
        configureFor("localhost", 8021);
        ObjectMapper objectMapper = new ObjectMapper();
        stubFor(get(urlEqualTo("/gbp/last/10/?format=json")).willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                .withBody(
                        objectMapper.writeValueAsString(new RatesTable(Table.A, "1", "GBP", List.of(new Rate("","",2), new Rate("","", 1))))
                )));
        MinMaxRate minMaxRate = service.getMinAndMaxRate("gbp", 10);
        Assertions.assertEquals(2, minMaxRate.getMaxRate());
        Assertions.assertEquals(1, minMaxRate.getMinRate());
        wireMockServer.stop();
    }
}
