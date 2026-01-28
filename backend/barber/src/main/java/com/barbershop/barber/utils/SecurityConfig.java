package com.barbershop.barber.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;
    private final CustomAuthEntryPoint customAuthEntryPoint;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
               /*  .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/appointments", "/appointments/{id}").permitAll() 
                        .requestMatchers("/appointments/barber/apps").hasRole("ADMIN") 
                        .anyRequest().authenticated() 
                ) */
                .oauth2ResourceServer(oauth2 ->
                oauth2
                    .jwt(jwt ->
                        jwt.jwtAuthenticationConverter(jwtAuthConverter)
                    )
                    .authenticationEntryPoint(customAuthEntryPoint)
            );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
       UserDetails admin = User.builder()
            .username("admin")
            .password(encoder.encode("admin"))
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
