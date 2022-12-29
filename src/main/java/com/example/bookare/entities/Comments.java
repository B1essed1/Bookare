package com.example.bookare.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    private Users user;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    private Users commenter;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
