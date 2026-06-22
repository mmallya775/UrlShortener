package cloud.mallya.urlshortener.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortenUrlResponseDTO {

    private String shortCode;
}
