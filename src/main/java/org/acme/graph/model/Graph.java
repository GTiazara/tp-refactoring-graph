package org.acme.graph.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.acme.graph.errors.NotFoundException;
import org.locationtech.jts.geom.Coordinate;

/**
 * 
 * Un graphe matérialisé par une liste de sommets et d'arcs
 * 
 * @author MBorne
 *
 */
public class Graph {
	/**
	 * Liste des sommets
	 */
	private List<Vertex> vertices = new ArrayList<>();

	/**
	 * Liste des arcs
	 */
	private List<Edge> edges = new ArrayList<>();

	/**
	 * Récupération de la liste sommets
	 * 
	 * @return
	 */
	public Collection<Vertex> getVertices() {
		return vertices;
	}

	/**
	 * Récupération de la liste arcs
	 * 
	 * @return
	 */

	/**
	 * Recherche d'un sommet par identifiant
	 * 
	 * @param id
	 * @return
	 */
	public Vertex findVertex(String id) {
		for (Vertex vertex : vertices) {
			if (vertex.getId().equals(id)) {
				return vertex;
			}
		}
		throw new NotFoundException(String.format("Vertex '%s' not found", id));
	}

	/**
	 * Recherche d'un sommet par égalité stricte de coordonnées
	 * 
	 * @param coordinate
	 * @return
	 */
	public Vertex findVertex(Coordinate coordinate) {
		for (Vertex vertex : vertices) {
			Coordinate candidate = vertex.getCoordinate();
			if (candidate != null && candidate.equals(coordinate)) {
				return vertex;
			}
		}
		throw new NotFoundException(String.format("Vertex not found at [%s,%s]", coordinate.x, coordinate.y));
	}

	/**
	 * Récupération ou création d'un sommet en assurant l'unicité
	 * 
	 * @param graph
	 * @param coordinate
	 * @return
	 */
	public Vertex getOrCreateVertex(Coordinate coordinate) {
		Vertex vertex;
		try {
			vertex = findVertex(coordinate);
		} catch (NotFoundException e) {
			/* création d'un nouveau sommet car non trouvé */
			vertex = this.createVertex(coordinate, Integer.toString(getVertices().size()));
		}
		return vertex;
	}

	/**
	 * Récupération de la liste des arcs
	 * 
	 * @return
	 */
	public Collection<Edge> getEdges() {
		return edges;
	}

	/**
	 * Recherche des arcs sortant d'un sommet
	 * 
	 * @param vertex
	 * @return
	 */
	public List<Edge> getInEdges(Vertex vertex) {
		List<Edge> result = new ArrayList<>();
		for (Edge candidate : edges) {
			if (candidate.getTarget() != vertex) {
				continue;
			}
			result.add(candidate);
		}
		return result;
	}

	/**
	 * Recherche des arcs sortant d'un sommet
	 * 
	 * @param vertex
	 * @return
	 */
	public List<Edge> getOutEdges(Vertex vertex) {
		List<Edge> result = new ArrayList<>();
		for (Edge candidate : edges) {
			if (candidate.getSource() != vertex) {
				continue;
			}
			result.add(candidate);
		}
		return result;
	}

	/**
	 * Définition de la liste des arcs
	 * 
	 * @param edges
	 */


	public Vertex createVertex(Coordinate coordinate, String id)
	{
		Vertex vertex = new Vertex();
		vertex.setId(id);
		vertex.setCoordinate(coordinate);
		this.getVertices().add(vertex);

		return vertex;
	}

	public Edge createEdge(Vertex source, Vertex target, String id) {
		Edge st = new Edge(source, target);
		st.setId(id);
		this.getEdges().add(st);
		return st;
	}
}
