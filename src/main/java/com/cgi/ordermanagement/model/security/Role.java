package com.cgi.ordermanagement.model.security;

import com.cgi.ordermanagement.model.enums.RoleName;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ROLES")
public class Role {

    @Id
    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    private RoleName name;

    public Role() {

    }


}
