package edu.aleksandrTreskov.mms.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Table(name = "ITEM")
@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Please provide the name")
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "imgName")
    private String imgName;
    @NotEmpty(message = "Please provide the category ")
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "description", nullable = false)
    private String description;
    @NotEmpty(message = "Please provide the brand")
    @Column(name = "brand", nullable = false)
    private String brand;
    @NotEmpty(message = "Please provide the model")
    @Column(name = "model", nullable = false)
    private String model;
    @NotNull(message = "Please provide the weight")
    @Column(name = "weight", nullable = false)
    private double weight;
    @NotNull(message = "Please provide the price")
    @Column(name = "price", nullable = false)
    private double price;
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "is_deleted")
    private boolean isDeleted = false;
    @Column(name = "sold")
    private int sold;

    public Item() {
    }

    public Item(long id, String name, String category, String description, String brand, String model, double weight, double price, int quantity, boolean isDeleted, int sold) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.brand = brand;
        this.model = model;
        this.weight = weight;
        this.price = price;
        this.quantity = quantity;
        this.isDeleted = isDeleted;
        this.sold = sold;
    }
}
