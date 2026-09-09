package com.mallya.urlshortener.controller;

import com.mallya.urlshortener.dto.CsrfResponseDTO;
import com.mallya.urlshortener.dto.CurrentUserResponseDTO;
import com.mallya.urlshortener.service.UsersService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UsersService usersService;

    public AuthController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/csrf")
    public CsrfResponseDTO csrfResponse(CsrfToken csrfToken) {
        return new CsrfResponseDTO(csrfToken.getHeaderName(), csrfToken.getToken());
    }

    @GetMapping("/me")
    public CurrentUserResponseDTO currentUser(Authentication authentication) {

        String username = authentication.getName();

        String name = usersService.getNameByUsername(username);

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new CurrentUserResponseDTO(username, name, roles);
    }
}
