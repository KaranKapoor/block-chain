package com.karanstudio.blockchain.core;

import java.util.UUID;

public class Transaction<T> {

    private T t;
    private String transactionId;

    public Transaction(T t) {
        this.t = t;
        this.transactionId = UUID.randomUUID().toString();
    }
}
