package com.cgi.ordermanagement.model.order;

import com.cgi.ordermanagement.model.audit.AuditBase;
import com.cgi.ordermanagement.model.enums.order.OrderStatus;
import com.cgi.ordermanagement.model.enums.order.OrderType;
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
@Table(name = "ORDERS")
public class Order extends AuditBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "QUANTITY", precision = 2, scale = 2, nullable = false)
    private Double quantity;

    @Column(name = "PRICE", precision = 2, scale = 2, nullable = false)
    private Double price;

    @Column(name = "ORDER_TYPE", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private OrderType orderType;

    @Column(name = "ORDER_STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus = OrderStatus.WAITING;

    @Column(name = "USER_ID")
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "FK_ORDER_USER"), insertable = false, updatable = false)
    private User user;



}
