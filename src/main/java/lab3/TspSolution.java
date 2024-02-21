package lab3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TspSolution {

    int dimension;
    List<Integer> route;

    public List<Integer> getRoute() {
        return route;
    }

    public TspSolution(int citiesNum) {
        this.dimension = citiesNum;

        route = new ArrayList<Integer>(citiesNum);

        for (int i = 0; i < citiesNum; i++) {
            route.add(i);
        }

        Collections.shuffle(route);
    }

    public TspSolution(int dimension, Random random) {
        this.dimension = dimension;
        route = new ArrayList<>(dimension);
        for (int i = 0; i < dimension; i++) {
            route.add(i);
        }
        Collections.shuffle(route, random);
    }

    public TspSolution(TspSolution parent) {
        this.dimension = parent.dimension;
        this.route = new ArrayList<>(parent.getRoute());
    }


    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder();

        int idx = route.indexOf(0);
        List<Integer> orderedRoute = new ArrayList<>(route);
        Collections.rotate(orderedRoute, -idx);
        for (int i = 0; i < orderedRoute.size(); i++) {
            representation.append(orderedRoute.get(i) + 1).append(" -> ");
        }

        return representation.toString();
    }
}
