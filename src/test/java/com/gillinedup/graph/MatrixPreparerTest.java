package com.gillinedup.graph;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MatrixPreparerTest {

    @Test
    public void testCheckInfoAboutEdge() {
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
        Edge edge = new Edge(new Vertex(4), new Vertex(3), 150.0);
        matrixPreparer.checkInfoAboutEdge(0, edge);
        double[][] matrix = matrixPreparer.getMatrix();
        assertEquals(matrix[0][2], 150.0, 1e-6);
    }

    @Test
    public void testFillRow() {
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
        matrixPreparer.fillRow(0);
        assertEquals(300.0, matrixPreparer.getMatrix()[0][0], 1e-6);
        assertEquals(100.0, matrixPreparer.getMatrix()[0][1], 1e-6);
        assertEquals(150.0, matrixPreparer.getMatrix()[0][2], 1e-6);
        matrixPreparer.fillRow(1);
        assertEquals(100.0, matrixPreparer.getMatrix()[1][0], 1e-6);
        assertEquals(650.0, matrixPreparer.getMatrix()[1][1], 1e-6);
        assertEquals(-300.0, matrixPreparer.getMatrix()[1][2], 1e-6);
        matrixPreparer.fillRow(2);
        assertEquals(150.0, matrixPreparer.getMatrix()[2][0], 1e-6);
        assertEquals(-300.0, matrixPreparer.getMatrix()[2][1], 1e-6);
        assertEquals(450.0, matrixPreparer.getMatrix()[2][2], 1e-6);
    }

    @Test
    public void testFillMatrix() {
        Graph g = new Graph();
        g.addBidirectionalEdge(1,2, 4.0);
        g.addBidirectionalVoltageEdge(1,4, 28.0);
        g.addBidirectionalEdge(2,3, 1.0);
        g.addBidirectionalEdge(2,5, 2.0);
        g.addBidirectionalVoltageEdge(3,6, 7.0);
        g.addBidirectionalEdge(4,5, 0.0);
        g.addBidirectionalEdge(5,6, 0.0);
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
        assertEquals(3.0, matrixPreparer.getMatrix()[0][0], 1e-6);
        assertEquals(-2.0, matrixPreparer.getMatrix()[0][1], 1e-6);
        assertEquals(-2.0, matrixPreparer.getMatrix()[1][0], 1e-6);
        assertEquals(6.0, matrixPreparer.getMatrix()[1][1], 1e-6);
    }

    @Test
    public void testVoltage() {
        Graph g = new Graph();
        g.addBidirectionalEdge(1,2, 4.0);
        g.addBidirectionalVoltageEdge(4,1, 28.0);
        g.addBidirectionalEdge(2,3, 1.0);
        g.addBidirectionalEdge(2,5, 2.0);
        g.addBidirectionalVoltageEdge(6,3, 7.0);
        g.addBidirectionalEdge(4,5, 0.0);
        g.addBidirectionalEdge(5,6, 0.0);
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
        assertEquals(3.0, matrixPreparer.getMatrix()[0][0], 1e-6);
        assertEquals(-2.0, matrixPreparer.getMatrix()[0][1], 1e-6);
        assertEquals(-2.0, matrixPreparer.getMatrix()[1][0], 1e-6);
        assertEquals(6.0, matrixPreparer.getMatrix()[1][1], 1e-6);
        assertEquals(7.0, matrixPreparer.getVector()[0][0], 1e-6);
        assertEquals(-28.0, matrixPreparer.getVector()[1][0], 1e-6);
    }

    @Test
    public void testVoltage2() {
        Graph g = new Graph();
        g.addBidirectionalVoltageEdge(2, 1, 24.0);
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
        matrixPreparer.fillMatrix();
        assertEquals(0.0, matrixPreparer.getVector()[0][0], 1e-6);
        assertEquals(0.0, matrixPreparer.getVector()[1][0], 1e-6);
        assertEquals(24.0, matrixPreparer.getVector()[2][0], 1e-6);
    }
}
