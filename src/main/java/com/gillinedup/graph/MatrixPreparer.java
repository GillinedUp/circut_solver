package com.gillinedup.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatrixPreparer {
    private List<List<Edge>> cycles;
    private double[][] matrix;
    private double[] vector;
    private Map<Edge, List<CurrentDirection>> edgeCurrentsMap;

    public MatrixPreparer(List<List<Edge>> cycles) {
        this.cycles = cycles;
        int n = cycles.size();
        matrix = new double[n][n];
        vector = new double[n];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 0.0;
            }
            vector[i] = 0.0;
        }
        edgeCurrentsMap = new HashMap<>();
    }

    public void fillMap() {
        for (int i = 0; i < cycles.size(); i++) {
            List<Edge> cycle = cycles.get(i);
            for (Edge edge : cycle) {
                Edge reversedEdge = new Edge(
                        edge.getDestination(),
                        edge.getSource(),
                        edge.getWeight()
                );
                if (!edgeCurrentsMap.containsKey(edge)) {
                    if (!edgeCurrentsMap.containsKey(reversedEdge)) {
                        List<CurrentDirection> currentDirections = new ArrayList<>();
                        currentDirections.add(new CurrentDirection(i, true, edge.getWeight()));
                        edgeCurrentsMap.put(edge, currentDirections);
                    } else {
                        edgeCurrentsMap.get(reversedEdge).add(new CurrentDirection(i, true, edge.getWeight()));
                    }
                } else {
                    edgeCurrentsMap.get(edge).add(new CurrentDirection(i, true, edge.getWeight()));
                }

            }
        }
    }

    public void fillMatrix() {
        for (int i = 0; i < cycles.size(); i++) {
            fillRow(i);
        }
    }

    private void fillRow(int i) {
        List<Edge> currentRow = cycles.get(i);
        for (int j = 0; j < currentRow.size(); j++) {
            Edge currentEdge = currentRow.get(j);
            if (currentEdge.isVoltage()) {
                vector[i] = currentEdge.getWeight(); // TODO: take direction into account
            } else {
                if (currentEdge.getWeight() != 0.0) {
                    matrix[i][i] += currentEdge.getWeight();
                    checkInfoAboutEdge(i, currentEdge);
                }
            }
        }
    }

    private void checkInfoAboutEdge(int i, Edge currentEdge) {
        Edge reversedEdge = new Edge(
                currentEdge.getDestination(),
                currentEdge.getSource(),
                currentEdge.getWeight()
        );
        List<CurrentDirection> currents;
        if (edgeCurrentsMap.containsKey(currentEdge)) {
            currents = edgeCurrentsMap.get(currentEdge);
            CurrentDirection currentDirection;
            for (CurrentDirection current : currents) {
                matrix[i][current.getCurrentNum()] += current.getWeight();
            }
        }
        if (edgeCurrentsMap.containsKey(reversedEdge)) {
            currents = edgeCurrentsMap.get(reversedEdge);
            CurrentDirection currentDirection;
            for (CurrentDirection current : currents) {
                matrix[i][current.getCurrentNum()] -= current.getWeight();
            }
        }
    }
}
