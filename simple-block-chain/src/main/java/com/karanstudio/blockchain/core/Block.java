package com.karanstudio.blockchain.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Block<T> {

    private String hash;
    private List<Transaction<T>> transactions;

    public Block(List<Transaction<T>> transactions, String previousHash) {
        this.transactions = transactions;
        this.hash = generateHashCode(transactions, previousHash);
    }

    public String getHash() {
        return hash;
    }

    public ArrayList<Transaction<T>> getTransactions() {
        return transactions;
    }

    public static String generateHashCode(Object o, String previousHash) {
        return Arrays.hashCode(new Object[]{o.hashCode(), previousHash}) + "";
    }
}
