package com.gillinedup.graph;

import java.util.concurrent.ThreadLocalRandom;

public class GraphGenerator {

    public static MyGraph generateGraph(int n) {
        MyGraph graph = new MyGraph();
        for (int i = 1; i < n; i++) {
            graph.addBidirectionalEdge(i, i+1, ThreadLocalRandom.current().nextDouble(0.0, 20.0));
        }
        graph.addBidirectionalVoltageEdge(n, 1, ThreadLocalRandom.current().nextDouble(20.0, 100.0));
        int numOfAdditionalVertices = new Double(n/2.0).intValue();

        if(numOfAdditionalVertices >= 1) {
            for (int i = 1; i <= numOfAdditionalVertices; i++) {
                int v1 = ThreadLocalRandom.current().nextInt(1, n);
                int v2 = ThreadLocalRandom.current().nextInt(1, n);
                while(v1 == v2) {
                    v2 = ThreadLocalRandom.current().nextInt(1, n);
                }
                graph.addBidirectionalEdge(v1, v2, ThreadLocalRandom.current().nextDouble(0.0, 20.0));
            }
        }
        return graph;
    }
}
