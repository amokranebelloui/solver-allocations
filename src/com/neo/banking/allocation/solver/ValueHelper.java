package com.neo.banking.allocation.solver;

import JaCoP.core.BoundDomain;

public class ValueHelper {

    public static int minlimit = 10000;
    public static int limit = 1000000;

    public static BoundDomain qty(int qty) {
        return new BoundDomain(qty, qty);
    }

    public static BoundDomain px(int px) {
        return new BoundDomain(px, px);
    }

    public static BoundDomain total(int qty, int px) {
        double err = px * 0.3;
        int min = (int)Math.round(qty * (px - err) * 1.0);
        int max = (int)Math.round(qty * (px + err) * 1.0);

        System.out.println(min + " " + max);
        return new BoundDomain(min, max);
    }

    public static int random(int max) {
        double random = Math.random() * (max * 1.0);
        return (int)random;
    }

}
