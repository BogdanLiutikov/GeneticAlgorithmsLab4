package lab3;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RankSelection;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.selection.TruncationSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TspAlg {

    static double best_rout = Double.MAX_VALUE;
    static int best_gen = -1;

    public static void main(String[] args) {
        int N = 10;
        double[] fitness = new double[N];
        int[] gen = new int[N];
        for (int i = 0; i < N; i++) {
            System.out.println("Тест " + (i + 1));
            best_rout = Double.MAX_VALUE;
            best_gen = -1;
            run(60, 100_000, 10);
            fitness[i] = best_rout;
            gen[i] = best_gen;
        }
        System.out.println(Arrays.stream(fitness).average());
        System.out.println(Arrays.stream(gen).average());
    }

    private static void run(int population, int generation, int elit) {
        String problem = "tasks/djb2036.tsp"; // name of problem or path to input file

        int populationSize = population; // size of population
        int generations = generation; // number of generations


        Random random = new Random(); // random

        TspFitnessFunction evaluator = new TspFitnessFunction(problem); // Fitness function
        CandidateFactory<TspSolution> factory = new TspFactory(evaluator.dimension); // generation of solutions

        ArrayList<EvolutionaryOperator<TspSolution>> operators = new ArrayList<EvolutionaryOperator<TspSolution>>();
        operators.add(new TspCrossover()); // Crossover
        operators.add(new TspMutation()); // Mutation
        EvolutionPipeline<TspSolution> pipeline = new EvolutionPipeline<TspSolution>(operators);

//        SelectionStrategy<Object> selection = new RouletteWheelSelection(); // Selection operator
//        SelectionStrategy<Object> selection = new RankSelection(); // Selection operator
        SelectionStrategy<Object> selection = new TournamentSelection(new Probability(0.7)); // Selection operator


//        EvolutionEngine<TspSolution> algorithm = new SteadyStateEvolutionEngine<TspSolution>(
//                factory, pipeline, evaluator, selection, populationSize, false, random);
        EvolutionEngine<TspSolution> algorithm = new GenerationalEvolutionEngine<>(
                factory, pipeline, evaluator, selection, random);

        algorithm.addEvolutionObserver(new EvolutionObserver() {
            public void populationUpdate(PopulationData populationData) {
                double bestFit = populationData.getBestCandidateFitness();
//                System.out.println("Generation " + populationData.getGenerationNumber() + ": " + bestFit);
                TspSolution best = (TspSolution) populationData.getBestCandidate();
//                System.out.println("\tBest solution = " + best.toString());
                if (bestFit < best_rout) {
                    best_rout = bestFit;
                    best_gen = populationData.getGenerationNumber();
                }
            }
        });

        TerminationCondition terminate = new GenerationCount(generations);
        algorithm.evolve(populationSize, elit, terminate);
        System.out.println(best_rout);
        System.out.println(best_gen);
    }
}
