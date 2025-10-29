package com.fhcs.clothing_store.exception;

public class AddressValidationException extends RuntimeException {
    public AddressValidationException(String message) {
        super(message);
    }
}
