package com.cgi.ordermanagement.model;


import com.cgi.ordermanagement.model.audit.AuditBase;
import com.cgi.ordermanagement.model.enums.Language;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "SCREEN_MESSAGE", uniqueConstraints = {@UniqueConstraint(columnNames = {"CODE", "LANGUAGE"}, name = "SCREEN_MESSAGE_UC")})
@ToString
@EqualsAndHashCode(callSuper = false)
public class ScreenMessage extends AuditBase {

    @Id
    @SequenceGenerator(name = "screen_message_seq", sequenceName = "screen_message_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "screen_message_seq")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "VALUE")
    private String value;

    @Column(name = "LANGUAGE")
    @Enumerated(EnumType.ORDINAL)
    private Language language;


}
