package com.gillinedup.graph;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MatrixPreparerTest {

    @Test
    public void testFillMatrix() {
        Graph g = new Graph();

        g.addBidirectionalVoltageEdge(1, 2, 24.0);
        g.addBidirectionalEdge(1,3, 0.0);
        g.addBidirectionalEdge(3,4, 150.0);
        g.addBidirectionalEdge(3,5, 50.0);
        g.addBidirectionalEdge(4,5, 100.0);
        g.addBidirectionalEdge(4,6, 300.0);
        g.addBidirectionalEdge(5,6, 250.0);
        g.addBidirectionalEdge(6,2, 0.0);

        CycleUtil cycleUtil = new CycleUtil(g);
        List<Graph> cycles = cycleUtil.listAllCycles();
        CycleTransform cycleTransform = new CycleTransform(cycles);
        List<List<Edge>> rawCycles = cycleTransform.getCyclesFromGraphs();
        List<List<Edge>> processedCycles = cycleTransform.removeRedundantEdges(rawCycles);
        cycleTransform.sortCyclesByLength(processedCycles);
        cycleTransform.removeRedundantCycles(processedCycles);
        MatrixPreparer matrixPreparer = new MatrixPreparer(processedCycles);
        matrixPreparer.fillMap();
        Map<Edge, List<CurrentDirection>> edgeCurrentsMap = matrixPreparer.getEdgeCurrentsMap();
        List<CurrentDirection> currentDirections1 = edgeCurrentsMap.get(new Edge(new Vertex(5), new Vertex(4)));
        assertEquals(currentDirections1.size(), 2);

    }
}
