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
        MatrixPreparer matrixPreparer = new MatrixPreparer(processedCycles);
        matrixPreparer.fillMap();
        matrixPreparer.fillMatrix();
        Matrix a = new Matrix(matrixPreparer.getMatrix());
        Matrix b = new Matrix(matrixPreparer.getVector());
        Matrix x = a.solve(b);
        for (int i = 0; i < x.getRowDimension(); i++) {
            System.out.println(x.get(i, 0));
        }

	}

}
