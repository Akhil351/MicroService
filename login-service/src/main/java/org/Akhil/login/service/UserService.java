package org.Akhil.login.service;

import org.Akhil.common.dto.UserDto;
import org.Akhil.common.model.User;
import org.Akhil.common.request.UpdateUserRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserService {
    User updateUser(UpdateUserRequest user, String userId);
    void deleteUser(String userId);
    List<UserDto> getAllUsers(Map<String,Object> params);
    UserDto userProfile(String userId);
    BigDecimal getCartBalance();
}
