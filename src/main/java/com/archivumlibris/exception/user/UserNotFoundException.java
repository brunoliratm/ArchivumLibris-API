package com.archivumlibris.exception.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException (){
        super("User not found");
    }
}
