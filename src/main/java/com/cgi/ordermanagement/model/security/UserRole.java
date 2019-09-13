package com.cgi.ordermanagement.model.security;

import com.cgi.ordermanagement.model.audit.AuditBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;


@Data
@Entity
@Table(name = "USER_ROLE", uniqueConstraints = @UniqueConstraint(columnNames = {"USER_ID", "ROLE_ID"}))
@ToString(exclude = {"role", "user"})
@EqualsAndHashCode(exclude = {"role", "user"}, callSuper = false)
public class UserRole extends AuditBase {

    public UserRole() {
    }

    public UserRole(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRole(Long userId, Long roleId, Long createdBy) {
        this.userId = userId;
        this.roleId = roleId;
        this.setCreatedBy(createdBy);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_seq")
    @SequenceGenerator(name = "user_role_seq", sequenceName = "user_role_seq", allocationSize = 1, initialValue = 2)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "FK_USERROLE_USER"), insertable = false, updatable = false)
    private User user;

    @Column(name = "USER_ID")
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "ROLE_ID", foreignKey = @ForeignKey(name = "FK_USERROLE_ROLE"), insertable = false, updatable = false)
    private Role role;

    @Column(name = "ROLE_ID")
    private Long roleId;


}