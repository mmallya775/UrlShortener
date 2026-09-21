package com.mallya.urlshortener.controller;

import com.mallya.urlshortener.dto.ShortenUrlRequestDTO;
import com.mallya.urlshortener.dto.ShortenUrlResponseDTO;
import com.mallya.urlshortener.entity.ShortLinks;
import com.mallya.urlshortener.service.UrlShortenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ShortenUrlResponseDTO shortenUrl(@RequestBody ShortenUrlRequestDTO request, Authentication authentication) {
        return urlShortenService.shortenUrl(request, authentication.getName());
    }

    @GetMapping("/urls")
    public List<ShortLinks> getAllUrls(Authentication authentication) {
        //        System.out.println(authentication.getName());
        return urlShortenService.getAllUrls(authentication.getName());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUrl(@PathVariable Long id, Authentication authentication) {
        urlShortenService.delete(id, authentication.getName());
    }
}
