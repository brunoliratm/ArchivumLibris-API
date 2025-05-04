package com.archivumlibris.book.exception;

public class InvalidBookDataException extends RuntimeException {
  public InvalidBookDataException(String message) {
      super(message);
  }
}
