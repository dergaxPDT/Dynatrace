package com.pdt.dynatrace;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.pdt.dynatrace.exception.NotFoundException;
import com.pdt.dynatrace.exception.TooMuchQuantityException;
import com.pdt.dynatrace.model.MinMaxRate;
import com.pdt.dynatrace.model.Rate;
import com.pdt.dynatrace.model.RatesTable;
import com.pdt.dynatrace.model.Table;
import com.pdt.dynatrace.service.NbpService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.http.RequestEntity.get;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@RequiredArgsConstructor
class ContractWireMockTest {

    static WireMockServer wireMockServer = new WireMockServer(8021);

    private final NbpService service;

    @BeforeAll
    static void setupBefore(){
        wireMockServer.start();
        configureFor("localhost", 8021);
    }
    private  final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldGetRatesTable() throws IOException {
        stubFor(get(urlPathMatching(".*gbp/2022-11-11/")).willReturn(aResponse()
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
    }

    @Test
    void shouldGetMinAndMaxRate() throws IOException {
        stubFor(get(urlPathMatching(".*gbp/last/10/.*")).willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                .withBody(
                        objectMapper.writeValueAsString(new RatesTable(Table.A, "1", "GBP", List.of(new Rate("","",2), new Rate("","", 1))))
                )));
        MinMaxRate minMaxRate = service.getMinAndMaxRate("gbp", 10);
        Assertions.assertEquals(2, minMaxRate.getMaxRate());
        Assertions.assertEquals(1, minMaxRate.getMinRate());
    }

    @Test
    void shouldGetDifferences() throws IOException {
        stubFor(get(urlPathMatching(".*gbp/last/10/.*")).willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                .withBody(
                        objectMapper.writeValueAsString(new RatesTable(Table.A, "1", "GBP", List.of(new Rate("","",2), new Rate("","", 1))))
                )));
        double differenceRate = service.getDifferenceRate("gbp", 10);
        Assertions.assertEquals(1, differenceRate);}

    @Test
    void shouldTooMuchQuantityExceptionGetMinAndMaxRate() throws IOException {
        stubFor(get(urlPathMatching(".*gbp/last/10/.*")).willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                .withBody(
                        objectMapper.writeValueAsString(new RatesTable(Table.A, "1", "GBP", List.of(new Rate("","",2), new Rate("","", 1))))
                )));
        Exception exception = Assertions.assertThrows(TooMuchQuantityException.class, () -> {
            service.getMinAndMaxRate("rrr", 260);
        });
        Assertions.assertEquals("Quantity is over 255 - count: 260", exception.getMessage());
    }

    @AfterAll
    static void setupAfter() {
        wireMockServer.start();
        configureFor("localhost", 8021);
    }
}
