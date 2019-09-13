package com.cgi.ordermanagement.model.security;

import com.cgi.ordermanagement.model.audit.AuditBase;
import com.cgi.ordermanagement.util.AppConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "DATA_STATUS"})})
@EqualsAndHashCode(callSuper = false, exclude = "userRoleList")
public class User extends AuditBase {

    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;

    @Column(name = "USER_TYPE")
    @Enumerated(EnumType.ORDINAL)
    private UserType userType;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "MOBILE")
    private String mobile;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<UserRole> userRoleList = new ArrayList<>();

    public User() {

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }


}