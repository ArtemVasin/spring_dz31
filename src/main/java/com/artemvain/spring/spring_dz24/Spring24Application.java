package com.artemvain.spring.spring_dz24;

import com.artemvain.spring.spring_dz24.entity.Book;
import com.artemvain.spring.spring_dz24.entity.BookWarehouse;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Spring24Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Spring24Application.class, args);

    }
}

















































//       Author author = new Author("Puskin");
//    Book book = new Book("Onegin", new Author("dfg"), 1865, 100, 800);
//   book.getName();
//        BookWarehouse bookWarehouse = new BookWarehouse(2, book);
//        Shopper shopper = new Shopper("Artem", 1987);
//       Shopper shopper1 = new Shopper("Maksim", 1985);
//        OrderBook orderBook = new OrderBook(shopper, 800);
//        OrderDetails orderDetails = new OrderDetails(orderBook, 2, 1600, book);

// context.getBean(BookStoreServiceImpl.class).deleteBookWarehouse(1);
//  context.getBean(BookStoreServiceImpl.class).saveBookWarehouse(new BookWarehouse(2,new Book("Idiot", new Author("Dostoevskii"), 1856,200,800)));
//  context.getBean(BookStoreServiceImpl.class).saveBookWarehouse(new BookWarehouse(3,new Book("Onegin", new Author("Pushkin"), 1859,150,900)));
//    context.getBean(OrderServiceImpl.class).saveOrderBook(new OrderBook(new Shopper("Artem", 1987), 100));
//   System.out.println(context.getBean(BookStoreServiceImpl.class).getAllBookWarehouse());

//  System.out.println(context.getBean(BookServiceImpl.class).getBook(23).getBookWarehouse());
// System.out.println(context.getBean(BookServiceImpl.class).getAllBooks());
// context.getBean(OrderServiceImpl.class).getAllOrderBook();
//  context.getBean(BookServiceImpl.class).saveBooks(new Book("Idiot", new Author("Dostoevskii"), 1964,200,800));