package com.mallya.urlshortener.dto;

import java.util.List;

public record CurrentUserResponseDTO(String username, String name, List<String> roles) {}
