package com.artemvain.spring.spring_dz24.controllers;


import com.artemvain.spring.spring_dz24.entity.*;
import com.artemvain.spring.spring_dz24.security.AuthRequest;
import com.artemvain.spring.spring_dz24.security.AuthResponse;
import com.artemvain.spring.spring_dz24.security.JWTUtil;
import com.artemvain.spring.spring_dz24.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.server.ResponseStatusException;
//import ru.sysout.jwt.security.AuthRequest;
//import ru.sysout.jwt.security.AuthResponse;
//import ru.sysout.jwt.security.JWTUtil;

@RestController
@Slf4j
@RequestMapping("/api")
public class UserController {

    @Autowired
    private final UserServiceImpl userService;
    //    @Autowired
//    private final BookStoreServiceImpl bookStoreService;
    @Autowired
    private final AuthorServiceImpl authorService;
    @Autowired
    private final OrderServiceImpl orderService;
    @Autowired
    private final BookServiceImpl bookService;


    public UserController(UserServiceImpl userService,
                          AuthorServiceImpl authorService, OrderServiceImpl orderService,
                          BookServiceImpl bookService) {
        this.userService = userService;
//        this.bookStoreService = bookStoreService;
        this.authorService = authorService;
        this.orderService = orderService;
        this.bookService = bookService;
    }

    @GetMapping({"/welcome"})
    public String welcome() {
        String w = "welcome!";
        return w;
    }




    @GetMapping({"/books"})
    public Flux<Book> showAllBooks() {
        List<Book> allBooks = this.bookService.getAllBooks();
        Flux<Book> fluxFromList = Flux.fromIterable(allBooks);
        return fluxFromList;
    }

    @GetMapping({"/books/{id}"})
    public Mono<ResponseEntity<Book>> getBook(@PathVariable int id) {
        Book book = this.bookService.getBook(id);
        return book;
    }

    @DeleteMapping({"/books/{id}"})
    @Secured("ROLE_ADMIN")
    public void deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
    }

    @PostMapping({"/books"})
    @Secured("ROLE_ADMIN")
    public void saveBook(@RequestBody Book book) {
        bookService.saveBooks(book);
    }

    @DeleteMapping({"/authors/{id}"})
    @Secured("ROLE_ADMIN")
    public void deleteAuthor(@PathVariable int id) {
        authorService.deleteAuthor(id);
    }

    @GetMapping({"/authors"})
    public List<Author> showAllAuthors() {
        List<Author> allAuthors = authorService.getAllAuthor();
        return allAuthors;
    }

    @PostMapping({"/authors"})
    @Secured("ROLE_ADMIN")
    public void saveAuthor(@RequestBody Author author) {
        authorService.saveAuthor(author);
    }

    @GetMapping({"/user"})
    public List<User> showAllClients() {
        List<User> allClients = userService.getAllUsers();
        return allClients;
    }

    @PutMapping({"/user"})
    public User updateUser(@RequestBody User user) {
        this.userService.saveUser(user);
        return user;
    }

    @Autowired
    private JmsTemplate jmsTemplate;

    @GetMapping(value = "/send/{message}", produces = "text/html")
    public String sendMessage(@PathVariable String message) {
        jmsTemplate.convertAndSend("superqueue", message);
        return "done";
    }

    @JmsListener(destination="superqueue")
    public  void processMessage(String message) {
        log.info("Order:  " + message);
    }

    @GetMapping({"/orders"})
    public List<OrderBook> showAllOrder() {
        List<OrderBook> allOrder = orderService.getAllOrderBook();
        return allOrder;
    }


    private AuthenticationManager authenticationManager;
    private JWTUtil jwtTokenUtil;


    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
            System.out.println(authentication);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "?????? ?????? ???????????? ??????????????????????", e);
        }
        String jwt = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());

        return new AuthResponse(jwt);
    }

}
