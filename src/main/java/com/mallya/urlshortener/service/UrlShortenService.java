package com.mallya.urlshortener.service;

import com.mallya.urlshortener.dto.ShortenUrlRequestDTO;
import com.mallya.urlshortener.dto.ShortenUrlResponseDTO;
import com.mallya.urlshortener.entity.ShortLinks;
import com.mallya.urlshortener.repository.ShortLinksRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlShortenService {

    private final ShortLinksRepository shortLinksRepository;

    /**
     * Creates a shortened URL from the URL contained in the request.
     *
     * @param requestDTO DTO containing the URL to be shortened
     * @return DTO containing the shortened URL
     */
    public ShortenUrlResponseDTO shortenUrl(ShortenUrlRequestDTO requestDTO) {
        ShortenUrlResponseDTO responseDTO = new ShortenUrlResponseDTO();

        String shortCode = RandomStringUtils.secure().nextAlphanumeric(8);
        //        String shortCode = "TODO";
        ShortLinks shortLinks = new ShortLinks();

        shortLinks.setUserId(1L);
        shortLinks.setMainUrl(requestDTO.getUrl());
        shortLinks.setShortCode(shortCode);

        shortLinksRepository.save(shortLinks);

        responseDTO.setShortUrl(shortCode);

        return responseDTO;
    }
}
