package cloud.mallya.urlshortener.controller;

import cloud.mallya.urlshortener.dto.ShortenUrlRequestDTO;
import cloud.mallya.urlshortener.dto.ShortenUrlResponseDTO;
import cloud.mallya.urlshortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public ShortenUrlResponseDTO shortenUrl(@RequestBody ShortenUrlRequestDTO requestDTO) {
        return urlService.shortenUrl(requestDTO);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> getRedirectionUrl(@PathVariable String shortCode) {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY.value())
                .location(urlService.getRedirectionURI(shortCode))
                .build();
    }

}

