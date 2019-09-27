package com.cgi.ordermanagement.model.order;


import com.cgi.ordermanagement.model.audit.AuditBase;
import com.cgi.ordermanagement.model.enums.order.OrderStatus;
import com.cgi.ordermanagement.model.enums.order.OrderType;
import com.cgi.ordermanagement.model.project.Dealer;
import com.cgi.ordermanagement.model.security.User;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@DynamicUpdate
@Table(name = "PRICE_LIST_ITEM")
public class PriceListItem extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "PRICE", scale = 2, precision = 2)
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "ITEM_ID", foreignKey = @ForeignKey(name = "FK_ITEM_PRICE_LIST_ITEM"), insertable = false, updatable = false)
    private Item item;

    @Column(name = "ITEM_ID")
    private Long itemId;

}

