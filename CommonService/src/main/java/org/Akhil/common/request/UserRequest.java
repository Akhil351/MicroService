package org.Akhil.common.request;

import lombok.Data;

import java.util.List;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private List<String> role;
}
