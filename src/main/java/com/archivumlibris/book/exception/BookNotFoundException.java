package com.archivumlibris.book.exception;

public class BookNotFoundException extends RuntimeException {
  public BookNotFoundException(){
    super("Book not Found");
  }
}
