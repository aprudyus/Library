package com.library.dao;

import com.library.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Pavlo-Anton on 22-Jul-17.
 */
@Repository
public interface BookDao {
    public void add(Book book);

    public List<Book> findAll();

    public List<Book> findAllByName(String name);

    public Book update(Book book);

    public void delete(Book book);
}
