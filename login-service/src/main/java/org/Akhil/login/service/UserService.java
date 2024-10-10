package org.Akhil.login.service;

import org.Akhil.common.model.User;
import org.Akhil.common.request.UpdateUserRequest;
import org.Akhil.common.request.UserRequest;

public interface UserService {
    User getUserById(String userId);
    User createUser(UserRequest user);
    User updateUser(UpdateUserRequest user, String userId);
    void deleteUser(String userId);
}
