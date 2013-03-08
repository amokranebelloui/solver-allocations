package com.neo.banking.allocation.solver.sets;

import JaCoP.core.IntVar;
import JaCoP.core.Store;
import com.neo.banking.allocation.solver.vars.AllocationVar;
import com.neo.banking.allocation.solver.vars.ExecutionVar;
import com.neo.banking.allocation.solver.vars.SplitVar;

import java.util.ArrayList;
import java.util.List;

public class AllocationSetVar {

    private Store store;

    private List<AllocationVar> allocations = new ArrayList<AllocationVar>();

    public AllocationSetVar(Store store) {
        this.store = store;
    }

    public AllocationVar get(String execId, String splitId) {
        for (AllocationVar alloc : allocations) {
            if (alloc.getExecId().equals(execId) && alloc.getSplitId().equals(splitId)) {
                return alloc;
            }
        }
        return null;
    }

    public void createFrom(ExecutionSetVar execs, SplitSetVar splits) {
        for (ExecutionVar exec : execs.getExecutions()) {
            for (SplitVar split : splits.getSplits()) {
                AllocationVar alloc = new AllocationVar(store, exec, split);
                allocations.add(alloc);
            }
        }
    }

    public List<IntVar> getVars() {
        List<IntVar> result = new ArrayList<IntVar>();
        for (AllocationVar alloc : allocations) {
            result.addAll(alloc.getVars());
        }
        return result;
    }
}
