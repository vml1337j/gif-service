package vml1337j.gifservice.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import feign.Feign;
import feign.RequestInterceptor;
import feign.jackson.JacksonDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vml1337j.gifservice.client.GiphyClient;
import vml1337j.gifservice.model.Gif;

import java.io.IOException;

@RequiredArgsConstructor
@Configuration
public class GiphyApiConfig {

    @Value("${giphy-api.api_key}")
    String apiKey;
    @Value("${giphy-api.url}")
    String url;

    @Bean
    public GiphyClient giphyClient() {
        return Feign.builder()
                .contract(new SpringMvcContract())
                .decoder(new JacksonDecoder())
                .requestInterceptor(giphyApiRequestInterceptor(apiKey))
                .target(GiphyClient.class, url);
    }

    private RequestInterceptor giphyApiRequestInterceptor(String apiKey) {
        return template -> template.query("api_key", this.apiKey);
    }

    public static class GifDeserializer extends JsonDeserializer<Gif> {

        @Override
        public Gif deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            ObjectCodec codec = p.getCodec();
            TreeNode rootNode = codec.readTree(p);
            String url = codec.treeToValue(rootNode.at("/data/images/original/url"), String.class);
            return new Gif(url);
        }
    }
}
