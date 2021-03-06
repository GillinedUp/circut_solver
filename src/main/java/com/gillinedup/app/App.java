package com.gillinedup.app;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import Jama.Matrix;
import com.gillinedup.graph.*;
import com.gillinedup.input.GraphReader;
import com.gillinedup.visualization.VisualGraphMaker;
import com.gillinedup.visualization.Visualizer;

public class App {
	public static void main(String[] args) {
        MyGraph g = null;
	    if (args.length != 0) {
            try {
                File file = new File(args[0]);
                GraphReader graphReader = new GraphReader(file);
                graphReader.readFromFile();
                g = graphReader.getGraph();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            Scanner in = new Scanner(System.in);
	        g = GraphGenerator.generateGraph(in.nextInt());
        }
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
