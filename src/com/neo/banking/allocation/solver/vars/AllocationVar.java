package com.neo.banking.allocation.solver.vars;

import JaCoP.constraints.XmulYeqZ;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import com.neo.banking.allocation.solver.ValueHelper;

import java.util.Arrays;
import java.util.List;

public class AllocationVar {

    private ExecutionVar execVar;
    private SplitVar splitVar;

    private IntVar qty;
    private IntVar totalPx;


    public AllocationVar(Store store, ExecutionVar execVar, SplitVar splitVar) {
        this.execVar = execVar;
        this.splitVar = splitVar;
        String id = execVar.getExecId() + "-" + splitVar.getSplitId();
        this.qty = new IntVar(store, "alloc_" + id + "_qty", 0, ValueHelper.minlimit);
        this.totalPx = new IntVar(store, "alloc_" + id + "_total", 0, ValueHelper.limit);

        execVar.getAllocations().add(this);
        splitVar.getAllocations().add(this);

        store.impose(new XmulYeqZ(qty, execVar.getPx(), totalPx));  // Important: Computes alloc total price
    }

    @Override
    public String toString() {
        return "AllocationVar{" +
                "exec=" + execVar.getExecId() +
                ", split=" + splitVar.getSplitId() +
                ", qty=" + qty +
                '}';
    }

    public List<IntVar> getVars() {
        return Arrays.asList(qty, totalPx);
    }

    public String getExecId() {
        return execVar.getExecId();
    }

    public String getSplitId() {
        return splitVar.getSplitId();
    }

    public IntVar getQty() {
        return qty;
    }

    public IntVar getTotalPx() {
        return totalPx;
    }
}
