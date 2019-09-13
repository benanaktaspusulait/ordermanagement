package com.cgi.ordermanagement.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.cgi.ordermanagement.model.security.User;
import com.cgi.ordermanagement.model.security.UserType;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPrincipal implements UserDetails {

    private Long id;
    private @JsonIgnore
    String email;
    private @JsonIgnore
    String password;
     private UserType userType;
    private String tradingName;
    private String firstName;
    private String lastName;
    private Collection<? extends GrantedAuthority> authorities;



    public UserPrincipal(Long id, String email, String password,
                         UserType userType, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
         this.authorities = authorities;
        this.userType = userType;
    }

    public static UserPrincipal create(User user) {

        List<GrantedAuthority> authorities = user.getUserRoleList().stream().map(userRole ->
                new SimpleGrantedAuthority(userRole.getRole().getName().name())
        ).collect(Collectors.toList());

        UserPrincipal userPrincipal = new UserPrincipal(user.getId(), user.getEmail(), user.getPassword(), user.getUserType(), authorities);


         return userPrincipal;
    }


    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
