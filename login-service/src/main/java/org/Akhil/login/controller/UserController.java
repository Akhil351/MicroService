package org.Akhil.login.controller;

import org.Akhil.common.dto.UserDto;
import org.Akhil.common.model.User;
import org.Akhil.common.request.UpdateUserRequest;
import org.Akhil.common.response.ApiResponse;
import org.Akhil.common.util.JwtUtils;
import org.Akhil.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest user,@PathVariable String userId){
        User theUser=userService.updateUser(user,userId);
        return ResponseEntity.ok(ApiResponse.builder().message("Success").data(theUser).build());
    }

    @PreAuthorize("@securityValidate.isAdmin()")
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.builder().message("User deleted Successfully").data(null).build());
    }

    @PreAuthorize("@securityValidate.isAdmin()")
    @GetMapping("/getAllUsers")
    public  ResponseEntity<ApiResponse> getAllUsers(@RequestBody Map<String,String> params){
        List<UserDto> users=userService.getAllUsers(params);
        return ResponseEntity.ok(ApiResponse.builder().message("Success").data(users).build());
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> userProfile(@RequestHeader(JwtUtils.JWT_HEADER) String jwt){
        UserDto userDto=userService.userProfile(jwt.substring(7));
        return ResponseEntity.ok(ApiResponse.builder().message("Success").data(userDto).build());
    }
}
