package com.gillinedup.app;

import java.util.List;
import java.util.Map;

import Jama.Matrix;
import com.gillinedup.graph.*;

public class App {
	public static void main(String[] args) {
		Graph g = new Graph();

        g.addBidirectionalEdge(1, 2, 7.0);
        g.addBidirectionalVoltageEdge(7, 1, 300.0);
        g.addBidirectionalEdge(2, 3, 2.0);
        g.addBidirectionalEdge(2, 4, 5.0);
        g.addBidirectionalEdge(3, 5, 4.0);
        g.addBidirectionalEdge(3, 6, 6.0);
        g.addBidirectionalEdge(4, 5, 1.0);
        g.addBidirectionalEdge(4, 6, 11.0);
        g.addBidirectionalEdge(5, 6, 9.0);
        g.addBidirectionalEdge(6, 7, 0.0);

		CycleUtil cycleUtil = new CycleUtil(g);
		List<Graph> cycles = cycleUtil.listAllCycles();
		CycleTransform cycleTransform = new CycleTransform(cycles);
		List<List<Edge>> rawCycles = cycleTransform.getCyclesFromGraphs();
		List<List<Edge>> processedCycles = cycleTransform.removeRedundantEdges(rawCycles);
		cycleTransform.sortCyclesByLength(processedCycles);
		cycleTransform.removeRedundantCycles(processedCycles);
//        for(List<Edge> cycle : processedCycles) {
//            for (Edge e : cycle) {
//                System.out.print(e + " ");
//            }
//            System.out.println();
//        }
        MatrixPreparer matrixPreparer = new MatrixPreparer(processedCycles);
        matrixPreparer.fillMap();
        matrixPreparer.fillMatrix();
//        double[][] matrix = matrixPreparer.getMatrix();
//        double[] vector = matrixPreparer.getVector();
//        for (double[] aMatrix : matrix) {
//            for (double anAMatrix : aMatrix) {
//                System.out.print(anAMatrix + " ");
//            }
//            System.out.println();
//        }
//        for (double aVector : vector) {
//            System.out.println(aVector);
//        }
        double[] vector1 = matrixPreparer.getVector();
        int n = vector1.length;
        double[][] vector = new double[n][1];
        for (int i = 0; i < n; i++) {
            vector[i][0] = vector1[i];
        }
        Matrix a = new Matrix(matrixPreparer.getMatrix());
        Matrix b = new Matrix(vector);
        Matrix x = a.solve(b);
        for (int i = 0; i < n; i++) {
            System.out.println(x.get(i, 0));
        }

	}

}
