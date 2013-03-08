package com.neo.banking.allocation.solver;

import JaCoP.core.IntVar;
import JaCoP.core.Store;
import JaCoP.search.*;

import java.util.ArrayList;
import java.util.List;

public class AllocationResolver {

    private static Store store = new Store();

    public static void main(String[] args) {
        int total = 0;
        int ok = 0;

        List<Exec> execs = Generator.generateExecs(25, 100, 900, 660, 680);
        int qty = Exec.totalQuantity(execs);
        System.out.println("Total " + qty + ", Avg Price " + Exec.avgPrice(execs));

        for (int i = 0; i < 20; i++) {
            List<Split> splits = Generator.generate3Split(qty);

            if (splits != null) {
                System.out.println(splits);
                boolean result = avgPrice(execs, splits);
                total ++;
                if (result) ok++;
            }
        }
        System.out.println(ok + "/" + (total - ok) + " on " + total + "");
    }




    private static boolean avgPrice(List<Exec> ee, List<Split> ss) {
        AllocationProblemVar pb = new AllocationProblemVar(store);

        for (Exec e : ee) {
            pb.addExec(e.getId(), e.getQuantity(), e.getPx());
        }
        for (Split s : ss) {
            pb.addSplit(s.getId(), s.getQuantity());
        }
        pb.initPb();

        /*
        AllocationVar a12 = pb.getAllocations().get("1", "2");
        AllocationVar a32 = pb.getAllocations().get("3", "2");
        store.impose(new XeqC(a12.getQty(), 500));
        store.impose(new XeqC(a32.getQty(), 10));
        */

        System.out.println("Solving ...");
        boolean result = resolve(store, pb);
        System.out.println(result ? pb.display(): "*** NoResult ***");
        return result;
    }


    private static boolean resolve(Store store, AllocationProblemVar pb) {
        List<IntVar> vars = new ArrayList<IntVar>();
        vars.add(pb.getQty());
        vars.addAll(pb.getExecs().getVars());
        vars.addAll(pb.getSplits().getVars());
        vars.addAll(pb.getAllocations().getVars());

        IntVar[] varArray = vars.toArray(new IntVar[vars.size()]);

        Search<IntVar> search = new DepthFirstSearch<IntVar>();
        //search.setTimeOut(5);
        IndomainMin<IntVar> intVarIndomainMin = new IndomainMin<IntVar>();
        //SelectChoicePoint<IntVar> select = new InputOrderSelect<IntVar>(store, varArray, intVarIndomainMin);
        SelectChoicePoint<IntVar> select = new SimpleSelect<IntVar>(varArray, new MostConstrainedDynamic<IntVar>(), new MostConstrainedDynamic<IntVar>(), intVarIndomainMin);
        //search.printAllSolutions();
        return search.labeling(store, select);
    }


}
