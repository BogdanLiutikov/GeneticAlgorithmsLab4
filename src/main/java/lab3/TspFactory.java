package lab3;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;

public class TspFactory extends AbstractCandidateFactory<TspSolution> {

    int dimension;

    public TspFactory(int dimension) {
        this.dimension = dimension;
    }

    public TspSolution generateRandomCandidate(Random random) {
        TspSolution solution = new TspSolution(dimension, random);
        //your implementation
        return solution;
    }
}

