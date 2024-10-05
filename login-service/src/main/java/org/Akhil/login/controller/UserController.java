package org.Akhil.login.controller;

import org.Akhil.common.model.User;
import org.Akhil.common.request.UpdateUserRequest;
import org.Akhil.common.request.UserRequest;
import org.Akhil.common.response.ApiResponse;
import org.Akhil.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v2/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable String userId){
            User user=userService.getUserById(userId);
            return ResponseEntity.ok(ApiResponse.builder().message("Success").data(user).build());
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserRequest user){
        User theUser=userService.createUser(user);
        return ResponseEntity.ok(ApiResponse.builder().message("Success").data(theUser).build());
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest user,@PathVariable String userId){
        User theUser=userService.updateUser(user,userId);
        return ResponseEntity.ok(ApiResponse.builder().message("Success").data(theUser).build());
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.builder().message("User deleted Successfully").data(null).build());
    }
}
