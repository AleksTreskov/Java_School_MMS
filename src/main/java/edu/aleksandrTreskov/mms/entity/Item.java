package edu.aleksandrTreskov.mms.entity;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.ElementCollection;



@Table(name = "ITEM")
@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "category",nullable = false)
    private String category;
    @ElementCollection
    private Map<String, String> parameters = new HashMap<>();
    @Column(name = "price",nullable = false)
    private int price;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "is_deleted")
    private boolean isDeleted = false;


}
