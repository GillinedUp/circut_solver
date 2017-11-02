package com.gillinedup.graph;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CycleTransformTest {

    @Test
    public void testEdgeEquals() {
        Edge edge1 = new Edge(new Vertex(5), new Vertex(6));
        Edge edge2 = new Edge(new Vertex(5), new Vertex(6));
        assertEquals(edge1, edge2);
    }

    @Test
    public void testEdgeHashSet() {
        Set<Edge> appearedEdgesSet = new HashSet<>();
        Edge edge1 = new Edge(new Vertex(5), new Vertex(6));
        Edge edge2 = new Edge(new Vertex(5), new Vertex(6));
        appearedEdgesSet.add(edge1);
        assertTrue(appearedEdgesSet.contains(edge2));
    }

    @Test
    public void testRemoveRedundantCycles() {
        Graph g = new Graph();
        g.addBidirectionalEdge(1,2);
        g.addBidirectionalEdge(2,5);
        g.addBidirectionalEdge(5,4);
        g.addBidirectionalEdge(4,1);
        g.addBidirectionalEdge(2,3);
        g.addBidirectionalEdge(3,6);
        g.addBidirectionalEdge(6,5);
        CycleUtil cycleUtil = new CycleUtil(g);
        List<Graph> cycles = cycleUtil.listAllCycles();
        CycleTransform cycleTransform = new CycleTransform(cycles);
        List<List<Edge>> rawCycles = cycleTransform.getCyclesFromGraphs();
        List<List<Edge>> processedCycles = cycleTransform.removeRedundantEdges(rawCycles);
        cycleTransform.sortCyclesByLength(processedCycles);
        cycleTransform.removeRedundantCycles(processedCycles);
    }

    @Test
    public void testIsNewEdge() {
        Edge edge1 = new Edge(new Vertex(5), new Vertex(6));
        Edge edge2 = new Edge(new Vertex(6), new Vertex(5));
        Edge edge3 = new Edge(new Vertex(5), new Vertex(6));
        Graph g = new Graph();
        g.addBidirectionalEdge(1,2);
        g.addBidirectionalEdge(2,5);
        g.addBidirectionalEdge(5,4);
        g.addBidirectionalEdge(4,1);
        g.addBidirectionalEdge(2,3);
        g.addBidirectionalEdge(3,6);
        g.addBidirectionalEdge(6,5);
        CycleUtil cycleUtil = new CycleUtil(g);
        List<Graph> cycles = cycleUtil.listAllCycles();
        CycleTransform cycleTransform = new CycleTransform(cycles);
        Set<Edge> appearedEdgesSet = new HashSet<>();
        appearedEdgesSet.add(edge2);
        assertFalse(cycleTransform.isNewEdge(appearedEdgesSet,edge1));
    }
}
