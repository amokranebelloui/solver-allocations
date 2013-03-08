package com.neo.banking.allocation.solver.vars;

import JaCoP.constraints.Sum;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import com.neo.banking.allocation.solver.ValueHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExecutionVar {

    private Store store;
    private String execId;
    private List<AllocationVar> allocations = new ArrayList<AllocationVar>();

    private IntVar qty;
    private IntVar px;
    private IntVar total;

    public ExecutionVar(Store store, String execId, int qty, int px) {
        this.store = store;
        this.execId = execId;
        this.qty = new IntVar(store, "exec_" + execId + "_qty", ValueHelper.qty(qty));
        this.px = new IntVar(store, "exec_" + execId + "_px", ValueHelper.px(px));
        this.total = new IntVar(store, "exec_" + execId + "_total", 0, ValueHelper.limit); //ValueHelper.total(qty, px)
    }

    public void imposeConstraints() {
        List<IntVar> allocationsQty = getAllocationsQty();
        IntVar[] arrayQty = allocationsQty.toArray(new IntVar[allocationsQty.size()]);
        store.impose(new Sum(arrayQty, qty)); // Important: Alloc qty sum = exec qty

        List<IntVar> allocationsTotalPx = getAllocationsTotalPx();
        IntVar[] arrayTotalPx = allocationsTotalPx.toArray(new IntVar[allocationsTotalPx.size()]);
        store.impose(new Sum(arrayTotalPx, total)); // Info: Computes total px

    }

    @Override
    public String toString() {
        return "ExecutionVar{" +
                "execId='" + execId + '\'' +
                ", qty=" + qty +
                ", px=" + px +
                '}';
    }

    public String getExecId() {
        return execId;
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
        return Arrays.asList(qty, px, total);
    }

    public IntVar getQty() {
        return qty;
    }

    public IntVar getPx() {
        return px;
    }

    public IntVar getTotal() {
        return total;
    }
}
