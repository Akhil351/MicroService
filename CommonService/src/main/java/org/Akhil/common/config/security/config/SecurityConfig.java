package org.Akhil.common.config.security.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.Akhil.common.config.jwt.JwtFilter;
import org.Akhil.common.config.security.SecurityValidate;
import org.Akhil.common.config.userDetails.CustomerDetailsService;
import org.Akhil.common.util.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


import java.util.Collections;
import java.util.List;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private CustomerDetailsService customerDetailsService;
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private SecurityValidate securityValidate;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(
                                        "/api/v2/user/**",
                                        "/api/v2/products/add",
                                        "/api/v2/products/delete/**",
                                        "/api/v2/products/update/**",
                                        "/api/v2/categories/**",
                                        "/api/v2/images/**",
                                        "/api/v2/carts/my-cart",
                                        "/api/v2/carts/getTotalPrice",
                                        "/api/v2/cartItems/**",
                                        "/api/v2/orders/**"
                                ).authenticated()
                                .anyRequest().permitAll()
                )
                .cors(cors->cors.configurationSource(corsConfigurationSource()))
              .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

        private CorsConfigurationSource corsConfigurationSource() {
            return new CorsConfigurationSource() {
                @Override
                public CorsConfiguration getCorsConfiguration(@NonNull HttpServletRequest request) {
                    CorsConfiguration cfg=new CorsConfiguration();
                    cfg.setAllowedOrigins(List.of(JwtUtils.BASE_URL));
                    cfg.setAllowedMethods(List.of("POST","GET","PUT","PATCH","DELETE"));
                    cfg.setAllowCredentials(true);
                    cfg.setAllowedHeaders(Collections.singletonList("*"));
                    cfg.setExposedHeaders(List.of(JwtUtils.JWT_HEADER));
                    cfg.setMaxAge(3600L);
                    return cfg;
                }
            };
        }
//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
//        provider.setUserDetailsService(customerDetailsService);
//        provider.setPasswordEncoder(this.passwordEncoder());
//        return provider;
//    }
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
}



//When a user submits their credentials, an Authentication object is created (for example, a UsernamePasswordAuthenticationToken).
//The AuthenticationManager receives this object and passes it to the configured AuthenticationProvider (such as DaoAuthenticationProvider).
//If the authentication is successful, the AuthenticationProvider returns an authenticated Authentication object, which includes the user’s roles and permissions.
//If none of the providers authenticate the request, the AuthenticationManager throws an AuthenticationException.
