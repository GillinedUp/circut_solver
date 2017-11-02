package com.gillinedup.app;

import java.util.List;

import com.gillinedup.graph.*;

public class App {
	public static void main(String[] args) {
		Graph g = new Graph();

        g.addBidirectionalEdge(1, 2);
        g.addBidirectionalEdge(1, 7);
        g.addBidirectionalEdge(2, 3);
        g.addBidirectionalEdge(2, 4);
        g.addBidirectionalEdge(3, 5);
        g.addBidirectionalEdge(3, 6);
        g.addBidirectionalEdge(4, 5);
        g.addBidirectionalEdge(4, 6);
        g.addBidirectionalEdge(6, 7);

//        g.addBidirectionalEdge(1,2);
//        g.addBidirectionalEdge(2,5);
//        g.addBidirectionalEdge(5,4);
//        g.addBidirectionalEdge(4,1);
//        g.addBidirectionalEdge(2,3);
//        g.addBidirectionalEdge(3,6);
//        g.addBidirectionalEdge(6,5);

		CycleUtil cycleUtil = new CycleUtil(g);
		List<Graph> cycles = cycleUtil.listAllCycles();
        CycleTranform cycleTranform = new CycleTranform(cycles);
        List<List<Edge>> rawCycles = cycleTranform.getCyclesFromGraphs();
        List<List<Edge>> processedCycles = cycleTranform.removeRedundantEdges(rawCycles);

        for(List<Edge> cycle : processedCycles) {
            for (Edge e : cycle) {
                System.out.print(e + " ");
            }
            System.out.println();
        }

	}

}
