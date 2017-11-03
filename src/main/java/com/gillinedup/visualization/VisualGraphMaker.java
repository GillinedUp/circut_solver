package com.gillinedup.visualization;

import Jama.Matrix;
import com.gillinedup.graph.Edge;
import com.gillinedup.graph.MyGraph;
import com.gillinedup.graph.Vertex;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;

import java.util.List;

public class VisualGraphMaker {
    Graph<Vertex, Edge> resultGraph;
    List<Vertex> vertices;
    List<List<Edge>> cycles;
    Matrix b;

    public VisualGraphMaker(List<List<Edge>> cycles, Matrix b, List<Vertex> vertices) {
        this.cycles = cycles;
        this.b = b;
        resultGraph = new DirectedSparseGraph<>();
        this.vertices = vertices;
    }

    public void createGraph() {
        for (Vertex v : vertices) {
            resultGraph.addVertex(v);
        }
        for (int i = 0; i < cycles.size(); i++) {
            checkRow(i);
        }

    }

    private void checkRow(int i) {
        List<Edge> currentRow = cycles.get(i);
        for (Edge currentEdge : currentRow) {
            Edge reversedEdge = new Edge(
                    currentEdge.getDestination(),
                    currentEdge.getSource(),
                    currentEdge.getWeight()
            );
            if(!(currentEdge.isVisited() || reversedEdge.isVisited())) {

            }
        }
    }
}
