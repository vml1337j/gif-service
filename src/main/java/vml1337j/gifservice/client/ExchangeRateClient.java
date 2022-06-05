package vml1337j.gifservice.client;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vml1337j.gifservice.model.Exchange;

import java.time.LocalDate;

public interface ExchangeRateClient {

    @GetMapping("/historical/{date}.json")
    Exchange getHistoricalExchange(@PathVariable
                                   @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);

    @GetMapping("/latest.json")
    Exchange getLatestExchange();
}
