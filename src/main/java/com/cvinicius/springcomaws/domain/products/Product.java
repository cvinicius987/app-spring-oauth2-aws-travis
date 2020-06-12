package com.cvinicius.springcomaws.domain.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="tbl_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length=50)
    private String title;

    @Column(length=50)
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    public Product(String title, String description, Category category){
        this.title = title;
        this.description = description;
        this.category = category;
    }
}
