package com.neo.banking.allocation.solver;

public class Split {

    private final String id;

    private final int quantity;

    public Split(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

}
