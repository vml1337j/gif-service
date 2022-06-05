package vml1337j.gifservice.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vml1337j.gifservice.model.Gif;

public interface GiphyClient {

    @GetMapping("/gifs/random")
    Gif getRandomGifByTag(@RequestParam(value = "tag") String tag);
}
