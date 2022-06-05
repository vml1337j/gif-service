package vml1337j.gifservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vml1337j.gifservice.config.GiphyApiConfig;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = GiphyApiConfig.GifDeserializer.class)
public class Gif {
    private String gifUrl;
}
