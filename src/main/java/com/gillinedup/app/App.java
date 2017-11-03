package com.gillinedup.app;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.List;

import Jama.Matrix;
import com.gillinedup.graph.*;
import com.gillinedup.visualization.VisualGraphMaker;
import com.gillinedup.visualization.Visualizer;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import java.awt.Dimension;
import java.util.Map;
import javax.swing.*;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

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

        //        g.addBidirectionalVoltageEdge(2, 1, 24.0);
//        g.addBidirectionalEdge(1,3, 0.0);
//        g.addBidirectionalEdge(3,4, 150.0);
//        g.addBidirectionalEdge(3,5, 50.0);
//        g.addBidirectionalEdge(4,5, 100.0);
//        g.addBidirectionalEdge(4,6, 300.0);
//        g.addBidirectionalEdge(5,6, 250.0);
//        g.addBidirectionalEdge(6,2, 0.0);

		CycleUtil cycleUtil = new CycleUtil(g);
		List<MyGraph> cycles = cycleUtil.listAllCycles();
		CycleTransform cycleTransform = new CycleTransform(cycles);
		List<List<Edge>> rawCycles = cycleTransform.getCyclesFromGraphs();
		List<List<Edge>> processedCycles = cycleTransform.removeRedundantEdges(rawCycles);
		cycleTransform.sortCyclesByLength(processedCycles);
		cycleTransform.removeRedundantCycles(processedCycles);
        MatrixPreparer matrixPreparer = new MatrixPreparer(processedCycles);
        matrixPreparer.fillMap();
//        LinkedHashMap<Edge, List<CurrentDirection>> map = matrixPreparer.getEdgeCurrentsMap();
//        for (Map.Entry<Edge, List<CurrentDirection>> entry : map.entrySet()) {
//            System.out.println(entry.getKey());
//        }
        matrixPreparer.fillMatrix();
        Matrix a = new Matrix(matrixPreparer.getMatrix());
        Matrix b = new Matrix(matrixPreparer.getVector());
        Matrix x = a.solve(b);
//        for (int i = 0; i < x.getRowDimension(); i++) {
//            System.out.println(x.get(i, 0));
//        }

//        UndirectedGraph<Vertex, Edge> graphForVisualization = new UndirectedSparseGraph<>();
//        Vertex v1 = new Vertex(1);
//        Vertex v2 = new Vertex(2);
//        Vertex v3 = new Vertex(3);
//        Vertex v4 = new Vertex(4);
//        Vertex v5 = new Vertex(5);
//        Vertex v6 = new Vertex(6);

//        Edge e1 = new Edge(v2, v1, 24.0);
//        Edge e2 = new Edge(v1, v3, 0.0);
//        Edge e3 = new Edge(v3, v4, 150.0);
//        Edge e4 = new Edge(v3, v5, 50.0);
//        Edge e5 = new Edge(v4, v5, 100.0);
//        Edge e6 = new Edge(v4, v6, 300.0);
//        Edge e7 = new Edge(v5, v6, 250.0);
//        Edge e8 = new Edge(v6, v2, 0.0);
//        graphForVisualization.addEdge(e1, v2, v1);
//        graphForVisualization.addEdge(e2, v1, v3);
//        graphForVisualization.addEdge(e3, v3, v4);
//        graphForVisualization.addEdge(e4, v3, v5);
//        graphForVisualization.addEdge(e5, v4, v5);
//        graphForVisualization.addEdge(e6, v4, v6);
//        graphForVisualization.addEdge(e7, v5, v6);
//        graphForVisualization.addEdge(e8, v6, v2);
//

        VisualGraphMaker visualGraphMaker = new VisualGraphMaker(
                processedCycles,
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
