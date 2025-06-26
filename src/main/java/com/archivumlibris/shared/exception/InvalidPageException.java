package com.archivumlibris.shared.exception;

public class InvalidPageException extends RuntimeException {
  public InvalidPageException() {
    super("Page number must be greater than 0");
  }
}
