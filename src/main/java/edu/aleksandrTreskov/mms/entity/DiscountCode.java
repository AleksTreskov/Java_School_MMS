package edu.aleksandrTreskov.mms.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "DISCOUNT_CODE")
public class DiscountCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "required_sum_for_activation")
    private int requiredSumForActivation;
    @Column(name = "percent_discount")
    private int percentDiscount;
    @Column(name = "description")
    private String description;
    @Column(name = "is_deleted")
    private boolean isDeleted;
}
