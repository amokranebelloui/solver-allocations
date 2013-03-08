package com.neo.banking.allocation.solver;

import com.neo.banking.allocation.solver.vars.ExecutionVar;

import java.util.List;

public class Exec {

    private final String id;

    private final int quantity;

    private final int px;

    public Exec(String id, int quantity, int px) {
        this.id = id;
        this.quantity = quantity;
        this.px = px;
    }

    public static int totalQuantity(List<Exec> execs) {
        int sumQty = 0;
        for (Exec exec : execs) {
            sumQty += exec.getQuantity();
        }
        return sumQty;
    }

    public static int avgPrice(List<Exec> execs) {
        int sumPx = 0;
        int sumQty = 0;
        for (Exec exec : execs) {
            sumQty += exec.getQuantity();
            sumPx += exec.getPx() * exec.getQuantity();
        }
        return sumPx / sumQty;
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPx() {
        return px;
    }
}
