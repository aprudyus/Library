package com.library.dao.impl;

import com.library.dao.BookDao;
import com.library.entity.Book;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Pavlo-Anton on 22-Jul-17.
 */
public class BookDaoImpl implements BookDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void add(Book book) {
        entityManager.persist(book);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Book> findAll() {
        return entityManager.createQuery("SELECT book FROM Book book ORDER BY book.name ASC", Book.class)
                .getResultList();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Book> findAllByName(String name) {
        return entityManager.createQuery("SELECT book FROM Book book WHERE book.name = :name", Book.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Book update(Book book) {
        return entityManager.merge(book);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Book book) {
        Book bookToBeDeleted = entityManager.getReference(Book.class, book.getId());
        entityManager.remove(bookToBeDeleted);
    }
}
