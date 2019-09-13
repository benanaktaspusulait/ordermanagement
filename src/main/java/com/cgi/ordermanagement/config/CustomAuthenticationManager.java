package com.cgi.ordermanagement.config;

import com.cgi.ordermanagement.exception.AppException;
import com.cgi.ordermanagement.exception.NotAuthenticationException;
import com.cgi.ordermanagement.model.dto.system.ErrorDTO;
import com.cgi.ordermanagement.model.enums.DataStatus;
import com.cgi.ordermanagement.model.security.User;
import com.cgi.ordermanagement.repository.security.UserRepository;
import com.cgi.ordermanagement.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(@NonNull final Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        try {
            User user = userRepository.findOneByEmailIgnoreCase(username);
            if (user == null) throw new BadCredentialsException("1000");

            if(DataStatus.DELETED.equals(user.getDataStatus())){
                throw new AppException(ErrorDTO.ResponseCodes.USER_PASSIVE);
            }

            if (!passwordEncoder.matches(password, user.getPassword())) throw new BadCredentialsException("1000");

            UserPrincipal userPrincipal = new UserPrincipal(user.getId(), user.getEmail(), user.getPassword(),  user.getUserType(), user.getUserRoleList().stream().map(x -> new SimpleGrantedAuthority(x.getRole().getName().name())).collect(Collectors.toList()));

            return new UsernamePasswordAuthenticationToken(userPrincipal, password, user.getUserRoleList().stream().map(x -> new SimpleGrantedAuthority(x.getRole().getName().name())).collect(Collectors.toList()));
        } catch (AccountStatusException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        throw new NotAuthenticationException("Username and password does not match");

    }

}