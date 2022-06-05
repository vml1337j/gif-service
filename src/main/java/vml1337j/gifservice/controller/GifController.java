package vml1337j.gifservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vml1337j.gifservice.client.ExchangeRateClient;
import vml1337j.gifservice.client.GiphyClient;
import vml1337j.gifservice.exception.InvalidCurrencyCodeException;
import vml1337j.gifservice.model.Exchange;
import vml1337j.gifservice.model.Gif;

import java.time.LocalDate;

@RequiredArgsConstructor
@Controller
public class GifController {

    public static final String FORM_URL = "/api/v1/currencies/{code}/rate";

    public final static String RICH_TAG = "rich";
    public final static String BROKE_TAG = "broke";

    private final GiphyClient giphyClient;
    private final ExchangeRateClient exchangeRateClient;

    public static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);

    @GetMapping(FORM_URL)
    public String getGifByExchangeDiff(@PathVariable("code") String currencyCode, Model model) {

        Exchange todayExchange = exchangeRateClient.getLatestExchange();
        Exchange yesterdayExchange = exchangeRateClient.getHistoricalExchange(YESTERDAY);

        if (!todayExchange.getRates().containsKey(currencyCode)) {
            throw new InvalidCurrencyCodeException(String.format("Code is %s not supported", currencyCode));
        }

        Double todayRate = todayExchange.getRates().get(currencyCode);
        Double yesterdayRate = yesterdayExchange.getRates().get(currencyCode);

        Gif gif = getGifByRates(todayRate, yesterdayRate);

        model.addAttribute("gif", gif);
        model.addAttribute("todayRate", todayRate);
        model.addAttribute("yesterdayRate", yesterdayRate);

        return "index";
    }

    private Gif getGifByRates(Double todayRate, Double yesterdayRate) {
        return todayRate > yesterdayRate ?
                giphyClient.getRandomGifByTag(RICH_TAG) :
                giphyClient.getRandomGifByTag(BROKE_TAG);
    }
}
