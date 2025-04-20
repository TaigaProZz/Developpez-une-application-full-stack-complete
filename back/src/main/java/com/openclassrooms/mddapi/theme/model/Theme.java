package com.openclassrooms.mddapi.theme.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Themes")
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;
}
