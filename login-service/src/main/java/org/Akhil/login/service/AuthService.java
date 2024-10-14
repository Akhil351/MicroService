package org.Akhil.login.service;

import org.Akhil.common.model.User;
import org.Akhil.common.request.UserRequest;
import org.Akhil.login.request.LoginRequest;

public interface AuthService {
    User createUser(UserRequest user);
    String login(LoginRequest request);
}
