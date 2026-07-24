package com.mallya.urlshortener.service;

import com.mallya.urlshortener.dto.ShortenUrlRequestDTO;
import com.mallya.urlshortener.dto.ShortenUrlResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenService {

    public ShortenUrlResponseDTO shortenUrl(ShortenUrlRequestDTO requestDTO ) {
        ShortenUrlResponseDTO responseDTO = new ShortenUrlResponseDTO();

        responseDTO.setShortUrl("TODO");

        return responseDTO;
    }
}
