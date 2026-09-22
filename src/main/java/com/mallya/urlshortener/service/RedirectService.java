package com.mallya.urlshortener.service;

import com.mallya.urlshortener.entity.ShortLinks;
import com.mallya.urlshortener.repository.ShortLinksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RedirectService {

    private final ShortLinksRepository shortLinksRepository;

    @Cacheable(value = "redirection", key = "#shortCode")
    public String getMainUrl(String shortCode) {
        ShortLinks link = shortLinksRepository
                .findByShortCode(shortCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "URL not found"));
        return link.getMainUrl();
    }
}
