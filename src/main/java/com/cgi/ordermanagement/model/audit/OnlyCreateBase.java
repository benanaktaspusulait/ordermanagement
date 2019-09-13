package com.cgi.ordermanagement.model.audit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class OnlyCreateBase extends ModelBase {


    @CreatedDate
    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;

    @NotNull
    @CreatedBy
    @Column(name = "CREATE_USER")
    private Long createdBy = 1L;

}