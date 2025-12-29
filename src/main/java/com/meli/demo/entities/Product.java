package com.meli.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "products")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String name;

    @Column(length = 255)
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(precision = 19, scale = 2)
    private BigDecimal price;

    /*Eu não criei nenhuma lógica no código para popular esse campo, as avaliações vão ser colocadas na hora de criar o produto,
    porém a precisão é 2 e a escala é 1 para permitir valores como 4.5, 3.0 e assim simular as avaliações no Mercado Livre.
    Considere que seriam a média das avaliações dos usuários.*/
    @Column(precision = 2, scale = 1)
    private BigDecimal rating;

    @Column(columnDefinition = "VARCHAR(1000)")
    private String specification;

    @Column(columnDefinition ="BOOLEAN")
    private Boolean availability;


}
