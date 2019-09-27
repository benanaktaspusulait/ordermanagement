package com.cgi.ordermanagement.model.project;

import com.cgi.ordermanagement.model.audit.AuditBase;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@DynamicUpdate
@Table(name = "PROJECTS")
public class Project extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "DEALER_ID", foreignKey = @ForeignKey(name = "FK_PROJECT_DEALER"), insertable = false, updatable = false)
    private Dealer dealer;

    @Column(name = "DEALER_ID")
    private Long dealerId;
}
