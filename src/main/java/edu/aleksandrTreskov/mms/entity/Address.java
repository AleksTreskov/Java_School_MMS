package edu.aleksandrTreskov.mms.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Table(name = "ADDRESS")
@Entity
@Data
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Please provide a country")
    @Column(name = "country", nullable = false)
    private String country;

    @NotEmpty(message = "Please provide a city")
    @Column(name = "city", nullable = false)
    private String city;

    @NotEmpty(message = "Please provide a postcode")
    @Column(name = "postcode", nullable = false)
    private String postcode;

    @NotEmpty(message = "Please provide a street")
    @Column(name = "street", nullable = false)
    private String street;

    @NotEmpty(message = "Please provide a building")
    @Column(name = "building", nullable = false)
    private String building;

    @NotNull(message = "Please provide a flat")
    @Column(name = "flat")
    private int flat;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String index) {
        this.postcode = index;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String house) {
        this.building = house;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
