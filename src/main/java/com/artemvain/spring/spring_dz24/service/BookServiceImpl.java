package com.artemvain.spring.spring_dz24.service;

import com.artemvain.spring.spring_dz24.dao.BookRepository;
import com.artemvain.spring.spring_dz24.entity.Author;
import com.artemvain.spring.spring_dz24.entity.Book;
import com.artemvain.spring.spring_dz24.entity.BookWarehouse;
import com.artemvain.spring.spring_dz24.entity.Shopper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;


    public List<Book> getAllBooks() {
        return  bookRepository.findAll();
    }

    @Override
    public void saveBooks(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Book getBook(int id) {
        Book book = null;
        Optional<Book> optional = bookRepository.findById(id);
        if (optional.isPresent()) {
            book = optional.get();
        }
        return book;
    }

    @Override
    public void deleteBook(int id) {

        bookRepository.deleteById(id);
    }

//@Lock(LockModeType.WRITE)
    public void addOrder(int id, Shopper shopper, int count) {
        BookWarehouse bookWarehouse = new BookWarehouse(1, new Book("Onegin", new Author("Alisa"), 1865, 100, 800));
        System.out.println("1111111  " + bookRepository.findById(id));
        if (bookWarehouse.getNumberOfBooks() > 0) {
            bookRepository.deleteById(id);
        } else {
            System.out.println("the book was bought by another buyer");
        }


    }
//
//    public void deleteBooks() {
//
//        bookRepository.deleteAll();
//    }

}

