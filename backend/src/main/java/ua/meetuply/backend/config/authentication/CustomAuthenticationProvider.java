package ua.meetuply.backend.config.authentication;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.service.AppUserService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Setter
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private AppUserService appUserService;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        AppUser user = appUserService.getUserByEmail(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            if(user.isDeactivated())
                throw new BadCredentialsException("User is deactivated");
            else if(!user.isRegistration_confirmed())
                throw new BadCredentialsException("Email is not confirmed");
            else
                return new UsernamePasswordAuthenticationToken(username, password, appUserService.loadUserByUsername(username).getAuthorities());
        } else {
            throw new BadCredentialsException("Authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}