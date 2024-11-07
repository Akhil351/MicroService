package org.Akhil.login.service.impl;

import feign.FeignException;
import org.Akhil.common.dto.UserDto;
import org.Akhil.common.exception.ResourceNotFoundException;
import org.Akhil.common.mapper.Converter;
import org.Akhil.common.model.User;
import org.Akhil.common.repo.RolesRepo;
import org.Akhil.common.repo.UserRepo;
import org.Akhil.common.request.UpdateUserRequest;
import org.Akhil.common.util.Utils;
import org.Akhil.login.service.CartClient;
import org.Akhil.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
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
    @Autowired
    private MongoTemplate mongoTemplate;
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
        try{
            cartClient.deleteCart(userId);
            userRepo.findById(userId).ifPresentOrElse(userRepo::delete,()->{throw new ResourceNotFoundException("User Not Found");});
            rolesRepo.deleteAllByUserId(userId);
        }
        catch (FeignException e){
            System.out.println("server is down");
            throw e;
        }
    }

    @Override
    public List<UserDto> getAllUsers(Map<String,Object> params) {
        int pageNo=(ObjectUtils.isEmpty(params.get(Utils.PAGE_NO)))?0:Integer.parseInt(params.get(Utils.PAGE_NO).toString());
        int pageSize=(ObjectUtils.isEmpty(params.get(Utils.PAGE_SIZE)))?5:Integer.parseInt(params.get(Utils.PAGE_SIZE).toString());
        Sort sort=Sort.by(Sort.Order.asc("name"));
        PageRequest pageRequest=PageRequest.of(pageNo,pageSize,sort);
        if(ObjectUtils.isEmpty(params.get("searchKey"))){
            return userRepo.findAll(pageRequest).stream().map(this::convertToDto).toList();
        }
        Object searchKey=params.get("searchKey");
        Query query = new Query();
        query.addCriteria(
                new Criteria().orOperator(
                        Criteria.where("firstName").regex(searchKey.toString(), "i"),
                        Criteria.where("lastName").regex(searchKey.toString(), "i"),
                        Criteria.where("id").is(searchKey),
                        Criteria.where("email").regex(searchKey.toString(), "i"),
                        Criteria.where("phoneNumber").regex(searchKey.toString(), "i")
                )
        );
        query.skip(pageNo*pageSize).limit(pageSize);
        return mongoTemplate.find(query,User.class).stream().map(this::convertToDto).toList();
    }

    @Override
    public UserDto userProfile(String userId) {
        User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
        return convertToDto(user);
    }

    @Override
    public BigDecimal getCartBalance() {
        return cartClient.getTotalAmount();
    }

    private UserDto convertToDto(User user){
        return converter.convertToDto(user, UserDto.class);
    }
}
