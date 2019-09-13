package com.cgi.ordermanagement.model.audit;


import com.cgi.ordermanagement.model.enums.DataStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditBase extends OnlyCreateBase {

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @LastModifiedBy
    @Column(name = "UPDATE_USER")
    private Long lastModifiedBy;

    @Column(name = "DATA_STATUS")
    @Enumerated(EnumType.ORDINAL)
    private DataStatus dataStatus = DataStatus.ACTIVE;

    @NotNull
    @Version
    @Column(name = "VERSION")
    private Long version;

}