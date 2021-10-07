package com.cnstar.interfacetestmock.exception;

public class MockErrorResponseException extends Exception {
    private static final long serialVersionUID = 6373895005184658927L;

    public MockErrorResponseException(String message) {
        super(message);
    }
}
