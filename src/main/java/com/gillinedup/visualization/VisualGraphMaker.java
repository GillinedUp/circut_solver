package com.gillinedup.visualization;

import Jama.Matrix;
import com.gillinedup.graph.CurrentDirection;
import com.gillinedup.graph.Edge;
import com.gillinedup.graph.Vertex;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;

import java.util.*;

public class VisualGraphMaker {
    private Graph<Vertex, Edge> resultGraph;
    private List<Vertex> vertices;
    private Matrix b;
    private LinkedHashMap<Edge, List<CurrentDirection>> edgeCurrentsMap;

    public VisualGraphMaker(
            Matrix b,
            List<Vertex> vertices,
            LinkedHashMap<Edge, List<CurrentDirection>> edgeCurrentsMap
    )
    {
        this.b = b;
        resultGraph = new DirectedSparseGraph<>();
        this.vertices = vertices;
        this.edgeCurrentsMap = edgeCurrentsMap;
    }

    public void createGraph() {
        for (Vertex v : vertices) {
            resultGraph.addVertex(v);
        }
        Set<Edge> visitedEdges = new HashSet<>();
        for (Map.Entry<Edge, List<CurrentDirection>> entry : edgeCurrentsMap.entrySet()) {
            Edge edge = entry.getKey();
            Edge reversedEdge = new Edge(
                    edge.getDestination(),
                    edge.getSource(),
                    edge.getWeight()
            );
            if(!visitedEdges.contains(reversedEdge)) {
                visitedEdges.add(edge);
                List<CurrentDirection> directions = entry.getValue();
                double result = 0.0;
                if(edge.isVoltage()) {
                    result = edge.getWeight();
                } else {
                    for (CurrentDirection direction : directions) {
                        int i = direction.getCurrentNum();
                        result += b.get(i, 0);
                    }
                    if(edgeCurrentsMap.containsKey(reversedEdge)) {
                        List<CurrentDirection> otherDirections = edgeCurrentsMap.get(reversedEdge);
                        for (CurrentDirection direction : otherDirections) {
                            int i = direction.getCurrentNum();
                            result -= b.get(i, 0);
                        }
                    }
                    result = result * edge.getWeight();
                }
                if (result > 0) {
                    resultGraph.addEdge(
                            new Edge(edge.getSource(), edge.getDestination(), result),
                            edge.getSource(),
                            edge.getDestination()
                    );
                } else {
                    resultGraph.addEdge(
                            new Edge(edge.getDestination(), edge.getSource(), -result),
                            edge.getDestination(),
                            edge.getSource()
                    );
                }
            }
        }
    }

    public Graph<Vertex, Edge> getResultGraph() {
        return resultGraph;
    }
}
