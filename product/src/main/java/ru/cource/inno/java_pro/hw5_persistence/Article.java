package ru.cource.inno.java_pro.hw5_persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id")
    private Author author;

    public Article() {}
    public Article(String title) { this.title = title; }

    @Override
    public String toString() {
        return "Article{id=" + id + ", title='" + title + "'}";
    }
}
