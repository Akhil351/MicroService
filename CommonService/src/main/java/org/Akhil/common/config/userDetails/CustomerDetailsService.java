package org.Akhil.common.config.userDetails;

import org.Akhil.common.model.Roles;
import org.Akhil.common.model.User;
import org.Akhil.common.repo.RolesRepo;
import org.Akhil.common.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerDetailsService implements UserDetailsService {
    @Autowired
    private  UserRepo userRepo;
    @Autowired
    private RolesRepo rolesRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        List<Integer> roles=rolesRepo.findByUserId(user.getId()).stream()
                           .map(Roles::getRole).toList();
        return CustomerDetails.buildUserDetails(user,roles);
    }
}
