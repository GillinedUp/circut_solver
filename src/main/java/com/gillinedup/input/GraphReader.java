package com.gillinedup.input;

import com.gillinedup.graph.MyGraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphReader {
    private File file;
    private MyGraph graph;

    public GraphReader(File file) {
        this.file = file;
        graph = new MyGraph();
    }

    public void readFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(file));
        boolean isVoltageEdge = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().length() == 0) {
                isVoltageEdge = true;
                continue;
            }
            String[] data = line.split(" ");
            int vertex1Id = Integer.parseInt(data[0]);
            int vertex2Id = Integer.parseInt(data[1]);
            double weight = Double.parseDouble(data[2]);
            if(isVoltageEdge) {
                graph.addBidirectionalVoltageEdge(vertex1Id, vertex2Id, weight);
            } else {
                graph.addBidirectionalEdge(vertex1Id, vertex2Id, weight);
            }
        }
    }

    public MyGraph getGraph() {
        return graph;
    }
}
