package org.Akhil.common.config.security;

import org.Akhil.common.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityValidate {
    public boolean isAdmin(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return  authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.getRole()));
    }
}
