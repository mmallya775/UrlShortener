package cloud.mallya.urlshortener.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "url")
public class UrlEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String mainUrl;

    @Column(unique = true)
    private String shortCode;
}
