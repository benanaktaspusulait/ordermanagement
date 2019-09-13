package com.cgi.ordermanagement.model.system;

import com.cgi.ordermanagement.model.audit.OnlyCreateBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "SYSTEM_ERRORS")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class SystemError extends OnlyCreateBase implements Serializable {

    private static final long serialVersionUID = 868259880463348196L;

    @Id
    @SequenceGenerator(name = "system_errors_seq", sequenceName = "system_errors_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_errors_seq")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ERROR_CODE")
    private Integer errorCode;

    @Column(name = "USERNAME")
    private String username;

    @Lob
    @Column(name = "ERROR_DESCRIPTION",columnDefinition = "NCLOB")
    private String errorDescription;
}