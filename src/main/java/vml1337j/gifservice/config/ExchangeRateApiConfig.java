package vml1337j.gifservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.RequestInterceptor;
import feign.jackson.JacksonDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vml1337j.gifservice.client.ExchangeRateClient;

@RequiredArgsConstructor
@Configuration
public class ExchangeRateApiConfig {

    @Value("${exchange-rate-api.url}") String url;
    @Value("${exchange-rate-api.api_key}") String apiKey;
    @Value("${exchange-rate-api.base_currency}") String baseCurrency;

    private final ObjectMapper mapper;

    @Bean
    public ExchangeRateClient exchangeRateClient() {
        return Feign.builder()
                .contract(new SpringMvcContract())
                .decoder(new JacksonDecoder(mapper))
                .requestInterceptor(exchangeRateRequestInterceptor(apiKey, baseCurrency))
                .target(ExchangeRateClient.class, url);
    }

    private RequestInterceptor exchangeRateRequestInterceptor(String apiKey, String baseCurrency){
        return template -> template.query("base", baseCurrency)
                .query("app_id", apiKey);
    }
}
