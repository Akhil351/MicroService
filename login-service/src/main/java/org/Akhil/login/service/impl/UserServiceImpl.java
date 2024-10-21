package org.Akhil.login.service.impl;

import jakarta.transaction.Transactional;
import org.Akhil.common.dto.UserDto;
import org.Akhil.common.exception.ResourceNotFoundException;
import org.Akhil.common.mapper.Converter;
import org.Akhil.common.model.User;
import org.Akhil.common.repo.RolesRepo;
import org.Akhil.common.repo.UserRepo;
import org.Akhil.common.request.UpdateUserRequest;
import org.Akhil.login.service.CartClient;
import org.Akhil.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RolesRepo rolesRepo;
    @Autowired
    private Converter converter;
    @Autowired
    private CartClient cartClient;
    @Override
    public User updateUser(UpdateUserRequest user, String userId) {
        return userRepo.findById(userId)
                .map(existingUser->this.updateExistingUser(existingUser,user))
                .map(userRepo::save)
                .orElseThrow(()->new ResourceNotFoundException("User Not Found"));
    }
    private User updateExistingUser(User existingUser,UpdateUserRequest user){
        if(!ObjectUtils.isEmpty(user.getFirstName())) existingUser.setFirstName(user.getFirstName());
        if(!ObjectUtils.isEmpty(user.getLastName())) existingUser.setLastName(user.getLastName());
        return existingUser;
    }

    @Transactional
    @Override
    public void deleteUser(String userId) {
        userRepo.findById(userId).ifPresentOrElse(userRepo::delete,()->{throw new ResourceNotFoundException("User Not Found");});
        rolesRepo.deleteAllByUserId(userId);
        cartClient.deleteCart(userId);
    }

    @Override
    public List<UserDto> getAllUsers(Map<String,String> params) {
        return userRepo.findAll().stream().map(this::convertToDto).toList();
    }
    private UserDto convertToDto(User user){
        return converter.convertToDto(user, UserDto.class);
    }
}
