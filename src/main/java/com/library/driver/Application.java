package com.library.driver;

import com.library.dao.BookDao;
import com.library.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Pavlo-Anton on 23-Jul-17.
 */
public class Application {
    @Autowired
    private BookDao bookDao;

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private BufferedReader getReader() {
        return reader;
    }

    public void run() throws IOException {
        System.out.println("Hello! Welcome to our library! You can do several things here:");
        System.out.println("1. add a book to the library;");
        System.out.println("2. remove book from library");
        System.out.println("3. change book's name;");
        System.out.println("4. get a list of all library's books;");
        System.out.println("5. quit the application.");
        BufferedReader reader = getReader();
        loop:
        while (true) {
            int choice;
            try {
                System.out.println("Please enter a number of command. For example, 2");
                choice = Integer.parseInt(reader.readLine());
            } catch (IOException | NumberFormatException e) {
                System.out.println("Invalid choice. Try again");
                continue;
            }
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    remove();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    printAllBooks();
                    break;
                case 5:
                    System.out.println("Thank for using our library! Goodbye!");
                    break loop;
                default:
                    System.out.println("Invalid choice. Try again");
                    break;
            }
        }
        closeReader();
    }

    private void addBook() throws IOException {
        System.out.println("Enter author of a book:");
        String author = getNotEmptyString();
        System.out.println("Enter name of a book:");
        String name = getNotEmptyString();
        Book book = new Book();
        book.setAuthorName(author);
        book.setName(name);
        bookDao.add(book);
        System.out.println("book " + book + " was added");
    }

    private void remove() throws IOException {
        System.out.println("Enter name of a book you want to remove:");
        String name = getCorrectBookName();
        Book book = getBookForUpdateOrRemove(name);
        bookDao.delete(book);
        System.out.println("book " + book + " was deleted");
    }

    private void update() throws IOException {
        System.out.println("Enter name of a book you want to edit:");
        String name = getCorrectBookName();
        Book book = getBookForUpdateOrRemove(name);
        System.out.println("Enter a new name of a book:");
        String newName = getNotEmptyString();
        book.setName(newName);
        bookDao.update(book);
        System.out.println("book " + book + " was edited");
    }

    private void printAllBooks() {
        List<Book> books = bookDao.findAll();
        if (books.isEmpty()) {
            System.out.println("Unfortunately our library doesn't have any books yet");
            return;
        }
        System.out.println("Here is a list of books currently available in our library:");
        books.forEach(System.out::println);
    }

    private String getNotEmptyString() throws IOException {
        BufferedReader reader = getReader();
        String name;
        while (true) {
            name = reader.readLine();
            if (name.isEmpty()) {
                System.out.println("Neither name of a book nor author's name could be empty! Please, try again.");
                continue;
            }
            break;
        }
        return name;
    }

    private String getCorrectBookName() throws IOException {
        BufferedReader reader = getReader();
        String name;
        while (true) {
            name = reader.readLine();
            if (name.isEmpty() || !areThereBooks(name)) {
                System.out.println("There is no a book with such name in our library. Please, try again.");
                continue;
            }
            break;
        }
        return name;
    }

    private boolean areThereBooks(String name) {
        return !bookDao.findAllByName(name).isEmpty();
    }

    private Book getBookForUpdateOrRemove(String bookName) {
        List<Book> books = bookDao.findAllByName(bookName);
        if (books.size() > 1) {
            System.out.println("We have few books with such name please choose one by typing a number of book:");
            for (int i = 0; i < books.size(); i++) {
                System.out.println((i + 1) + ". " + books.get(i));
            }
            int number = getNumberOfBookToUpdateOrRemove(books.size());
            return books.get(number - 1);
        } else if (books.size() == 1) return books.get(0);
        return null;
    }

    private int getNumberOfBookToUpdateOrRemove(int bookListSize) {
        BufferedReader reader = getReader();
        int choice;
        while (true) {
            try {
                System.out.println("Enter number of a book:");
                choice = Integer.parseInt(reader.readLine());
            } catch (IOException | NumberFormatException e) {
                System.out.println("Invalid choice. Try again");
                continue;
            }
            if (choice < 1 || choice > bookListSize) {
                System.out.println("Number of a book can't be less than 1" +
                        " or bigger than number of books in library with such name. Try again");
                continue;
            }
            break;
        }
        return choice;
    }

    private void closeReader() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }
}
