package ua.meetuply.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.service.AppUserService;

import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    AppUserService appUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String username = authentication.getName();
        String password = passwordEncoder.encode(authentication.getCredentials().toString());

        AppUser user = appUserService.getUserByEmail(username);

        if (user == null) {
            throw new BadCredentialsException("There is no user with such email");
        } else if(user.isDeactivated()) {
            throw new BadCredentialsException("User is deactivated"); //TODO exception
        } else if(!user.isRegistration_confirmed()) {
            throw new BadCredentialsException("Email is not confirmed");
        } else if (user.getPassword().equals(password)) {
            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
        } else {
            throw new BadCredentialsException("Authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}