package com.learn.Ecom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private String category;
    @Column(name = "stock_quantity")
    private Integer stockQuantity;
    @Column(name = "release_date")
    private Date releaseDate;
    @Column(name = "product_available")
    private Boolean productAvailable;
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;
}
