package com.mallya.urlshortener.controller;

import com.mallya.urlshortener.dto.ShortenUrlRequestDTO;
import com.mallya.urlshortener.dto.ShortenUrlResponseDTO;
import com.mallya.urlshortener.service.UrlShortenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlShortenService urlShortenService;

    @PostMapping("/shorten")
    public ShortenUrlResponseDTO shortenUrl(@RequestBody ShortenUrlRequestDTO request) {
        return urlShortenService.shortenUrl(request);
    }
}