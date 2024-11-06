package org.Akhil.common.config.security.config;

import org.Akhil.common.config.jwt.JwtFilter;
import org.Akhil.common.config.security.SecurityValidate;
import org.Akhil.common.config.userDetails.CustomerDetailsService;
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
                                        "/api/v2/carts/**",
                                        "/api/v2/cartItems/**",
                                        "/api/v2/orders/**"
                                ).authenticated()
                                .anyRequest().permitAll()
                )
              .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
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
