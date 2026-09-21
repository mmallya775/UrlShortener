package com.mallya.urlshortener.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class SecurityConfig {

    /**
     * Provides a JDBC-backed user details manager using the
     * application's PostgreSQL datasource.
     * @param dataSource configured datasource
     * @return JDBC-backed user details manager
     */
    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    /**
     * Provides a password encoder that is used to encode, decode
     * and verify user passwords. Supports multiple encoding algorithms
     * and the password is stored in the database table with prefixed
     * algorithm. For e.g., {bcrypt|noop|...}
     *
     * @return Password encoder used by authentication provider
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Configures the application's Spring Security filter chain.
     *
     * @param httpSecurity the Spring Security HTTP configuration
     * @return the configured security filter chain
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/csrf")
                        .permitAll()
                        .requestMatchers("/api/createUser")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(
                        (_, response, _) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                .formLogin(form -> form.loginProcessingUrl("/login")
                        .successHandler(((_, response, _) -> response.setStatus(HttpServletResponse.SC_OK)))
                        .failureHandler(((_, response, _) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessHandler(((_, response, _) -> response.setStatus(HttpServletResponse.SC_OK))));

        return httpSecurity.build();
    }

    /**
     * Configure CORS for the frontend application.
     *
     * @param frontendUrl he allowed frontend origin configured through {@code app.frontend-url}
     * @return the CORS configuration source used by Spring Security
     */
    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource(@Value("${app.frontend-url}") String frontendUrl) {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(frontendUrl));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    /**
     * Creates initial users for development when the application starts.
     *
     * @param userDetailsManager the user manager used to create security users
     * @param passwordEncoder the encoder used to securely encode passwords
     * @param jdbcTemplate JDBC helper used to update additional user fields
     * @return a startup task that creates the initial development users
     */
    @Bean
    CommandLineRunner createInitialUser(
            UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder, JdbcTemplate jdbcTemplate) {
        return _ -> {
            if (!userDetailsManager.userExists("user")) {
                userDetailsManager.createUser(User.builder()
                        .username("user")
                        .password(passwordEncoder.encode(
                                "password")) // TODO: Replace with configurable custom strong password
                        .roles("USER")
                        .build());
                jdbcTemplate.update("UPDATE users SET name = ? WHERE username = ?", "Mock User", "user");
            }

            if (!userDetailsManager.userExists("admin")) {
                userDetailsManager.createUser(User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode(
                                "password")) // TODO: Replace with configurable custom strong password
                        .roles("USER", "ADMIN")
                        .build());
                jdbcTemplate.update("UPDATE users SET name = ? WHERE username = ?", "Administrator", "admin");
            }
        };
    }
}
