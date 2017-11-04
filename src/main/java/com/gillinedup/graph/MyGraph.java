package com.gillinedup.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyGraph {
	private final List<Vertex> vertices;
	private final List<Edge> edges;
    
	public MyGraph() {
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
	}

	MyGraph(List<Vertex> vertices, List<Edge> edges) {
		this.vertices = vertices;
		this.edges = edges;
	}

	public List<Vertex> getVertices() {
		return vertices;
	}

	List<Edge> getEdges() {
		return edges;
	}

	void removeEdge(Edge edge) {
		edges.remove(edge);
	}

	Set<Vertex> getNeighbors(Vertex node) {
		Set<Vertex> neighbors = new HashSet<>();
		for (Edge edge : edges) {
			if (edge.getSource().equals(node)) {
				neighbors.add(edge.getDestination());
			}
		}
		return neighbors;
	}

	void addBidirectionalEdge(int id1, int id2) {
        addBidirectionalEdge(id1, id2, 0.0);
	}

	public void addBidirectionalEdge(int id1, int id2, double w) {
		Vertex sourceVertex = new Vertex(id1);
		Vertex destinationVertex = new Vertex(id2);

		if (!vertices.contains(sourceVertex)) {
			vertices.add(sourceVertex);
		}

		if (!vertices.contains(destinationVertex)) {
			vertices.add(destinationVertex);
		}

		edges.add(new Edge(sourceVertex, destinationVertex, w));
		edges.add(new Edge(destinationVertex, sourceVertex, w));
	}

    public void addBidirectionalVoltageEdge(int id1, int id2, double w) {
        Vertex sourceVertex = new Vertex(id1);
        Vertex destinationVertex = new Vertex(id2);

        if (!vertices.contains(sourceVertex)) {
            vertices.add(sourceVertex);
        }

        if (!vertices.contains(destinationVertex)) {
            vertices.add(destinationVertex);
        }

        Edge edge1 = new Edge(sourceVertex, destinationVertex, w);
        edge1.setVoltage();
        edge1.setVoltageDirection(1);

        Edge edge2 = new Edge(destinationVertex, sourceVertex, w);
        edge2.setVoltage();
        edge2.setVoltageDirection(-1);

        edges.add(edge1);
        edges.add(edge2);
    }

	@Override
	public String toString() {
		return "MyGraph [vertices=" + vertices + ", edges=" + edges + "]";
	}
}
