package com.mallya.urlshortener.controller;

import com.mallya.urlshortener.service.RedirectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class RedirectContoller {

    private final RedirectService redirectService;

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String mainUrl = redirectService.getMainUrl(shortCode);

        URI location = URI.create(
                mainUrl.startsWith("http://") || mainUrl.startsWith("https://") ? mainUrl : "https://" + mainUrl);

        return ResponseEntity.status(HttpStatus.FOUND).location(location).build();
    }
}
