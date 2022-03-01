package edu.aleksandrTreskov.mms.entity;

import edu.aleksandrTreskov.mms.common.OrderStatus;
import edu.aleksandrTreskov.mms.common.PaymentMethod;
import edu.aleksandrTreskov.mms.common.PaymentStatus;
import edu.aleksandrTreskov.mms.common.ShipmentMethod;

import java.util.List;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;


@Table(name = "PURCHASE")
@Entity
@Data
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;
    @Column(name = "shipment_method", nullable = false)
    private ShipmentMethod shipmentMethod;
    @ManyToMany
    @JoinTable(name = "PURCHASE_ITEM",
            joinColumns = @JoinColumn(name = "purchase_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "item_id", nullable = false))
    private List<Item> items;
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;
    @Column(name = "purchase_status", nullable = false)
    private OrderStatus orderStatus;
    @Column(name = "is_deleted")
    private boolean isDeleted = false;


}
