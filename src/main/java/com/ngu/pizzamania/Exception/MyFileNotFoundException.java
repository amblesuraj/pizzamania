package com.ngu.pizzamania.Exception;

public class MyFileNotFoundException extends RuntimeException {
    public MyFileNotFoundException(String s) {
    super(s);
    }
    public MyFileNotFoundException(String s,Throwable ex) {
        super(s,ex);
    }
}
