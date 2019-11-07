package ua.meetuply.backend.config.authentication;

import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.service.AppUserService;

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