package ru.ya.kolemik.howtoignite;

import javax.inject.Named;

@Named("hello")
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return String.format("Hello, %s!", name);
    }
}
