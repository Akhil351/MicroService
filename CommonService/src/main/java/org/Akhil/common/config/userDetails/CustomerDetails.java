package org.Akhil.common.config.userDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.Akhil.common.enums.Role;
import org.Akhil.common.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetails implements UserDetails {
    private String id;
    private String email;
    private String password;
    private Collection<SimpleGrantedAuthority> authorities;

    public static CustomerDetails buildUserDetails(User user,List<Integer> roles){
         List<SimpleGrantedAuthority> authorities= roles
                 .stream()
                 .map(Role::role)
                 .map(SimpleGrantedAuthority::new)
                 .toList();
         return new CustomerDetails(user.getId(),user.getEmail(), user.getPassword(), authorities);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
