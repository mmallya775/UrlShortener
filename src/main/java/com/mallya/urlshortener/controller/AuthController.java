package com.mallya.urlshortener.controller;

import com.mallya.urlshortener.dto.CsrfResponseDTO;
import com.mallya.urlshortener.dto.CurrentUserResponseDTO;
import com.mallya.urlshortener.dto.NewUserRequestDTO;
import com.mallya.urlshortener.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final UsersService usersService;

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

    @PostMapping("/createUser")
    public ResponseEntity<String> createNewUser(@RequestBody NewUserRequestDTO newUserRequestDTO) {
        try {
            usersService.createNewUser(newUserRequestDTO);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Account created successfully");
        }  catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }
}
