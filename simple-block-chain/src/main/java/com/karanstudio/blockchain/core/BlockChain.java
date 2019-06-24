package com.karanstudio.blockchain.core;

import java.util.ArrayList;
import java.util.List;

public class BlockChain<T> {

    private List<Block<T>> blockChain;
    private int maxTransactionsPerBlock = 0;

    private List<Transaction<T>> transientTransactions;
    private int noOfTransactions = 0;

    public BlockChain(final int maxTransactionsPerBlock) {
        blockChain = new ArrayList<Block<T>>();
        transientTransactions = new ArrayList<>();
        this.maxTransactionsPerBlock = maxTransactionsPerBlock;
    }

    public void addTransaction(Transaction<T> transaction) throws Exception {
        synchronized (transientTransactions) {
            if (noOfTransactions < maxTransactionsPerBlock) {
                transientTransactions.add(transaction);
                noOfTransactions++;
            }
            else {
                noOfTransactions = 0;
                addBlock(transientTransactions);
            }
        }
    }

    private void addBlock(List<Transaction<T>> transactions) throws Exception {
        synchronized (blockChain) {
            // Validate current last block in block chain before adding new block
            if (validateLastBlock()) {
                Block<T> block = new Block(transactions, blockChain.get(blockChain.size() - 1).getHash());
                blockChain.add(block);
            } else {
                throw new Exception("Block chain invalidated");
            }
            // Validate after adding if hash is generated properly
            if (!validateLastBlock()) {
                throw new Exception("Block chain invalidated");
            }
        }
    }

    private boolean validateBlock(Block newBlock, Block lastBlock) {
        if (newBlock.getHash() == Block.generateHashCode(lastBlock.getTransactions(), lastBlock.getHash())) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean validateLastBlock() {
        return (validateBlock(blockChain.get(blockChain.size() - 1), blockChain.get(blockChain.size() - 2)));
    }

    public boolean validateBlockChain() {
        boolean isValid = true;
        for (int i = blockChain.size() - 1; i > 0; i--) {
            if (!validateBlock(blockChain.get(i), blockChain.get(i-1))) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }
}
