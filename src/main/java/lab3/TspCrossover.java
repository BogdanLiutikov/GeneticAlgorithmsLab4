package lab3;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.*;

public class TspCrossover extends AbstractCrossover<TspSolution> {
    protected TspCrossover() {
        super(1);
    }

    protected List<TspSolution> mate(TspSolution p1, TspSolution p2, int i, Random random) {
        ArrayList<TspSolution> children = new ArrayList<>();
        // your implementation:
        TspSolution c1 = new TspSolution(p1);
        TspSolution c2 = new TspSolution(p2);
        int dim = c1.dimension;

        orderCrossOver(p1, p2, c1, random);
        orderCrossOver(p2, p1, c2, random);

        children.add(c1);
        children.add(c2);
        return children;
    }

    private static void orderCrossOver(TspSolution mainParent, TspSolution secondParent, TspSolution child, Random random) {
        int dim = mainParent.dimension;

        int a = random.nextInt(dim - 1);
        int b = random.nextInt(dim - a) + a;

        Set<Integer> used = new HashSet<>();
        for (int i = a; i <= b; i++) {
            int cur = mainParent.getRoute().get(i);
            used.add(cur);
            child.getRoute().set(i, cur);
        }
        int insertIndex = (b + 1) % dim;
        for (int i = 0; i < dim; i++) {
            int curIndex = (b + 1 + i) % dim;
            int cur = secondParent.getRoute().get(curIndex);
            if (!used.contains(cur)) {
                child.getRoute().set(insertIndex, cur);
                insertIndex = (insertIndex + 1) % dim;
            }
        }
    }
}
