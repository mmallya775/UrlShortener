package cloud.mallya.urlshortener.controller;

import cloud.mallya.urlshortener.dto.ShortenUrlRequestDTO;
import cloud.mallya.urlshortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody ShortenUrlRequestDTO requestDTO) {

        return urlService.shortenUrl(requestDTO);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Well hello!";
    }
}

