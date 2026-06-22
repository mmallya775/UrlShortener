package cloud.mallya.urlshortener.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShortenUrlRequestDTO {

    private String url;
}
