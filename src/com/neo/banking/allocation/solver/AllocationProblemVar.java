package com.neo.banking.allocation.solver;

import JaCoP.core.IntVar;
import JaCoP.core.Store;
import com.neo.banking.allocation.solver.sets.AllocationSetVar;
import com.neo.banking.allocation.solver.sets.ExecutionSetVar;
import com.neo.banking.allocation.solver.sets.SplitSetVar;
import com.neo.banking.allocation.solver.vars.AllocationVar;
import com.neo.banking.allocation.solver.vars.ExecutionVar;
import com.neo.banking.allocation.solver.vars.SplitVar;

public class AllocationProblemVar {

    private Store store;

    private IntVar qty;

    private ExecutionSetVar execs;

    private SplitSetVar splits;

    private AllocationSetVar allocations;

    public AllocationProblemVar(Store store) {
        this.store = store;
        this.qty = new IntVar(store, "qty", 0, ValueHelper.limit);
        this.execs = new ExecutionSetVar(store, qty);
        this.splits = new SplitSetVar(store, qty);
        this.allocations = new AllocationSetVar(store);
    }

    public void addExec(String execId, int qty, int px) {
        execs.add(execId, qty, px);
    }

    public void addSplit(String splitId, int weight) {
        splits.add(splitId, weight);
    }

    public void createAllocs() {
        allocations.createFrom(execs, splits);
    }

    public void initPb() {
        createAllocs();
        getExecs().imposeConstraints();
        getSplits().imposeConstraints();
    }


    public String display() {
        StringBuilder builder = new StringBuilder();
        builder.append("Quantity " + qty.value() + "\n");

        builder.append("Splits:	\t\t");
        for (SplitVar split : splits.getSplits()) {
            builder.append("[" + split.getSplitId() + "] " + split.getTotalQty().value() + " (" + split.getAvgPx().value()  + ")\t");
        }
        builder.append("\n\n");
        for (ExecutionVar exec : execs.getExecutions()) {
            builder.append("[" + exec.getExecId() + "] " + exec.getQty().value() + " (" + exec.getPx().value() + ")\t");
            for (AllocationVar alloc : exec.getAllocations()) {
                builder.append("[" + alloc.getSplitId() + "] " + alloc.getQty().value() + "\n");
                builder.append("\t\t\t\t");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public String displaySimple() {
        StringBuilder builder = new StringBuilder();
        builder.append(qty + "\n");
        for (ExecutionVar exec : execs.getExecutions()) {
            builder.append(exec + "\n");
        }
        for (SplitVar split : splits.getSplits()) {
            builder.append(split + "\n");
        }
        for (ExecutionVar exec : execs.getExecutions()) {
            for (AllocationVar alloc : exec.getAllocations()) {
                builder.append(alloc + "\n");
            }
        }
        return builder.toString();
    }

    public IntVar getQty() {
        return qty;
    }

    public ExecutionSetVar getExecs() {
        return execs;
    }

    public SplitSetVar getSplits() {
        return splits;
    }

    public AllocationSetVar getAllocations() {
        return allocations;
    }
}
