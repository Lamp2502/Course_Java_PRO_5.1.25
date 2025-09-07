package ru.cource.inno.java_pro.hw5_persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Article> articles = new ArrayList<>();

    public Author() {}
    public Author(String name) { this.name = name; }

    // Helper methods to keep both sides in sync
    public void addArticle(Article a) {
        articles.add(a);
        a.setAuthor(this);
    }

    public void removeArticle(Article a) {
        articles.remove(a);
        a.setAuthor(null);
    }

    @Override
    public String toString() {
        return "Author{id=" + id + ", name='" + name + "'}";
    }
}
