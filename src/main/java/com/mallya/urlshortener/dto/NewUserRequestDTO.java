package com.mallya.urlshortener.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewUserRequestDTO {
    private String name;
    private String username;
    private String password;
}
