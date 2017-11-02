package com.gillinedup.graph;

import java.util.*;

public class CycleTranform {
    private List<Graph> cyclesASGraphList;
    private List<List<Edge>> cycles;

    public CycleTranform(List<Graph> cyclesList) {
        this.cyclesASGraphList = cyclesList;
    }

    public List<List<Edge>> getCyclesFromGraphs() {
        this.cycles = new ArrayList<>();
        for(Graph g : cyclesASGraphList) {
            cycles.add(g.getEdges());
        }
        return cycles;
    }

    public List<List<Edge>> removeRedundantEdges(List<List<Edge>> rawCycles) {

//        int currentEndId = 0;
//        boolean idIsPresent = false;
//        for (List<Edge> cycle : rawCycles) {
//            for (int i = 0; i < cycle.size(); i++) {
//                if (idIsPresent) {
//                    if (cycle.get(i).getSource().getId() == currentEndId) {
//                        cycle.remove(cycle.get(i+1));
//                    } else {
//                        cycle.remove(cycle.get(i));
//                    }
//                } else {
//                    cycle.remove(cycle.get(i+1));
//                }
//                currentEndId = cycle.get(i).getDestination().getId();
//                idIsPresent = true;
//            }
//        }

//        int currentSourceId = 0;
//        int currentDestinationId = 0;
//        List<List<Edge>> resultCycles = new ArrayList<>();
//        for (int i = 0; i < rawCycles.size(); i++) {
//            List<Edge> currentCycle = rawCycles.get(i);
//            resultCycles.add(new ArrayList<>());
//            currentSourceId = currentCycle.get(0).getSource().getId();
//            currentDestinationId = currentCycle.get(0).getDestination().getId();
//            resultCycles.get(i).add(currentCycle.remove(0));
//            for (int j = 0; j < currentCycle.size(); j++) {
//                Edge currentEdge = currentCycle.get(j);
//                if(currentEdge.getSource().getId() == currentDestinationId &&
//                        currentEdge.getDestination().getId() == currentSourceId) {
//                    currentCycle.remove(j);
//                }
//            }
//        }

        int currentSourceId = 0;
        int currentDestinationId = 0;
        List<List<Edge>> resultCycles = new ArrayList<>();
        for (int i = 0; i < rawCycles.size(); i++) {
            List<Edge> currentRawCycle = rawCycles.get(i);
            resultCycles.add(new ArrayList<>());
            List<Edge> currentResultCycle = resultCycles.get(i);
            currentSourceId = currentRawCycle.get(0).getSource().getId();
            currentDestinationId = currentRawCycle.get(0).getDestination().getId();
            int loopInv = currentRawCycle.size() / 2;

            for (int k = 0; k < loopInv; k++) {
                for (int j = 0; j < currentRawCycle.size(); j++) {
                    if(currentRawCycle.get(j).getSource().getId() == currentSourceId &&
                            currentRawCycle.get(j).getDestination().getId() == currentDestinationId) {
                        currentResultCycle.add(currentRawCycle.remove(j));
                        break;
                    }
                }
                for (int j = 0; j < currentRawCycle.size(); j++) {
                    if(currentRawCycle.get(j).getDestination().getId() == currentSourceId &&
                            currentRawCycle.get(j).getSource().getId() == currentDestinationId) {
                        currentRawCycle.remove(j);
                        break;
                    }
                }
                for (int j = 0; j < currentRawCycle.size(); j++) {
                    if(currentRawCycle.get(j).getSource().getId() == currentDestinationId) {
                        currentSourceId = currentRawCycle.get(j).getSource().getId();
                        currentDestinationId = currentRawCycle.get(j).getDestination().getId();
                        break;
                    }
                }
            }
        }


        return resultCycles;
    }
}
