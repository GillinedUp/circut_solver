package com.gillinedup.app;

import java.util.List;

import com.gillinedup.graph.*;

public class App {
	public static void main(String[] args) {
		Graph g = new Graph();

//		g.addBidirectionalEdge(new Point(2, 1), new Point(2, 5));
//		g.addBidirectionalEdge(new Point(2, 1), new Point(3, 1));
//		g.addBidirectionalEdge(new Point(2, 5), new Point(4, 5));
//		g.addBidirectionalEdge(new Point(3, 1), new Point(6, 1));
//		g.addBidirectionalEdge(new Point(3, 1), new Point(4, 5));
//		g.addBidirectionalEdge(new Point(4, 5), new Point(7, 5));
//		g.addBidirectionalEdge(new Point(7, 5), new Point(7, 2));
//		g.addBidirectionalEdge(new Point(7, 5), new Point(9, 5));
//		g.addBidirectionalEdge(new Point(9, 5), new Point(9, 4));
//		g.addBidirectionalEdge(new Point(9, 4), new Point(7, 2));
//		g.addBidirectionalEdge(new Point(9, 4), new Point(9, 1));
//		g.addBidirectionalEdge(new Point(9, 1), new Point(7, 1));
//		g.addBidirectionalEdge(new Point(7, 1), new Point(7, 2));
//		g.addBidirectionalEdge(new Point(7, 1), new Point(6, 1));
//		g.addBidirectionalEdge(new Point(7, 2), new Point(6, 1));

        g.addBidirectionalEdge(1, 2);
        g.addBidirectionalEdge(1, 7);
        g.addBidirectionalEdge(2, 3);
        g.addBidirectionalEdge(2, 4);
        g.addBidirectionalEdge(3, 5);
        g.addBidirectionalEdge(3, 6);
        g.addBidirectionalEdge(4, 5);
        g.addBidirectionalEdge(4, 6);
        g.addBidirectionalEdge(6, 7);

//        g.addBidirectionalEdge(1, 2);
//        g.addBidirectionalEdge(2, 3);
//        g.addBidirectionalEdge(3, 4);
//        g.addBidirectionalEdge(4, 1);

//        List<Edge> edges = g.getEdges();
//        for (Edge e : edges) {
//            System.out.println(e);
//        }
//        List<Vertex> vertices = g.getVertices();
//        for (Vertex e : vertices) {
//            System.out.println(e);
//        }

		CycleUtil cycleUtil = new CycleUtil(g);
		List<Graph> cycles = cycleUtil.listAllCycles();
		for (Graph cycle : cycles) {
			System.out.println(cycle.getEdges());
		}
        CycleTranform cycleTranform = new CycleTranform(cycles);
        List<List<Edge>> rawCycles = cycleTranform.getCyclesFromGraphs();

//        List<Edge> exampleCycle = rawCycles.get(0);
//        List<Edge> resultCycle = new ArrayList<>();
//        for (Edge e : exampleCycle) {
//                System.out.print(e + " ");
//        }
//        System.out.println();


//        System.out.println(exampleCycle.size());
//        while(!exampleCycle.isEmpty()) {
//
//        }
//        int currentSourceId = 0;
//        int currentDestinationId = 0;
//        currentSourceId = exampleCycle.get(0).getSource().getId();
//        currentDestinationId = exampleCycle.get(0).getDestination().getId();
//        int loopInv = exampleCycle.size() / 2;
//        for (int i = 0; i < loopInv; i++) {
//            for (int j = 0; j < exampleCycle.size(); j++) {
//                if(exampleCycle.get(j).getSource().getId() == currentSourceId &&
//                        exampleCycle.get(j).getDestination().getId() == currentDestinationId) {
//                    resultCycle.add(exampleCycle.remove(j));
//                    break;
//                }
//            }
//            for (int j = 0; j < exampleCycle.size(); j++) {
//                if(exampleCycle.get(j).getDestination().getId() == currentSourceId &&
//                        exampleCycle.get(j).getSource().getId() == currentDestinationId) {
//                    exampleCycle.remove(j);
//                    break;
//                }
//            }
//            for (int j = 0; j < exampleCycle.size(); j++) {
//                if(exampleCycle.get(j).getSource().getId() == currentDestinationId) {
//                    currentSourceId = exampleCycle.get(j).getSource().getId();
//                    currentDestinationId = exampleCycle.get(j).getDestination().getId();
//                    break;
//                }
//            }
//        }
//
//        for (Edge e : resultCycle) {
//            System.out.print(e + " ");
//        }
//        System.out.println();

        List<List<Edge>> processedCycles = cycleTranform.removeRedundantEdges(rawCycles);
        for(List<Edge> cycle : processedCycles) {
            for (Edge e : cycle) {
                System.out.print(e + " ");
            }
            System.out.println();
        }

	}

}
