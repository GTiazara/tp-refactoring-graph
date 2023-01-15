package org.acme.graph.model;

import org.acme.graph.model.*;
import org.acme.graph.routing.DijkstraPathFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


public class PathTree {
    private static final Logger log = LogManager.getLogger(DijkstraPathFinder.class);

    private Map<Vertex, PathNode> nodes;

    /**
     * Prépare le graphe pour le calcul du plus court chemin
     * @param graph
     * @param origin
     */
    public PathTree(Graph graph, Vertex origin)
    {

        log.trace("initGraph({})", origin);
        this.nodes = new HashMap<>();

        for (Vertex vertex : graph.getVertices()) {
            //init
            this.nodes.put(vertex, new PathNode());

            this.getNode(vertex).setCost(origin == vertex ? 0.0 : Double.POSITIVE_INFINITY);
            this.getNode(vertex).setReachingEdge(null);
            this.getNode(vertex).setVisited(false);

        }
    }

    public PathNode getNode(Vertex vertex)
    {
        return this.nodes.get(vertex);
    }

    /**
     * Construit le chemin en remontant les relations incoming edge
     *
     * @param target
     * @return
     */
    public Path getPath(Vertex target) {
        List<Edge> result = new ArrayList<>();

        Edge current = this.getNode(target).getReachingEdge();
        do {
            result.add(current);
            current = this.getNode(current.getSource()).getReachingEdge();
        } while (current != null);

        Collections.reverse(result);
        Path path = new Path();
        path.setEdges(result);

        return path;
    }

}
