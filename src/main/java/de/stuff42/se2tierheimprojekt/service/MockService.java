package de.stuff42.se2tierheimprojekt.service;


import org.springframework.stereotype.Service;

@Service
public class MockService {

    public String sayHello(String caller) {
        return String.format("Hello World, %s", caller);
    }

}