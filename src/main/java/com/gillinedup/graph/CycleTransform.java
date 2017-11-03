package com.gillinedup.graph;

import java.util.*;

public class CycleTransform {
    private List<MyGraph> cyclesAsGraphList;
    private List<List<Edge>> cycles;

    public CycleTransform(List<MyGraph> cyclesList) {
        this.cyclesAsGraphList = cyclesList;
    }

    public List<List<Edge>> getCycles() {
        return cycles;
    }

    public List<List<Edge>> getCyclesFromGraphs() {
        this.cycles = new ArrayList<>();
        for(MyGraph g : cyclesAsGraphList) {
            cycles.add(g.getEdges());
        }
        return cycles;
    }

    public List<List<Edge>> removeRedundantEdges(List<List<Edge>> rawCycles) {

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

    public void sortCyclesByLength(List<List<Edge>> cycles) {
        cycles.sort((l1, l2) -> (l1.size() - l2.size()));
    }

    public void removeRedundantCycles(List<List<Edge>> cycles) {
        Set<Edge> appearedEdgesSet = new HashSet<>();
        boolean foundNewEdge = false;
        for (int i = 0; i < cycles.size(); i++) {
            for (Edge e : cycles.get(i)) {
                if(isNewEdge(appearedEdgesSet, e)) {
                    appearedEdgesSet.add(e);
                    foundNewEdge = true;
                }
            }
            if(!foundNewEdge) {
                cycles.remove(i);
                i--;
            }
            foundNewEdge = false;
        }
    }

    public boolean isNewEdge(Set<Edge> appearedEdgesSet, Edge e) {
        return !(appearedEdgesSet.contains(e) ||
                appearedEdgesSet.contains(new Edge(e.getDestination(), e.getSource())));
    }

}
