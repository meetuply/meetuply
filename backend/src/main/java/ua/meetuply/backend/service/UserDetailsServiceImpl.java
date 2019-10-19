package ua.meetuply.backend.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        AppUser appUser = appUserDAO.findAppUserByEmail(email);
        if (appUser == null) throw new UsernameNotFoundException(email);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        User user;
        if(appUser.getRole().getRoleName().equals("admin"))
            user = new User(appUser.getEmail(), appUser.getPassword(), grantedAuthorities);
        else user = new User(appUser.getEmail(), appUser.getPassword(), new HashSet<>());
        return user;
    }
}