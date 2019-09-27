package com.cgi.ordermanagement.model.project;

import com.cgi.ordermanagement.model.audit.AuditBase;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@DynamicUpdate
@Table(name = "DEALERS")
public class Dealer extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CODE")
    private String code;

    @Column(name = "ADDRESS")
    private String address;
}
