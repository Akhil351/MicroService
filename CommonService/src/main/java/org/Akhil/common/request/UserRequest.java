package org.Akhil.common.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UserRequest {
    @NotBlank(message = "name is required")
    private String firstName;
    private String lastName;
    @NotBlank(message = "email is required")
    @Email(message = "The email should be in a valid format, like example@gmail.com")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min=5,max = 18)
    private String password;
    @NotBlank(message = "phoneNumber is required")
    @Size(min = 10,max = 15)
    private String phoneNumber;
    private List<String> role;
}
