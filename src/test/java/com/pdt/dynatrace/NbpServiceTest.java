package com.pdt.dynatrace;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
    @Disabled("Manual Test for test API NBP")
class NbpServiceTest {

    @Autowired
    private NbpService nbpService;

    @Test
    void getAverageExchangeRate() {
        RatesTable ratesTable = nbpService.getRatesTable("gbp", "2012-01-02");
        assertNotNull(ratesTable);
        assertNotNull(ratesTable.code);
        assertEquals("GBP", ratesTable.code);
        assertEquals("A", ratesTable.table.toString());
        assertEquals("funt szterling", ratesTable.currency);
        assertEquals("1/A/NBP/2012", ratesTable.rates.get(0).no);
        assertEquals("2012-01-02", ratesTable.rates.get(0).effectiveDate);
        assertEquals(5.3480, ratesTable.rates.get(0).mid);
    }
}