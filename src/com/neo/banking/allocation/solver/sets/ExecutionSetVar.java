package com.neo.banking.allocation.solver.sets;

import JaCoP.constraints.Sum;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import com.neo.banking.allocation.solver.vars.ExecutionVar;

import java.util.ArrayList;
import java.util.List;

public class ExecutionSetVar {

    private Store store;
    private IntVar qty;

    private List<ExecutionVar> executions = new ArrayList<ExecutionVar>();


    public ExecutionSetVar(Store store, IntVar qty) {
        this.store = store;
        this.qty = qty;
    }

    public ExecutionVar add(String execId, int qty, int px) {
        ExecutionVar execVar = new ExecutionVar(store, execId, qty, px);
        executions.add(execVar);
        return execVar;
    }

    public void add(ExecutionVar execVar) {
        executions.add(execVar);
    }

    public List<IntVar> getVars() {
        List<IntVar> result = new ArrayList<IntVar>();
        for (ExecutionVar exec : executions) {
            result.addAll(exec.getVars());
        }
        return result;
    }

    public void imposeConstraints() {
        for (ExecutionVar exec : executions) {
            exec.imposeConstraints();
        }
        store.impose(new Sum(new ArrayList<IntVar>(getExecutionsQty()), qty));
    }

    public List<ExecutionVar> getExecutions() {
        return executions;
    }

    private List<IntVar> getExecutionsQty() {
        List<IntVar> result = new ArrayList<IntVar>();
        for (ExecutionVar exec : executions) {
            result.add(exec.getQty());
        }
        return result;
    }
}
