package com.magic3.magic3.security;

import com.magic3.magic3.model.Usera;
import com.magic3.magic3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usera user = repo.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User bulunamadı.");
        }
        return new CustomUserDetails(user);
    }
}
