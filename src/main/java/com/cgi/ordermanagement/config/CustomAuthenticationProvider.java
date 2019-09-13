package com.cgi.ordermanagement.config;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @NotNull}))
public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(@NonNull final Authentication authentication) throws AuthenticationException {
        try {
            final Object details = authentication.getDetails();
            WebAuthenticationDetails webAuthenticationDetails = null;
            if (details != null) {
                if (details instanceof WebAuthenticationDetails) {
                    webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
                }
            }
            if (details == null) {
                throw new BadCredentialsException("Invalid Credentials");
            }

            final String name = authentication.getName();
            final String password = authentication.getCredentials().toString();
            if (name.equals("admin") && password.equals("system")) {
                final ArrayList<GrantedAuthority> grantedAuths = new ArrayList<>();
                grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
                final UserDetails principal = new User(name, password, grantedAuths);
                final Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
                return auth;
            } else {
                return null;
            }
        } catch (ClassCastException e) {
            throw new BadCredentialsException("Invalid Credentials");
        } catch (Exception e) {

        }
        return null;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

}