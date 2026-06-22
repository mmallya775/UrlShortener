package cloud.mallya.urlshortener.service;

import cloud.mallya.urlshortener.dto.ShortenUrlRequestDTO;
import cloud.mallya.urlshortener.dto.ShortenUrlResponseDTO;
import cloud.mallya.urlshortener.entity.UrlEntity;
import cloud.mallya.urlshortener.repository.UrlRepository;
import cloud.mallya.urlshortener.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlUtils urlUtils;
    private final UrlRepository urlRepository;

    public ShortenUrlResponseDTO shortenUrl(ShortenUrlRequestDTO requestDTO) {
        String url = requestDTO.getUrl();
        try {

            boolean isValid = urlUtils.isValid(url);
            if (!isValid) {
                throw new RuntimeException("URL is invalid");
            }

            String shortCode = RandomStringUtils.secure().nextAlphabetic(8);

            UrlEntity urlEntity = new UrlEntity();
            urlEntity.setMainUrl(url);
            urlEntity.setShortCode(shortCode);

            urlRepository.save(urlEntity);

            return ShortenUrlResponseDTO.builder()
                    .shortCode(shortCode)
                    .build();
        } catch (DataIntegrityViolationException e){
            String shortCode = RandomStringUtils.secure().nextAlphabetic(8);

            UrlEntity urlEntity = new UrlEntity();
            urlEntity.setMainUrl(url);
            urlEntity.setShortCode(shortCode);

            urlRepository.save(urlEntity);

            return ShortenUrlResponseDTO.builder()
                    .shortCode(shortCode)
                    .build();
        }
    }
}
