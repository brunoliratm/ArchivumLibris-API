package com.archivumlibris.exception.purchase;

public class PurchaseNotFoundException extends RuntimeException {

    public PurchaseNotFoundException(){
        super("Purchase not found");
    }

}
