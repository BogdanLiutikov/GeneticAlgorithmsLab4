package lab3;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TspFitnessFunction implements FitnessEvaluator<TspSolution> {

    int dimension;

    static class City {
        int id;
        int x;
        int y;

        public City(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "City{" +
                    "id=" + id +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    City[] cities;

    public TspFitnessFunction(String problem) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream(problem));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String line = scanner.nextLine();
        while (!"NODE_COORD_SECTION".equals(line)) {
            if (line.contains("DIMENSION")) {
                this.dimension = Integer.parseInt(line.split(" : ")[1]);
            }
            line = scanner.nextLine();
        }

        cities = new City[this.dimension];

        line = scanner.nextLine();
        while (!"EOF".equals(line)) {
            String idxy[] = line.split(" ");
            int id = Integer.parseInt(idxy[0]);
            int x = Integer.parseInt(idxy[1]);
            int y = Integer.parseInt(idxy[2]);

            cities[id - 1] = new City(id, x, y);

            line = scanner.nextLine();
        }

        scanner.close();
    }

    public double getFitness(TspSolution solution, List<? extends TspSolution> list) {
        double dist = 0;
        List<Integer> ids = solution.getRoute();
        for (int i = 0; i < ids.size(); i++) {
            int id1 = ids.get(i);
            int id2 = ids.get((i + 1) % ids.size());
            City c1 = cities[id1];
            City c2 = cities[id2];
            dist += getDistance(c1.x, c1.y, c2.x, c2.y);
        }
        return dist;
    }

    private double getDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public boolean isNatural() {
        return false;
    }
}
