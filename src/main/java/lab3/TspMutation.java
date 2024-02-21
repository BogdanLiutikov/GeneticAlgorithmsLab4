package lab3;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TspMutation implements EvolutionaryOperator<TspSolution> {
    public List<TspSolution> apply(List<TspSolution> population, Random random) {
        // your implementation:
        for (TspSolution candidate : population
        ) {
            for (int i = 0; i < candidate.dimension; i++) {
                if (random.nextDouble() < 0.1 / candidate.dimension) {
                    if (random.nextDouble() < 0.1)
                        swap(candidate, random);
                    if (random.nextDouble() < 0.1)
                        inverse(candidate, random);
                    if (random.nextDouble() < 0.1)
                        scramble(candidate, random);
                }
            }
        }
        return population;
    }

    private static void swap(TspSolution candidate, Random random) {
        int dim = candidate.dimension;
        int i1 = random.nextInt(dim);
        int range = (int) (random.nextGaussian() * 30);
        int i2 = Math.max(0, Math.min(i1 + range, dim - 1));
        if (i1 == i2)
            return;
        Integer g1 = candidate.getRoute().get(i1);
        candidate.getRoute().set(i1, candidate.getRoute().get(i2));
        candidate.getRoute().set(i2, g1);
    }

    private static void inverse(TspSolution candidate, Random random) {
        int dim = candidate.dimension;
        int i1 = random.nextInt(dim - 1);
        int i2 = i1 + random.nextInt(30) + 1;
        i2 = Math.min(i2, dim - 1);
        Collections.reverse(candidate.getRoute().subList(i1, i2 + 1));
    }

    private static void scramble(TspSolution candidate, Random random) {
        int dim = candidate.dimension;
        int i1 = random.nextInt(dim - 1);
        int i2 = i1 + random.nextInt(30) + 1;
        i2 = Math.min(i2, dim - 1);
        Collections.shuffle(candidate.getRoute().subList(i1, i2 + 1), random);
    }
}
