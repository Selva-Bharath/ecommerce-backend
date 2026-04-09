package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private float price;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String images;

    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
}