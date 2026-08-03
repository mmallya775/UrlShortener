package com.mallya.urlshortener.controller;

import com.mallya.urlshortener.dto.ShortenUrlRequestDTO;
import com.mallya.urlshortener.dto.ShortenUrlResponseDTO;
import com.mallya.urlshortener.service.UrlShortenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UrlController {

    private final UrlShortenService urlShortenService;

    /**
     * Accepts a full URL and generates the short code that
     * represents the full URL in the database.
     *
     * @param request request DTO containing URL to shorten
     * @return DTO representing the shortened URL
     */
    @PostMapping("/shorten")
    public ShortenUrlResponseDTO shortenUrl(@RequestBody ShortenUrlRequestDTO request) {
        return urlShortenService.shortenUrl(request);
    }
}
