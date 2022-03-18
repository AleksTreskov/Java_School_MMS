package edu.aleksandrTreskov.mms.entity;

import edu.aleksandrTreskov.mms.common.PurchaseStatus;
import edu.aleksandrTreskov.mms.common.PaymentMethod;
import edu.aleksandrTreskov.mms.common.PaymentStatus;
import edu.aleksandrTreskov.mms.common.ShipmentMethod;
import lombok.Data;
import lombok.ToString;
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
import java.time.LocalDateTime;
import java.util.List;


@Table(name = "PURCHASE")
@Entity
@Data
@ToString
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "client_id" )
    private Client client;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @Column(name = "total_price")
    private int totalPrice;
    @Column(name = "date_created")
    private LocalDateTime dateCreated;
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;
    @Column(name = "shipment_method")
    private ShipmentMethod shipmentMethod;
    @ManyToMany
    @JoinTable(name = "PURCHASE_ITEM",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items;
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;
    @Column(name = "purchase_status")
    private PurchaseStatus purchaseStatus;
    @Column(name = "is_deleted")
    private boolean isDeleted = false;


}
