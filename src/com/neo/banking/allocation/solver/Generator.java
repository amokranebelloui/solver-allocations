package com.neo.banking.allocation.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Generator {

    public static List<Exec> generateExecs(int number, int minQty, int maxQty, int minPx, int maxPx) {
        List<Exec> result = new ArrayList<Exec>();
        for (int i = 1; i <= number; i ++) {
            result.add(generateExec("" + i, minQty, maxQty, minPx, maxPx));
        }
        return result;
    }

    private static Exec generateExec(String id, int minQty, int maxQty, int minPx, int maxPx) {
        return new Exec(id, ValueHelper.random(maxQty - minQty) + minQty, ValueHelper.random(maxPx - minPx) + minPx);
    }

    public static List<Split> generate3Split(int qty) {
        int a = ValueHelper.random(qty);
        int b = ValueHelper.random(qty - a);
        int c = qty - a - b;

        List<Split> ss = Arrays.asList(
                new Split("1", a),
                new Split("2", b),
                new Split("3", c)
        );
        if (a >= 0 & b >= 0 & c >= 0) {
            return ss;
        }
        return null;
    }

}
