package org.acme.graph.model;

import java.util.Collections;
import java.util.List;

public class Path {

    private List<Edge> edges;

    public double getLenght() {
        double lenght = 0;
        for(Edge e: edges)
        {
            lenght+=e.getGeometry().getLength();
        }

        return lenght;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}
