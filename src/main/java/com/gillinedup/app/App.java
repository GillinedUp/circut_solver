package com.gillinedup.app;

import java.util.List;

import Jama.Matrix;
import com.gillinedup.graph.*;
import com.gillinedup.visualization.VisualGraphMaker;
import com.gillinedup.visualization.Visualizer;

public class App {
	public static void main(String[] args) {
		MyGraph g = new MyGraph();

        g.addBidirectionalVoltageEdge(2, 1, 24.0);
        g.addBidirectionalEdge(1,3, 0.0);
        g.addBidirectionalEdge(3,4, 150.0);
        g.addBidirectionalEdge(3,5, 50.0);
        g.addBidirectionalEdge(4,5, 100.0);
        g.addBidirectionalEdge(4,6, 300.0);
        g.addBidirectionalEdge(5,6, 250.0);
        g.addBidirectionalEdge(6,2, 0.0);

//        g.addBidirectionalEdge(1,2, 4.0);
//        g.addBidirectionalVoltageEdge(1,4, 28.0);
//        g.addBidirectionalEdge(2,3, 1.0);
//        g.addBidirectionalEdge(2,5, 2.0);
//        g.addBidirectionalVoltageEdge(3,6, 7.0);
//        g.addBidirectionalEdge(4,5, 0.0);
//        g.addBidirectionalEdge(5,6, 0.0);

		CycleUtil cycleUtil = new CycleUtil(g);
		List<MyGraph> cycles = cycleUtil.listAllCycles();
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

        VisualGraphMaker visualGraphMaker = new VisualGraphMaker(
                x,
                g.getVertices(),
                matrixPreparer.getEdgeCurrentsMap()
        );
        visualGraphMaker.createGraph();

        Visualizer visualizer = new Visualizer(visualGraphMaker.getResultGraph());
        visualizer.setContext();
        visualizer.initializeFrame();

    }

}
