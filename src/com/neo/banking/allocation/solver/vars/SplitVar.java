package com.neo.banking.allocation.solver.vars;

import JaCoP.constraints.Sum;
import JaCoP.constraints.XdivYeqZ;
import JaCoP.constraints.XmulCeqZ;
import JaCoP.constraints.XmulYeqZ;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import com.neo.banking.allocation.solver.ValueHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplitVar {

    private Store store;
    private String splitId;

    private IntVar qty;
    private List<AllocationVar> allocations = new ArrayList<AllocationVar>();


    //private IntVar weight;
    private IntVar totalQty;
    //private IntVar totalQty100;
    private IntVar totalPx;
    private IntVar avgPx;

    public SplitVar(Store store, String splitId, IntVar qty, int splitQty) {
        this.store = store;
        this.splitId = splitId;
        this.qty = qty;
        //this.weight = new IntVar(store, "split_" + splitId + "_weight", ValueHelper.qty(weight));
        this.totalQty = new IntVar(store, "split_" + splitId + "_weight", ValueHelper.qty(splitQty));
        //this.totalQty = new IntVar(store, "split_" + splitId + "_totalQty", 0, ValueHelper.minlimit);
        //this.totalQty100 = new IntVar(store, "split_" + splitId + "_totalQty100", 0, ValueHelper.limit);
        this.totalPx = new IntVar(store, "split_" + splitId + "_totalPx", 0, ValueHelper.limit);
        this.avgPx = new IntVar(store, "split_" + splitId + "_avgPx", 0, ValueHelper.minlimit);
    }

    public void imposeConstraints() {
        List<IntVar> allocationsQty = getAllocationsQty();
        IntVar[] arrayQty = allocationsQty.toArray(new IntVar[allocationsQty.size()]);
        store.impose(new Sum(arrayQty, totalQty));

        //store.impose(new XmulCeqZ(this.totalQty, 100, this.totalQty100)); // Info: Variable containing totalQuantity * 100
        //store.impose(new XmulYeqZ(this.weight, qty, this.totalQty100)); // Important: Verify that the weight is correct

        List<IntVar> allocationsTotalPx = getAllocationsTotalPx();
        IntVar[] arrayTotalPx = allocationsTotalPx.toArray(new IntVar[allocationsTotalPx.size()]);
        store.impose(new Sum(arrayTotalPx, totalPx)); // Info

        store.impose(new XdivYeqZ(this.totalPx, this.totalQty, this.avgPx)); // Info
    }

    @Override
    public String toString() {
        return "SplitVar{" +
                "splitId='" + splitId + '\'' +
                //", weight=" + weight +
                ", totalQty=" + totalQty +
                ", totalPx=" + totalPx +
                ", avgPx=" + avgPx +
                '}';
    }

    public String getSplitId() {
        return splitId;
    }

    public List<AllocationVar> getAllocations() {
        return allocations;
    }

    private List<IntVar> getAllocationsQty() {
        List<IntVar> result = new ArrayList<IntVar>();
        for (AllocationVar alloc : allocations) {
            result.add(alloc.getQty());
        }
        return result;
    }

    private List<IntVar> getAllocationsTotalPx() {
        List<IntVar> result = new ArrayList<IntVar>();
        for (AllocationVar alloc : allocations) {
            result.add(alloc.getTotalPx());
        }
        return result;
    }

    public List<IntVar> getVars() {
        return Arrays.asList(/*weight,*/ totalQty /*, totalQty100*/);
    }

    /*
    public IntVar getWeight() {
        return weight;
    }
    */

    public IntVar getTotalQty() {
        return totalQty;
    }

    public IntVar getAvgPx() {
        return avgPx;
    }

    /*
    public IntVar getTotalQty100() {
        return totalQty100;
    }
    */
}
