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
                if (!edgeCurrentsMap.containsKey(edge)) {
                    List<CurrentDirection> currentDirections = new ArrayList<>();
                    currentDirections.add(new CurrentDirection(i, true, edge.getWeight()));
                    edgeCurrentsMap.put(edge, currentDirections);
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

    public void fillRow(int i) {
        List<Edge> currentRow = cycles.get(i);
        for (int j = 0; j < currentRow.size(); j++) {
            Edge currentEdge = currentRow.get(j);
            if (currentEdge.isVoltage()) {
                if (currentEdge.getVoltageDirection() < 0) {
                    vector[i] = currentEdge.getWeight();
                } else {
                    vector[i] = -currentEdge.getWeight();
                }
            } else {
                if (currentEdge.getWeight() != 0.0) {
                    matrix[i][i] += currentEdge.getWeight();
                    checkInfoAboutEdge(i, currentEdge);
                }
            }
        }
    }

    public void checkInfoAboutEdge(int i, Edge currentEdge) {
        Edge reversedEdge = new Edge(
                currentEdge.getDestination(),
                currentEdge.getSource(),
                currentEdge.getWeight()
        );
        List<CurrentDirection> currents;
        if (edgeCurrentsMap.containsKey(currentEdge)) {
            currents = edgeCurrentsMap.get(currentEdge);
            for (CurrentDirection current : currents) {
                int j = current.getCurrentNum();
                if (i != j) {
                    matrix[i][j] += current.getWeight();
                }
            }
        }
        if (edgeCurrentsMap.containsKey(reversedEdge)) {
            currents = edgeCurrentsMap.get(reversedEdge);
            for (CurrentDirection current : currents) {
                int j = current.getCurrentNum();
                if (i != j) {
                    matrix[i][j] -= current.getWeight();
                }
            }
        }
    }

    public Map<Edge, List<CurrentDirection>> getEdgeCurrentsMap() {
        return edgeCurrentsMap;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public double[] getVector() {
        return vector;
    }
}
