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

    // ✅ NEW FIELD
    private String weight;   // 100g, 250g, 1kg

    // ✅ NEW FIELD
    private String category; // Powder, Rice, Oil

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String images;

    // ===== GETTERS & SETTERS =====

    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
}