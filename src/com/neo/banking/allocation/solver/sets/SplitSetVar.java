package com.neo.banking.allocation.solver.sets;

import JaCoP.constraints.Sum;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import com.neo.banking.allocation.solver.vars.SplitVar;

import java.util.ArrayList;
import java.util.List;

public class SplitSetVar {

    private Store store;
    private IntVar qty;

    private List<SplitVar> splits = new ArrayList<SplitVar>();


    public SplitSetVar(Store store, IntVar qty) {
        this.store = store;
        this.qty = qty;
    }

    public SplitVar add(String splitId, int weight) {
        SplitVar splitVar = new SplitVar(store, splitId, qty, weight);
        splits.add(splitVar);
        return splitVar;
    }

    public void add(SplitVar splitVar) {
        splits.add(splitVar);
    }

    public List<IntVar> getVars() {
        List<IntVar> result = new ArrayList<IntVar>();
        for (SplitVar split : splits) {
            result.addAll(split.getVars());
        }
        return result;
    }

    public void imposeConstraints() {
        for (SplitVar split : splits) {
            split.imposeConstraints();
        }
        store.impose(new Sum(new ArrayList<IntVar>(getSplitsTotalQty()), qty));
    }

    public List<SplitVar> getSplits() {
        return splits;
    }

    private List<IntVar> getSplitsTotalQty() {
        List<IntVar> result = new ArrayList<IntVar>();
        for (SplitVar split : splits) {
            result.add(split.getTotalQty());
        }
        return result;
    }
}
