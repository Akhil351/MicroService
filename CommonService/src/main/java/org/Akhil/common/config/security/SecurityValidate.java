package org.Akhil.common.config.security;

import org.Akhil.common.config.userDetails.CustomerDetails;
import org.Akhil.common.model.UserRequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityValidate {
    public boolean isAdmin(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return  authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("Admin"));
    }
    public UserRequestContext userRequestContext(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        CustomerDetails userDetails=(CustomerDetails) authentication.getPrincipal();
        UserRequestContext context=new UserRequestContext();
        context.setUserEmail(userDetails.getUsername());
        context.setUserId(userDetails.getId());
        context.setAuthorities(authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList());
        return context;
    }
}
