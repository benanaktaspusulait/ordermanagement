package com.cgi.ordermanagement.model.order;


import com.cgi.ordermanagement.model.audit.AuditBase;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@DynamicUpdate
@Table(name = "PRICE_LIST")
public class PriceList extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", scale = 2, precision = 2)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "ITEM_ID", foreignKey = @ForeignKey(name = "FK_PRICE_LIST_ITEM"), insertable = false, updatable = false)
    private PriceListItem item;

    @Column(name = "ITEM_ID")
    private Long itemId;




}

