package com.example.bookare.entities;

import lombok.Getter;
import lombok.Setter;
import reactor.util.annotation.Nullable;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Address{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String districtName;
    @Column(nullable = false)
    private String quartersName;
    @Column(nullable = false)
    private String regionsName;

    @Column(nullable = false)
    private String districtNameKrill;
    @Column(nullable = false)
    private String quartersNameKrill;
    @Column(nullable = false)
    private String regionsNameKrill;
}
