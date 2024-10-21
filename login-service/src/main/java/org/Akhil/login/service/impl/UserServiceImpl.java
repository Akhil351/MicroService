package org.Akhil.login.service.impl;

import org.Akhil.common.dto.UserDto;
import org.Akhil.common.exception.ResourceNotFoundException;
import org.Akhil.common.mapper.Converter;
import org.Akhil.common.model.User;
import org.Akhil.common.repo.UserRepo;
import org.Akhil.common.request.UpdateUserRequest;
import org.Akhil.common.specification.SpecificationBuilder;
import org.Akhil.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private Converter converter;
    @Override
    public UserDto getUserById(String userId) {
        return this.convertToDto(userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found")));
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

    @Override
    public List<UserDto> getAllUsers(Map<String,String> params) {
        if(ObjectUtils.isEmpty(params.get("searchKey"))) return userRepo.findAll().stream().map(this::convertToDto).toList();
        Object searchKey=params.get("searchKey");
        SpecificationBuilder<User> specificationBuilder=new SpecificationBuilder<>();
        Specification<User> spec=specificationBuilder.contains("id",searchKey)
                .or(specificationBuilder.contains("firstName",searchKey))
                .or(specificationBuilder.contains("lastName",searchKey))
                .or(specificationBuilder.contains("email",searchKey))
                .or(specificationBuilder.contains("phoneNumber",searchKey));
        return userRepo.findAll(spec).stream().map(this::convertToDto).toList();
    }
    private UserDto convertToDto(User user){
        return converter.convertToDto(user, UserDto.class);
    }
}
