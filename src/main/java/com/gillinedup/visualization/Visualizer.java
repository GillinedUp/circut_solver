package com.gillinedup.visualization;

import com.gillinedup.graph.Edge;
import com.gillinedup.graph.Vertex;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class Visualizer {

    private BasicVisualizationServer<Vertex, Edge> basicVisualizationServer;

    public Visualizer(Graph<Vertex, Edge> graph) {
        Layout<Vertex, Edge> layout = new FRLayout<>(graph);
        this.basicVisualizationServer = new BasicVisualizationServer<>(layout);
    }

    public void setContext() {
        DecimalFormat df = new DecimalFormat("#.##");
        basicVisualizationServer
                .getRenderContext()
                .setEdgeLabelTransformer(edge -> df.format(edge.getWeight()));
        basicVisualizationServer
                .getRenderContext()
                .setVertexLabelTransformer(Vertex::toString);
        basicVisualizationServer.setPreferredSize(new Dimension(1900, 1700));
    }

    public void initializeFrame() {
        JFrame frame = new JFrame("graph");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(basicVisualizationServer);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
