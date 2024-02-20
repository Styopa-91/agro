package com.abi.agro_back.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.abi.agro_back.collection.Role.ADMIN;
import static com.abi.agro_back.collection.Role.USER;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
//            "/api/auth/register",
            "/api/auth/authenticate",
            "/swagger-ui/**",
            "/v3/**",
            "/api/users/resetPassword/**",
            "/api/users/savePassword",
    };

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()

                                .requestMatchers("/api/auth/register").hasAuthority(ADMIN.name())

                                .requestMatchers(GET, "/api/users").hasAuthority(ADMIN.name())
                                .requestMatchers(POST, "/api/users").hasAnyAuthority(ADMIN.name())
//                                .requestMatchers(POST, "/api/users/**").hasAnyAuthority(ADMIN.name())
                                .requestMatchers(PUT, "/api/users/**").hasAuthority(ADMIN.name())
                                .requestMatchers(DELETE, "/api/users/**").hasAuthority(ADMIN.name())

                                .requestMatchers(GET, "/api/reg-request").hasAuthority(ADMIN.name())
                                .requestMatchers(POST, "/api/reg-request").permitAll()
                                .requestMatchers(PUT, "/api/reg-request/**").hasAuthority(ADMIN.name())
                                .requestMatchers(DELETE, "/api/reg-request/**").hasAuthority(ADMIN.name())

                                .requestMatchers(GET, "/api/agrarians").hasAnyAuthority(USER.name(), ADMIN.name())
                                .requestMatchers(POST, "/api/agrarians/**").hasAnyAuthority(ADMIN.name())
                                .requestMatchers(PUT, "/api/agrarians/**").hasAuthority(ADMIN.name())
                                .requestMatchers(DELETE, "/api/agrarians/**").hasAuthority(ADMIN.name())

                                .requestMatchers(GET, "/api/banners").permitAll()
                                .requestMatchers(POST, "/api/banners/**").hasAnyAuthority(ADMIN.name())
                                .requestMatchers(PUT, "/api/banners/**").hasAuthority(ADMIN.name())
                                .requestMatchers(DELETE, "/api/banners/**").hasAuthority(ADMIN.name())

                                .requestMatchers(GET, "/api/exhibitions").permitAll()
                                .requestMatchers(POST, "/api/exhibitions/**").hasAnyAuthority(ADMIN.name())
                                .requestMatchers(PUT, "/api/exhibitions/**").hasAuthority(ADMIN.name())
                                .requestMatchers(DELETE, "/api/exhibitions/**").hasAuthority(ADMIN.name())

                                .requestMatchers(GET, "/api/imagePages").permitAll()
                                .requestMatchers(POST, "/api/imagePages/**").hasAnyAuthority(ADMIN.name())
                                .requestMatchers(PUT, "/api/imagePages/**").hasAuthority(ADMIN.name())
                                .requestMatchers(DELETE, "/api/imagePages/**").hasAuthority(ADMIN.name())

                                .requestMatchers(GET, "/api/pages").permitAll()
                                .requestMatchers(POST, "/api/pages/**").hasAnyAuthority(ADMIN.name())
                                .requestMatchers(PUT, "/api/pages/**").hasAuthority(ADMIN.name())
                                .requestMatchers(DELETE, "/api/pages/**").hasAuthority(ADMIN.name())

                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
