package com.pdt.dynatrace.exception;

public class TooMuchQuantityException extends RuntimeException{
    public TooMuchQuantityException(int count) {
        super("Quantity is over 255 - count: "+ count);
    }
}
