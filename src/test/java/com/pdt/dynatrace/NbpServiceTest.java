package com.pdt.dynatrace;

import com.pdt.dynatrace.data.MinMaxRate;
import com.pdt.dynatrace.data.RatesTable;
import com.pdt.dynatrace.service.NbpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//    @Disabled("Manual Test for test API NBP")
class NbpServiceTest {

    @Autowired
    private NbpService nbpService;

    @Test
    void getAverageExchangeRate() {
        RatesTable ratesTable = nbpService.getRatesTable("gbp", "2012-01-02");
        assertNotNull(ratesTable);
        assertNotNull(ratesTable.getCode());
        assertEquals("GBP", ratesTable.getCode());
        assertEquals("A", ratesTable.getTable().toString());
        assertEquals("funt szterling", ratesTable.getCurrency());
        assertEquals("1/A/NBP/2012", ratesTable.getRates().get(0).getNo());
        assertEquals("2012-01-02", ratesTable.getRates().get(0).getEffectiveDate());
        assertEquals(5.3480, ratesTable.getRates().get(0).getMid());
    }

    @Test
    void getAveragesssExchangeRate() {
        MinMaxRate minMaxRate = nbpService.getMinAndMaxRate("gbp", 10);
        assertEquals(5.2086, minMaxRate.getMinRate());
        assertEquals(5.3369, minMaxRate.getMaxRate());
    }

    @Test
    void differenceRate() {
        double differenceRate = nbpService.getDifferenceRate("gbp", 10);
        assertEquals(0.1283000000000003, differenceRate);
    }
}