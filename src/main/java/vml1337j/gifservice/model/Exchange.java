package vml1337j.gifservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exchange {
    @JsonProperty("timestamp")
    private Instant date;

    @JsonProperty("base")
    private String baseCurrency;

    @JsonProperty("rates")
    Map<String, Double> rates;
}
