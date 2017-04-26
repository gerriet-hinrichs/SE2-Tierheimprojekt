package de.stuff42.se2tierheimprojekt.testSuit;


import org.springframework.stereotype.Service;

@Service
public class GreeterService {

    public String sayHello(String caller) {
        return String.format("Hello World, %s", caller);
    }

}