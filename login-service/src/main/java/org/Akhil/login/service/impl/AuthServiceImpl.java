package org.Akhil.login.service.impl;

import org.Akhil.common.enums.Role;
import org.Akhil.common.exception.InvalidCredential;
import org.Akhil.common.exception.UserAlreadyExist;
import org.Akhil.common.model.User;
import org.Akhil.common.repo.UserRepo;
import org.Akhil.common.request.UserRequest;
import org.Akhil.login.config.jwt.JwtService;
import org.Akhil.login.config.userDetails.CustomerDetailsService;
import org.Akhil.login.request.LoginRequest;
import org.Akhil.login.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerDetailsService customerDetailsService;
    @Autowired
    private JwtService jwtService;
    @Override
    public User createUser(UserRequest user) {
        userRepo.findByEmail(user.getEmail()).ifPresent((u)->{throw new UserAlreadyExist("user with email "+user.getEmail()+ " already exist");});
        List<Integer> roles;
        if(ObjectUtils.isEmpty(user.getRole())) roles=List.of(101);
        else roles=user.getRole().stream().map(Role::code).toList();
        return userRepo.save(User.builder()
                .id(UUID.randomUUID().toString())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(passwordEncoder.encode(user.getPassword()))
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .roles(roles)
                .build());
    }

    @Override
    public String login(LoginRequest request) {
        Authentication authentication=this.authentication(request.getEmail(),request.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtService.generateKey(authentication);
    }
    private Authentication authentication(String email,String password){
        UserDetails customerDetails=customerDetailsService.loadUserByUsername(email);
        if(!passwordEncoder.matches(password,customerDetails.getPassword())){
            throw new InvalidCredential("Invalid Credential");
        }
        return new UsernamePasswordAuthenticationToken(customerDetails,customerDetails.getPassword(),customerDetails.getAuthorities());
    }
}
