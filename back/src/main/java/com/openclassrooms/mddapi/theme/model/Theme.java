package com.openclassrooms.mddapi.theme.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.openclassrooms.mddapi.article.model.Article;
import com.openclassrooms.mddapi.user.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Themes")
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToMany(mappedBy = "themes")
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "theme")
    private Set<Article> articles = new HashSet<>();
}
