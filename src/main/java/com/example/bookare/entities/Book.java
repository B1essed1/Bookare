package com.example.bookare.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String authors;
    @Column(columnDefinition = "text")
    private String description;
    private int pages;
    private String language;
    private int quantity;
    private int type;
    private float price;

    @Builder.Default
    private boolean active = true;

    @OneToMany
    private List<Genre> genres;

    @ManyToOne
    @JsonIgnore
    private Users users;

}

