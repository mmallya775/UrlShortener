package com.mallya.urlshortener.service;

import com.mallya.urlshortener.dto.ShortenUrlRequestDTO;
import com.mallya.urlshortener.dto.ShortenUrlResponseDTO;
import com.mallya.urlshortener.entity.ShortLinks;
import com.mallya.urlshortener.repository.ShortLinksRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UrlShortenService {

    private final ShortLinksRepository shortLinksRepository;
    private final CacheManager cacheManager;

    /**
     * Creates a shortened URL from the URL contained in the request.
     *
     * @param requestDTO DTO containing the URL to be shortened
     * @return DTO containing the shortened URL
     */
    public ShortenUrlResponseDTO shortenUrl(ShortenUrlRequestDTO requestDTO, String username) {
        ShortenUrlResponseDTO responseDTO = new ShortenUrlResponseDTO();

        String shortCode = RandomStringUtils.secure().nextAlphanumeric(8);
        //        String shortCode = "TODO";
        ShortLinks shortLinks = new ShortLinks();

        shortLinks.setUsername(username);
        shortLinks.setMainUrl(requestDTO.getUrl());
        shortLinks.setShortCode(shortCode);

        shortLinksRepository.save(shortLinks);

        responseDTO.setShortUrl(shortCode);

        return responseDTO;
    }

    public List<ShortLinks> getAllUrls(String username) {
        return shortLinksRepository.findByUsername(username);
    }

    @Transactional
    public void delete(Long id, String username) {
        ShortLinks link = shortLinksRepository
                .findByIdAndUsername(id, username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "URL not found"));

        shortLinksRepository.delete(link);

        Cache cache = cacheManager.getCache("redirection");


        if (cache != null) {
            cache.evictIfPresent(link.getShortCode());
        }
    }
}
