package org.Akhil.login.service.impl;

import org.Akhil.common.exception.ResourceNotFoundException;
import org.Akhil.common.model.User;
import org.Akhil.common.repo.UserRepo;
import org.Akhil.common.request.UpdateUserRequest;
import org.Akhil.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public User getUserById(String userId) {
        return userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
    }
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

    @Override
    public void deleteUser(String userId) {
        userRepo.findById(userId).ifPresentOrElse(userRepo::delete,()->{throw new ResourceNotFoundException("User Not Found");});
    }
}
