package org.Akhil.login.controller;

import org.Akhil.common.model.User;
import org.Akhil.common.request.UserRequest;
import org.Akhil.common.response.ApiResponse;
import org.Akhil.login.request.LoginRequest;
import org.Akhil.login.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserRequest user){
        User theUser=authService.createUser(user);
        return ResponseEntity.ok(ApiResponse.builder().message("Success").data(theUser).build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest){
        String jwtToken=authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.builder().message("Success").data(jwtToken).build());
    }

}
