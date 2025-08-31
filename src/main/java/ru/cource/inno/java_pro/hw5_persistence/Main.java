package ru.cource.inno.java_pro.hw5_persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("demoPU");
        EntityManager em = emf.createEntityManager();

        Long authorId;

        // 1) Каскадное создание Автора со статьями.
        em.getTransaction().begin();
        Author author = new Author("Иван Иванов");
        author.addArticle(new Article("Persistence_1"));
        author.addArticle(new Article("Persistence_2"));
        author.addArticle(new Article("Persistence_3"));
        em.persist(author); // каскадное сохранение
        em.getTransaction().commit();
        authorId = author.getId();
        System.out.println("[STEP1] Saved author id=" + authorId + ", articles=" + author.getArticles().size());

        // 2) Очистка контекста и получение из БД свежего автора.
        em.clear();
        Author fresh = em.find(Author.class, authorId);
        System.out.println("[STEP2] Fresh from DB: " + fresh);

        // 3) Демонстрация ленивой загрузки.
        System.out.println("[STEP3] Author name (no join fetch): " + fresh.getName());
        int lazySize = fresh.getArticles().size(); // побуждение к ленивой загрузке.
        System.out.println("[STEP3] Articles count (after lazy load): " + lazySize);

        // 4) orphanRemoval: удаление одной статьи внутри транзакции.
        em.getTransaction().begin();
        Article toRemove = fresh.getArticles().get(0);
        fresh.removeArticle(toRemove);
        em.getTransaction().commit();
        System.out.println("[STEP4] Removed one article via orphanRemoval. Remaining=" + fresh.getArticles().size());

        // 5) Cascade delete: удаление автора со связанными статьями.
        em.getTransaction().begin();
        Author forDelete = em.find(Author.class, authorId);
        em.remove(forDelete);
        em.getTransaction().commit();
        System.out.println("[STEP5] Author and related articles deleted via cascade.");

        em.close();
        emf.close();
    }
}
