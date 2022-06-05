package vml1337j.gifservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;
import vml1337j.gifservice.client.ExchangeRateClient;
import vml1337j.gifservice.client.GiphyClient;
import vml1337j.gifservice.exception.InvalidCurrencyCodeException;
import vml1337j.gifservice.model.Exchange;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class GifControllerTest {

    @Autowired
    GifController gifController;

    @MockBean
    ExchangeRateClient exchangeRateClient;

    @MockBean
    GiphyClient giphyClient;

    @MockBean
    Model model;

    public static final String TEST_CURRENCY = "RUB";

    @Test
    void whenTodayExchangeRateSmallerThenYesterday_callWithBrokeTag() {
        setMockedExchangeRates(1.0, 2.0);

        gifController.getGifByExchangeDiff(TEST_CURRENCY, model);

        verify(giphyClient, only()).getRandomGifByTag(GifController.BROKE_TAG);
    }

    @Test
    void callGetRandomGifByTag_whenTodayExchangeRateBiggerThenYesterday_callWithRichTag() {
        setMockedExchangeRates(2.0, 1.0);

        gifController.getGifByExchangeDiff(TEST_CURRENCY, model);

        verify(giphyClient, only()).getRandomGifByTag(GifController.RICH_TAG);
    }

    @Test
    void throwInvalidCurrencyCodeExceptionWhenCodeNotContains() {
        String notContainsCode = "QWE";
        setMockedExchangeRates(0.0, 0.0);

        InvalidCurrencyCodeException actualException = assertThrows(InvalidCurrencyCodeException.class, () -> {
            gifController.getGifByExchangeDiff(notContainsCode, model);
        });

        assertTrue(actualException.getMessage().contains("not supported"));
    }

    private void setMockedExchangeRates(Double exchangeRateToday, Double exchangeRateYesterday) {
        when(exchangeRateClient.getLatestExchange())
                .thenReturn(getMockedExchange(exchangeRateToday));

        when(exchangeRateClient.getHistoricalExchange(GifController.YESTERDAY))
                .thenReturn(getMockedExchange(exchangeRateYesterday));
    }

    private Exchange getMockedExchange(Double exchangeRate) {
        return new Exchange(
                null,
                null,
                Map.of(TEST_CURRENCY, exchangeRate)
        );
    }
}
