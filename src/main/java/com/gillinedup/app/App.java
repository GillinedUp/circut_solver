package com.gillinedup.app;

import java.util.List;

import com.gillinedup.graph.*;

public class App {
	public static void main(String[] args) {
		Graph g = new Graph();

		g.addBidirectionalEdge(1,2);
		g.addBidirectionalEdge(1,3);
		g.addBidirectionalEdge(3,4);
		g.addBidirectionalEdge(3,5);
		g.addBidirectionalEdge(4,5);
		g.addBidirectionalEdge(4,6);
		g.addBidirectionalEdge(5,6);
		g.addBidirectionalEdge(6,2);

		CycleUtil cycleUtil = new CycleUtil(g);
		List<Graph> cycles = cycleUtil.listAllCycles();
        CycleTransform cycleTransform = new CycleTransform(cycles);
        List<List<Edge>> rawCycles = cycleTransform.getCyclesFromGraphs();
        List<List<Edge>> processedCycles = cycleTransform.removeRedundantEdges(rawCycles);
        cycleTransform.sortCyclesByLength(processedCycles);
        cycleTransform.removeRedundantCycles(processedCycles);

        for(List<Edge> cycle : processedCycles) {
            for (Edge e : cycle) {
                System.out.print(e + " ");
            }
            System.out.println();
        }

	}

}
