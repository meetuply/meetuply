package ua.meetuply.backend.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.meetuply.backend.dao.AppUserDAO;
import ua.meetuply.backend.model.AppUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired @Lazy
    private AppUserDAO appUserDAO;

    @Override

    public UserDetails loadUserByUsername(String email) {
        AppUser user = appUserDAO.findAppUserByEmail(email);
        if (user == null) throw new UsernameNotFoundException(email);
        return new User(user.getEmail(), user.getPassword(), new HashSet<>());
    }
}