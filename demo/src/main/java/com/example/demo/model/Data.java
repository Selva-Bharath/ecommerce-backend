package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private float price;
    private int quantity;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String images;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}